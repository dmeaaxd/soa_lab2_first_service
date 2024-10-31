package ru.danmax.soa_lab2_first_service.dto.request;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class CoordinatesRequestDto {
    @NotNull
    private int x;

    private float y;
}
