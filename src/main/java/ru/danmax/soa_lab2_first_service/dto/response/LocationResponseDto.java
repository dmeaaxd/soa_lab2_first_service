package ru.danmax.soa_lab2_first_service.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.danmax.soa_lab2_first_service.entities.Location;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class LocationResponseDto {
    private float x;
    private float y;
    private float z;
    private String name;

    public static LocationResponseDto convertToDTO(Location location) {
        return LocationResponseDto.builder()
                .x(location.getX())
                .y(location.getY())
                .z(location.getZ())
                .name(location.getName())
                .build();
    }
}
