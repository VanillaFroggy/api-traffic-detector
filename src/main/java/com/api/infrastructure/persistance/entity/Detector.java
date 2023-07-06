package com.api.infrastructure.persistance.entity;

import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Builder;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;

@Data
@Builder
@Document("detectors")
public class Detector {
    @Id
    @Pattern(regexp = "^[\\w-]{6,50}$")
    private String serialNumber;

    private State state;

    @Size(min = 1, max = 50)
    private String model;

    @Size(max = 512)
    private String address;

    private final GpsCoordinate location;

    private final Zone zone;

    private final ConformityCertificate conformityCertificate;

    @Data
    public static class ConformityCertificate {
        @Size(max = 50)
        @Pattern(regexp = "^[0-9]$")
        public String number;

        @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
        public LocalDate expirationDate;
    }

    public boolean isNew() {
        return state.equals(State.NEW);
    }

    public boolean isSetup() {
        return state.equals(State.SETUP);
    }

    public boolean isActive() {
        return state.equals(State.ACTIVE);
    }
}


