package com.api.ui.web.rest.dto;

import com.api.infrustructure.persistance.entity.GpsCoordinate;
import com.api.infrustructure.persistance.entity.Zone;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class DetectorActiveRequest {
    private String address;
    private GpsCoordinate location;
    private Zone zone;
}
