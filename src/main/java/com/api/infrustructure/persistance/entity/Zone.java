package com.api.infrustructure.persistance.entity;

import jakarta.validation.constraints.Size;
import lombok.Data;
import lombok.RequiredArgsConstructor;

import java.util.List;

@Data
@RequiredArgsConstructor
public class Zone {
    private final GpsCoordinate gpsCoordinate;

    @Size(min = 1, max = 512)
    private String address;

    @Size(min = 2, max = 2)
    private final List<Point> vrpDetectionArea;
}
