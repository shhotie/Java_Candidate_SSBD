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

    // DateTime formatter for parsing the datetime and datetimeFM columns
    private static final DateTimeFormatter DATE_TIME_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss.SSSSSSXXX");

    // Batch size for efficient database saving
    private static final int BATCH_SIZE = 1000;

    // Method to parse the TSV file and save data to the database in batches
    public void parseAndSaveFile(MultipartFile file) {
        List<EarthquakeEvent> events = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(file.getInputStream()))) {
            String line;
            boolean isFirstLine = true; // Flag to skip the header
            while ((line = reader.readLine()) != null) {
                // Skip the header line
                if (isFirstLine) {
                    isFirstLine = false;
                    continue;
                }

                String[] fields = line.split("\t"); // Assuming TSV (tab-separated values)

                // Initialize a new EarthquakeEvent object and set its fields
                EarthquakeEvent event = new EarthquakeEvent();
                event.setEventID(fields[0]);

                // Parse the datetime field
                try {
                    event.setDatetime(OffsetDateTime.parse(fields[1], DATE_TIME_FORMATTER));
                } catch (DateTimeParseException e) {
                    System.err.println("Invalid datetime format: " + fields[1]);
                    continue;  // Skip this record if date is invalid
                }

                // Parse other fields
                event.setLatitude(parseDouble(fields[2]));
                event.setLongitude(parseDouble(fields[3]));
                event.setMagnitude(parseDouble(fields[4]));
                event.setMag_type(fields[5]);
                event.setDepth(parseDouble(fields[6]));
                event.setPhasecount(parseInteger(fields[7]));
                event.setAzimuth_gap(parseDouble(fields[8]));
                event.setLocation(fields[9]);
//                event.setAgency(fields[10]);
//
//                // Optionally parse FM-related fields if they exist
//                if (fields.length > 11) {
//                    try {
//                        event.setDatetimeFM(fields[11].isEmpty() ? null : OffsetDateTime.parse(fields[11], DATE_TIME_FORMATTER));
//                        event.setLatFM(parseDouble(fields[12]));
//                        event.setLonFM(parseDouble(fields[13]));
//                        event.setMagFM(parseDouble(fields[14]));
//                        event.setMagTypeFM(fields[15]);
//                        event.setDepthFM(parseDouble(fields[16]));
//                        event.setPhasecountFM(parseInteger(fields[17]));
//                        event.setAzgapFM(parseDouble(fields[18]));
//                        // Set other fields similarly...
//                    } catch (DateTimeParseException e) {
//                        System.err.println("Invalid FM datetime format: " + fields[11]);
//                    }
//                }

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