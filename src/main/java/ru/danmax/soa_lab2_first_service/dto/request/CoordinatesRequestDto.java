package ru.danmax.soa_lab2_first_service.dto.request;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.danmax.soa_lab2_first_service.entities.Coordinates;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CoordinatesRequestDto {
    @NotNull
    private Integer x;

    private Float y;

    public static Coordinates convertToObject(CoordinatesRequestDto coordinatesRequestDto) {
        if (coordinatesRequestDto == null) return null;
        return Coordinates.builder()
                .x(coordinatesRequestDto.getX())
                .y(coordinatesRequestDto.getY())
                .build();
    }
}
