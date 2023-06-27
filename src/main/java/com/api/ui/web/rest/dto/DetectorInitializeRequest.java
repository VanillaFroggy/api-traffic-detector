package com.api.ui.web.rest.dto;

import com.api.infrustructure.persistance.entity.Detector;
import lombok.Data;

@Data
public class DetectorInitializeRequest {
    private String serialNumber;
    private String model;
    private Detector.ConformityCertificate conformityCertificate;
}
