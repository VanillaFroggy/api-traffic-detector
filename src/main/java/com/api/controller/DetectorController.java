package com.api.controller;

import com.api.entity.Detector;
import com.api.service.DetectorService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/api/detector")
public class DetectorController {
    @Autowired
    private DetectorService detectorService;

    @GetMapping("")
    public ResponseEntity<List<Detector>> getAllDetectors() throws IOException {
        List<Detector> detectors = detectorService.getAll();
        if (detectors.isEmpty())
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        return new ResponseEntity<>(detectors, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Detector> getDetector(@PathVariable("id") Long id) throws IOException {
        Detector detector = detectorService.getDetectorById(id);
        if (detector == null)
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        return new ResponseEntity<>(detector, HttpStatus.OK);
    }

    @PostMapping("/initialize")
    public ResponseEntity<Detector> initializeDetector(@RequestBody @Valid Detector detector) {
        HttpHeaders headers = new HttpHeaders();
        if (detector == null)
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        detector = detectorService.initialize(detector);
        return new ResponseEntity<>(detector, headers, HttpStatus.OK);
    }

    @PutMapping("/activate")
    public ResponseEntity<Detector> activateDetector(@RequestBody @Valid Detector detector) {
        HttpHeaders headers = new HttpHeaders();
        if (detector == null)
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        detector = detectorService.activate(detector);
        return new ResponseEntity<>(detector, headers, HttpStatus.OK);
    }

    @PutMapping("/setup")
    public ResponseEntity<Detector> setupDetector(@RequestBody @Valid Detector detector) {
        HttpHeaders headers = new HttpHeaders();
        if (detector == null)
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        detector = detectorService.setup(detector);
        return new ResponseEntity<>(detector, headers, HttpStatus.OK);
    }

    @PutMapping("/reset")
    public ResponseEntity<Detector> resetDetector(@RequestBody @Valid Detector detector) throws IOException {
        HttpHeaders headers = new HttpHeaders();
        if (detector == null)
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        detector = detectorService.reset(detector);
        return new ResponseEntity<>(detector, headers, HttpStatus.OK);
    }
}
