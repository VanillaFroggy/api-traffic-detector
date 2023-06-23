package com.api.dto;


import com.api.entity.GpsCoordinate;
import com.api.entity.State;
import com.api.entity.Zone;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;


@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public final class DetectorActivateDTO {
        @Id
        @Pattern(regexp = "^[\\w-]{6,50}$")
        private String serialNumber;

        private State state;

        @Size(max = 512)
        private String address;

        private GpsCoordinate location;

        private Zone zone;
}
