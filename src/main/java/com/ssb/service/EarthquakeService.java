package com.ssb.service;

import com.ssb.model.EarthquakeEvent;
import com.ssb.repository.EarthquakeRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.OffsetDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.List;

@Service
public class EarthquakeService {

    @Autowired
    private EarthquakeRepository repository;

    // DateTime formatter for datetime without microseconds
    private static final DateTimeFormatter DATE_TIME_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ssXXX");

    // Secondary DateTime formatter for datetime with microseconds
    private static final DateTimeFormatter DATE_TIME_FORMATTER_MICROSECONDS = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss.SSSSSSXXX");

    // Batch size for efficient database saving
    private static final int BATCH_SIZE = 1000;

    // Method to parse the TSV file and save data to the database in batches
    public void parseAndSaveFile(MultipartFile file) {
        List<EarthquakeEvent> events = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(file.getInputStream()))) {
            String line;
            boolean isFirstLine = true;

            while ((line = reader.readLine()) != null) {
                // Skip the header line
                if (isFirstLine) {
                    isFirstLine = false;
                    continue;
                }

                String[] fields = line.split("\t"); // Assuming TSV (tab-separated values)
                if (fields.length == 0) {
                    continue; // Skip empty lines
                }

                EarthquakeEvent event = new EarthquakeEvent();

                // Set fields dynamically based on available columns
                event.setEventID(fields[0]);

                if (fields.length > 1) {
                    try {
                        // Try parsing without microseconds first
                        event.setDatetime(OffsetDateTime.parse(fields[1], DATE_TIME_FORMATTER));
                    } catch (DateTimeParseException e1) {
                        try {
                            // Try parsing with microseconds if the first fails
                            event.setDatetime(OffsetDateTime.parse(fields[1], DATE_TIME_FORMATTER_MICROSECONDS));
                        } catch (DateTimeParseException e2) {
                            System.err.println("Invalid datetime format: " + fields[1]);
                            continue;  // Skip this record if datetime is invalid
                        }
                    }
                }

                if (fields.length > 2) event.setLatitude(parseDouble(fields[2]));
                if (fields.length > 3) event.setLongitude(parseDouble(fields[3]));
                if (fields.length > 4) event.setMagnitude(parseDouble(fields[4]));
                if (fields.length > 5) event.setMag_type(fields[5]);
                if (fields.length > 6) event.setDepth(parseDouble(fields[6]));
                if (fields.length > 7) event.setPhasecount(parseInteger(fields[7]));
                if (fields.length > 8) event.setAzimuth_gap(parseDouble(fields[8]));
                if (fields.length > 9) event.setLocation(fields[9]);

                // Optionally set agency if it exists
                if (fields.length > 10) {
                    event.setAgency(fields[10]);
                }

                // Optionally parse FM-related fields if they exist
                if (fields.length > 11) {
                    try {
                        try {
                            event.setDatetimeFM(OffsetDateTime.parse(fields[11]));  // Assuming it's at index 11
                        } catch (DateTimeParseException e) {
                            System.err.println("Invalid FM datetime format: " + fields[11]);
                        }
                        if (fields.length > 12) event.setLatFM(parseDouble(fields[12]));
                        if (fields.length > 13) event.setLonFM(parseDouble(fields[13]));
                        if (fields.length > 14) event.setMagFM(parseDouble(fields[14]));
                        if (fields.length > 15) event.setMagTypeFM(fields[15]);
                        if (fields.length > 16) event.setDepthFM(parseDouble(fields[16]));
                        if (fields.length > 17) event.setPhasecountFM(parseInteger(fields[17]));
                        if (fields.length > 18) event.setAzgapFM(parseDouble(fields[18]));
                    } catch (DateTimeParseException e) {
                        System.err.println("Invalid FM datetime format: " + fields[11]);
                    }
                }

                // Add the event to the list
                events.add(event);

                // Save batch to the database
                if (events.size() == BATCH_SIZE) {
                    repository.saveAll(events);
                    events.clear();  // Clear the list after saving the batch
                }
            }

            // Save any remaining events
            if (!events.isEmpty()) {
                repository.saveAll(events);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    // Helper methods to handle parsing with defaults
    private Double parseDouble(String value) {
        try {
            return value.isEmpty() ? null : Double.parseDouble(value);
        } catch (NumberFormatException e) {
            return null;
        }
    }

    private Integer parseInteger(String value) {
        try {
            return value.isEmpty() ? null : Integer.parseInt(value);
        } catch (NumberFormatException e) {
            return null;
        }
    }

    // Retrieve event by eventID
    public EarthquakeEvent getEventById(String eventID) {
        return repository.findById(eventID).orElse(null);
    }
}