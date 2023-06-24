package com.api.infrustructure.service.dto;

import com.api.infrustructure.persistance.entity.Detector;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public final class DetectorInitializeDTO {
    @Pattern(regexp = "^[\\w-]{6,50}")
    private String serialNumber;

    @Size(min = 1, max = 50)
    private String model;

    private Detector.ConformityCertificate conformityCertificate;
}
