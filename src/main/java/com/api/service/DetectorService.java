package com.api.service;

import com.api.entity.*;
import com.api.repo.DetectorRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Slf4j
@Service
@AllArgsConstructor
public class DetectorService {
//    private final static String DATA_SOURCE = "src/main/resources/data/data.json";
    private DetectorRepository detectorRepository;
//     TODO сделать логику в DetectorService, задействуя логику проверки состояния из модели

    public Detector getDetectorBySerialNumber(String serialNumber) {
        log.info("IN DetectorService getDetectorById() {}", serialNumber);
        Detector detector = detectorRepository.findById(serialNumber).orElse(null);
        if (detector == null) {
            detector = getNewBuiltDetector(serialNumber);
            detectorRepository.save(detector);
        }
        return detector;
    }

    public Detector initialize(Detector detector) {
        log.info("IN DetectorService initialize() {}", detector);
        Detector detectorFromDB = detectorRepository
                .findById(detector.getSerialNumber())
                .orElse(null);
        if (detectorFromDB == null) {
            detector = Detector
                    .builder()
                    .serialNumber(detector.getSerialNumber())
                    .state(detector.getState())
                    .model(detector.getModel())
                    .build();
        }
        if (!detector.isNew())
            throw new IllegalArgumentException();
        detector.setState(State.SETUP);
        detectorRepository.save(detector);
        return detector;
    }

    public Detector activate(Detector detector) {
        log.info("IN DetectorService activate() {}", detector);
        detector.setState(State.ACTIVE);
        return detector;
    }

    public Detector setup(Detector detector) {
        log.info("IN DetectorService setup() {}", detector);
        detector.setState(State.SETUP);
        return detector;
    }

    public Detector reset(Detector detector) {
        log.info("IN DetectorService reset() {}", detector);
        detectorRepository.delete(detector);
        detector = getNewBuiltDetector(detector.getSerialNumber());
//        detector.setState(State.NEW);
        return detector;
    }

    public List<Detector> getAll() {
        log.info("IN DetectorService getAll() {}");
        return detectorRepository.findAll();
    }

    private Detector getNewBuiltDetector(String serialNumber) {
        log.info("IN DetectorService getNewBuiltDetector() {}", serialNumber);
        return Detector
                .builder()
                .serialNumber(serialNumber)
                .state(State.NEW)
                .model("V1")
                .conformityCertificate(
                        new Detector.ConformityCertificate("123", LocalDate.now())
                )
                .address("New st.")
                .location(
                        new GpsCoordinate(0.0, 0.0)
                )
                .zone(
                        new Zone(
                                new GpsCoordinate(0.0, 0.0),
                                "Russia",
                                new ArrayList<>(List.of(
                                        new Point(0, 0),
                                        new Point(0, 0)
                                ))
                        )
                )
                .build();
    }
}
