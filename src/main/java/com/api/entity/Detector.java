package com.api.entity;

//import jakarta.persistence.*;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@RequiredArgsConstructor
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id")
//@Entity()
public class Detector {
//    @Id
//    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private final Long id;

    private State state;

    @Pattern(regexp = "^[\\w-]{6,50}$")
    private String serialNumber;

    @Size(min = 1, max = 50)
    private String model;

    @Size(max = 512)
    private String address;

    //    @OneToOne
    private GpsCoordinate gpsCoordinate;

    //    @OneToOne
    private Zone zone;
}


