package com.api.service;

import com.api.dto.DetectorActivateDTO;
import com.api.dto.DetectorInitializeDTO;
import com.api.dto.DetectorResetDTO;
import com.api.dto.DetectorSetupDTO;
import com.api.entity.*;
import com.api.repo.DetectorRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
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
    private ModelMapper modelMapper;

    public Detector getDetectorBySerialNumber(String serialNumber) {
        log.info("IN DetectorService getDetectorById() {}", serialNumber);
        return detectorRepository
                .findById(serialNumber)
                .orElseThrow(NullPointerException::new);
    }

    public DetectorInitializeDTO initialize(DetectorInitializeDTO detectorDto) {
        log.info("IN DetectorService initialize() {}", detectorDto);
        Detector detector = modelMapper.map(detectorDto, Detector.class);
        if (detector == null) {
            detector = getNewBuiltDetector(detectorDto.getSerialNumber());
        } else if (!detector.isNew()) {
            throw new IllegalArgumentException();
        }
        detector.setState(State.SETUP);
        detectorRepository.save(detector);
        return modelMapper.map(detector, DetectorInitializeDTO.class);
    }

    public DetectorActivateDTO activate(DetectorActivateDTO detectorDto) {
        log.info("IN DetectorService activate() {}", detectorDto);
        Detector detector = modelMapper.map(detectorDto, Detector.class);
        Detector detectorFromDB = detectorRepository
                .findById(detectorDto.getSerialNumber())
                .orElseThrow(NullPointerException::new);
        if (!detector.isSetup()
                || ((Objects.nonNull(detector.getSerialNumber()) || Objects.nonNull(detectorFromDB.getSerialNumber()))
                && !detector.getSerialNumber().equals(detectorFromDB.getSerialNumber()))
                || ((Objects.nonNull(detector.getLocation()) || Objects.nonNull(detector.getZone()))
                && isDistanceMoreThanThreeHundred(detector))) {
            throw new IllegalArgumentException();
        }
        detector.setState(State.ACTIVE);
        detectorRepository.save(detector);
        return modelMapper.map(detector, DetectorActivateDTO.class);
    }

    public DetectorSetupDTO setup(DetectorSetupDTO detectorDto) {
        log.info("IN DetectorService setup() {}", detectorDto);
        Detector detector = modelMapper.map(detectorDto, Detector.class);
        detectorRepository
                .findById(detector.getSerialNumber())
                .orElseThrow(NullPointerException::new);
        if (!detector.isActive())
            throw new IllegalArgumentException();
        detector.setState(State.SETUP);
        detectorRepository.save(detector);
        return modelMapper.map(detector, DetectorSetupDTO.class);
    }

    public DetectorResetDTO reset(DetectorResetDTO detectorDto) {
        log.info("IN DetectorService reset() {}", detectorDto);
        Detector detector = modelMapper.map(detectorDto, Detector.class);
        detectorRepository.delete(detector);
        detector = getNewBuiltDetector(detectorDto.getSerialNumber());
        detectorRepository.save(detector);
        return modelMapper.map(detector, DetectorResetDTO.class);
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
