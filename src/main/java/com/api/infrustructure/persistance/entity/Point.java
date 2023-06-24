package com.api.infrustructure.persistance.entity;

import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
@AllArgsConstructor
@RequiredArgsConstructor
public class Point {
    @Size(max = 3840)
    private Integer x;

    @Size(max = 2160)
    private Integer y;
}
