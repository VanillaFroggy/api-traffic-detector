package com.api.infrastructure.persistance.entity;

import jakarta.validation.constraints.Size;
import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
@RequiredArgsConstructor
public class GpsCoordinate {
    @Size(min = -90)
    private final Double latitude;

    @Size(min = -180, max = 180)
    private final Double longitude;
}
