package com.api.entity;

import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
@AllArgsConstructor
@RequiredArgsConstructor
public class GpsCoordinate {
    @Size(min = -90)
    private Double latitude;

    @Size(min = -180, max = 180)
    private Double longitude;
}
