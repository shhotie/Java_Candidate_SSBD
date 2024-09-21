package com.ssb.controller;

import com.ssb.model.EarthquakeEvent;
import com.ssb.service.EarthquakeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/api/earthquake")
public class EarthquakeController {

    @Autowired
    private EarthquakeService service;

    // Endpoint to retrieve event by eventID
    @GetMapping("/event/{eventID}")
    public ResponseEntity<EarthquakeEvent> getEventById(@PathVariable String eventID) {
        EarthquakeEvent event = service.getEventById(eventID);
        return event != null ? ResponseEntity.ok(event) : ResponseEntity.notFound().build();
    }

    // Endpoint to upload the file and parse it
    @PostMapping("/upload")
    public ResponseEntity<String> uploadFile(@RequestParam("file") MultipartFile file) {
        service.parseAndSaveFile(file);
        return ResponseEntity.ok("File parsed and data saved successfully.");
    }

}