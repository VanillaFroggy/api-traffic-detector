package com.api.dto;

import com.api.entity.State;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;

@Data
@NoArgsConstructor
@AllArgsConstructor
public final class DetectorResetDTO {
        @Id
        @Pattern(regexp = "^[\\w-]{6,50}")
        private String serialNumber;

        private State state;
}
