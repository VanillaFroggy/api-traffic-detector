package com.api.controller;

import com.api.dto.DetectorActivateDTO;
import com.api.dto.DetectorInitializeDTO;
import com.api.dto.DetectorResetDTO;
import com.api.dto.DetectorSetupDTO;
import com.api.entity.Detector;
import com.api.service.DetectorService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/detector")
@AllArgsConstructor
public class DetectorController {
    private DetectorService detectorService;

    @GetMapping("")
    public ResponseEntity<List<Detector>> getAllDetectors() {
        List<Detector> detectors = detectorService.getAll();
        if (detectors.isEmpty())
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        return new ResponseEntity<>(detectors, HttpStatus.OK);
    }

    @GetMapping("/{serialNumber}")
    public ResponseEntity<Detector> getDetector(@PathVariable("serialNumber") String serialNumber) {
        Detector detector = detectorService.getDetectorBySerialNumber(serialNumber);
        if (detector == null)
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        return new ResponseEntity<>(detector, HttpStatus.OK);
    }

    @PutMapping("/initialized")
    public ResponseEntity<DetectorInitializeDTO> initializeDetector(@RequestBody @Valid DetectorInitializeDTO detector) {
        HttpHeaders headers = new HttpHeaders();
        if (detector == null)
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        detector = detectorService.initialize(detector);
        return new ResponseEntity<>(detector, headers, HttpStatus.OK);
    }

    @PutMapping("/active")
    public ResponseEntity<DetectorActivateDTO> activateDetector(@RequestBody @Valid DetectorActivateDTO detector) {
        HttpHeaders headers = new HttpHeaders();
        if (detector == null)
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        detector = detectorService.activate(detector);
        return new ResponseEntity<>(detector, headers, HttpStatus.OK);
    }

    @PutMapping("/setup")
    public ResponseEntity<DetectorSetupDTO> setupDetector(@RequestBody @Valid DetectorSetupDTO detector) {
        HttpHeaders headers = new HttpHeaders();
        if (detector == null)
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        detector = detectorService.setup(detector);
        return new ResponseEntity<>(detector, headers, HttpStatus.OK);
    }

    @PutMapping("/reset")
    public ResponseEntity<DetectorResetDTO> resetDetector(@RequestBody @Valid DetectorResetDTO detector) {
        HttpHeaders headers = new HttpHeaders();
        if (detector == null)
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        detector = detectorService.reset(detector);
        return new ResponseEntity<>(detector, headers, HttpStatus.OK);
    }
}
