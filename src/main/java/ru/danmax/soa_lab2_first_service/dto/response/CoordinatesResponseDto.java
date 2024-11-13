package ru.danmax.soa_lab2_first_service.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.danmax.soa_lab2_first_service.entities.Coordinates;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CoordinatesResponseDto {
    private int x;
    private float y;

    public static CoordinatesResponseDto convertToDTO(Coordinates coordinates) {
        if (coordinates == null) return null;
        return CoordinatesResponseDto.builder()
                .x(coordinates.getX())
                .y(coordinates.getY())
                .build();
    }
}
