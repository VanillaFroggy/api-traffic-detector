package com.api.infrustructure.service.impl;

import com.api.infrustructure.service.mapper.DetectorServiceMapper;
import com.api.infrustructure.persistance.entity.Detector;
import com.api.infrustructure.persistance.entity.State;
import com.api.infrustructure.persistance.repo.DetectorRepository;
import com.api.infrustructure.service.DetectorService;
import com.api.infrustructure.service.dto.DetectorActivateDTO;
import com.api.infrustructure.service.dto.DetectorInitializeDTO;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class DetectorServiceImpl implements DetectorService {
    private final DetectorRepository detectorRepository;
    private final DetectorServiceMapper mapper;

    @Override
    public Detector getDetectorBySerialNumber(@Valid String serialNumber) {
        log.info("IN DetectorServiceImpl getDetectorById() {}", serialNumber);
        return detectorRepository
                .findById(serialNumber)
                .orElseThrow(NullPointerException::new);
    }

    @Override
    public void initialize(@Valid DetectorInitializeDTO dto) {
        log.info("IN DetectorServiceImpl initialize() {}", dto);
        Detector detectorFromDB = detectorRepository
                .findById(dto.getSerialNumber())
                .orElse(null);
        if (detectorFromDB == null) {
            detectorFromDB = getNewBuiltDetector(dto.getSerialNumber());
        } else if (!detectorFromDB.isNew()) {
            throw new IllegalArgumentException();
        }
        Detector detector = mapper.initializeDtoToEntity(dto);
        detector.setState(State.SETUP);
        detectorRepository.save(detector);
    }

    @Override
    public void activate(@Valid DetectorActivateDTO dto) {
        log.info("IN DetectorServiceImpl activate() {}", dto);
        Detector detectorFromDB = detectorRepository
                .findById(dto.getSerialNumber())
                .orElseThrow(NullPointerException::new);
        Detector detector = mapper.activateDtoToEntity(dto);
        detector.setState(detectorFromDB.getState());
        if (!detector.isSetup() && !isDistanceMoreThanThreeHundred(detector)) {
            throw new IllegalArgumentException();
        }
        detector.setState(State.ACTIVE);
        detectorRepository.save(detector);
    }

    @Override
    public void setup(String serialNumber) {
        log.info("IN DetectorServiceImpl setup() {}", serialNumber);
        Detector detector = detectorRepository
                .findById(serialNumber)
                .orElseThrow(NullPointerException::new);
        if (!detector.isActive())
            throw new IllegalArgumentException();
        detector.setState(State.SETUP);
        detectorRepository.save(detector);
    }

    @Override
    public void reset(String serialNumber) {
        log.info("IN DetectorServiceImpl reset() {}", serialNumber);
        Detector detector = detectorRepository
                .findById(serialNumber)
                .orElseThrow(NullPointerException::new);
        detectorRepository.delete(detector);
        detector = getNewBuiltDetector(serialNumber);
        detectorRepository.save(detector);
    }

    @Override
    public List<Detector> getAll() {
        log.info("IN DetectorServiceImpl getAll() {}");
        return detectorRepository.findAll();
    }

    private Detector getNewBuiltDetector(String serialNumber) {
        log.info("IN DetectorServiceImpl getNewBuiltDetector() {}", serialNumber);
        return Detector
                .builder()
                .serialNumber(serialNumber)
                .state(State.NEW)
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
