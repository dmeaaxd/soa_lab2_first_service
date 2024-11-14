package ru.danmax.soa_lab2_first_service.dto.request;

import jakarta.validation.constraints.NotNull;
import lombok.Data;
import ru.danmax.soa_lab2_first_service.dto.response.DragonResponseDto;
import ru.danmax.soa_lab2_first_service.entities.Coordinates;
import ru.danmax.soa_lab2_first_service.entities.Dragon;

@Data
public class CoordinatesRequestDto {
    @NotNull
    private int x;

    private float y;

    public static Coordinates convertToObject(CoordinatesRequestDto coordinatesRequestDto) {
        if (coordinatesRequestDto == null) return null;
        return Coordinates.builder()
                .x(coordinatesRequestDto.getX())
                .y(coordinatesRequestDto.getY())
                .build();
    }
}
