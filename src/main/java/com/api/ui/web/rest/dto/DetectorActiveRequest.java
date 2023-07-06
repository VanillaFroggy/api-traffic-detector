package com.api.ui.web.rest.dto;

import com.api.infrastructure.persistance.entity.GpsCoordinate;
import com.api.infrastructure.persistance.entity.Zone;
import lombok.Data;

@Data
public class DetectorActiveRequest {
    private String address;
    private GpsCoordinate location;
    private Zone zone;
}
