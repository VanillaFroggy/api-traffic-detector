package com.api.service;

import com.api.entity.*;
import com.api.repo.DetectorRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Slf4j
@Service
@AllArgsConstructor
public class DetectorService {
    private DetectorRepository detectorRepository;

    public Detector getDetectorBySerialNumber(String serialNumber) {
        log.info("IN DetectorService getDetectorById() {}", serialNumber);
        return detectorRepository
                .findById(serialNumber)
                .orElseThrow(NullPointerException::new);
    }

    public Detector initialize(Detector detector) {
        log.info("IN DetectorService initialize() {}", detector);
        Detector detectorFromDB = detectorRepository
                .findById(detector.getSerialNumber())
                .orElse(null);
        if (detectorFromDB == null)
            detectorFromDB = getNewBuiltDetector(detector.getSerialNumber());
        if (!detector.isNew()
                || (Objects.nonNull(detector.getAddress()) && !detector.getAddress().equals(detectorFromDB.getAddress()))
                || (Objects.nonNull(detector.getLocation()) && !detector.getLocation().equals(detectorFromDB.getLocation()))
                || (Objects.nonNull(detector.getZone()) && !detector.getZone().equals(detectorFromDB.getZone()))) {
            throw new IllegalArgumentException();
        }
        detector.setState(State.SETUP);
        detectorRepository.save(detector);
        return detector;
    }

    public Detector activate(Detector detector) {
        log.info("IN DetectorService activate() {}", detector);
        Detector detectorFromDB = detectorRepository
                .findById(detector.getSerialNumber())
                .orElseThrow(NullPointerException::new);
        if (!detector.isSetup()
                || ((Objects.nonNull(detector.getSerialNumber()) || Objects.nonNull(detectorFromDB.getSerialNumber()))
                && !detector.getSerialNumber().equals(detectorFromDB.getSerialNumber()))
                || ((Objects.nonNull(detector.getModel()) || Objects.nonNull(detectorFromDB.getModel()))
                && !detector.getModel().equals(detectorFromDB.getModel()))
                || ((Objects.nonNull(detector.getConformityCertificate()) || Objects.nonNull(detectorFromDB.getConformityCertificate()))
                && !detector.getConformityCertificate().equals(detectorFromDB.getConformityCertificate()))
                || ((Objects.nonNull(detector.getLocation()) || Objects.nonNull(detector.getZone()))
                && isDistanceMoreThanThreeHundred(detector))) {
            throw new IllegalArgumentException();
        }
        detector.setState(State.ACTIVE);
        detectorRepository.save(detector);
        return detector;
    }

    public Detector setup(Detector detector) {
        log.info("IN DetectorService setup() {}", detector);
        detectorRepository
                .findById(detector.getSerialNumber())
                .orElseThrow(NullPointerException::new);
        if (!detector.isActive())
            throw new IllegalArgumentException();
        detector.setState(State.SETUP);
        detectorRepository.save(detector);
        return detector;
    }

    public Detector reset(Detector detector) {
        log.info("IN DetectorService reset() {}", detector);
        detectorRepository.delete(detector);
        detector = getNewBuiltDetector(detector.getSerialNumber());
        detectorRepository.save(detector);
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
                .model("")
                .conformityCertificate(
                        new Detector.ConformityCertificate("", LocalDate.now())
                )
                .address("")
                .location(
                        new GpsCoordinate(0.0, 0.0)
                )
                .zone(
                        new Zone(
                                new GpsCoordinate(0.0, 0.0),
                                "",
                                new ArrayList<>(List.of(
                                        new Point(0, 0),
                                        new Point(0, 0)
                                ))
                        )
                )
                .build();
    }

    private boolean isDistanceMoreThanThreeHundred(Detector detector) {
        return Math.abs(
                Math.sqrt(
                        Math.pow((detector.getLocation().getLatitude() - detector.getZone().getGpsCoordinate().getLatitude()), 2)
                                + Math.pow((detector.getLocation().getLongitude() - detector.getZone().getGpsCoordinate().getLongitude()), 2)
                )
        ) > 300;
    }
}
