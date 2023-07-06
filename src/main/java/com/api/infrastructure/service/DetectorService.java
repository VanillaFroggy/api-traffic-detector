package com.api.infrastructure.service;

import com.api.infrastructure.persistance.entity.Detector;
import com.api.infrastructure.service.dto.DetectorActivateDTO;
import com.api.infrastructure.service.dto.DetectorInitializeDTO;
import jakarta.validation.Valid;

import java.util.List;

public interface DetectorService {
    Detector getDetectorBySerialNumber(@Valid String serialNumber);

    void initialize(@Valid DetectorInitializeDTO dto);

    void activate(@Valid DetectorActivateDTO dto);

    void setup(String serialNumber);

    void reset(String serialNumber);

    List<Detector> getAll();
}
