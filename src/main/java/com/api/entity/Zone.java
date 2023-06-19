package com.api.entity;

//import jakarta.persistence.*;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;

import java.util.List;

@Data
//@NoArgsConstructor
@AllArgsConstructor
@RequiredArgsConstructor
//@Entity
public class Zone {
//    @Id
//    @GeneratedValue
//    private Long id;
//
//    @OneToOne
    private GpsCoordinate gpsCoordinate;

    @Size(min = 1, max = 512)
    private String address;

    @Size(min = 2, max = 2)
//    @OneToMany
    private List<Point> vrpDetectionArea;
}
