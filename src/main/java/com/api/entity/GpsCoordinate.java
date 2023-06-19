package com.api.entity;

//import jakarta.persistence.Entity;
//import jakarta.persistence.GeneratedValue;
//import jakarta.persistence.Id;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
//@NoArgsConstructor
@AllArgsConstructor
@RequiredArgsConstructor
//@Entity
public class GpsCoordinate {
//    @Id
//    @GeneratedValue
//    private Long id;

    @Size(min = -90)
    private Double latitude;

    @Size(min = -180, max = 180)
    private Double longitude;
}
