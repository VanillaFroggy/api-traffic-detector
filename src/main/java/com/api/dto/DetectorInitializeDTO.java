package com.api.dto;

import com.api.entity.Detector;
import com.api.entity.State;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;

@Data
@NoArgsConstructor
@AllArgsConstructor
public final class DetectorInitializeDTO {
        @Id
        @Pattern(regexp = "^[\\w-]{6,50}")
        private String serialNumber;

        private State state;

        @Size(min = 1, max = 50)
        private String model;

        private Detector.ConformityCertificate conformityCertificate;
}
