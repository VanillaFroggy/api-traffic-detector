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
public class Point {
//    @Id
//    @GeneratedValue
//    private Long id;

    @Size(max = 3840)
    private Integer x;

    @Size(max = 2160)
    private Integer y;
}
