package com.api.infrustructure.persistance.entity;

import jakarta.validation.constraints.Size;
import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
@RequiredArgsConstructor
public class Point {
    @Size(max = 3840)
    private final Integer x;

    @Size(max = 2160)
    private final Integer y;
}
