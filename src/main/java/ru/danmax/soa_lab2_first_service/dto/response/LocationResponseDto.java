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
    private Integer x;
    private Double y;
    private Integer z;
    private String name;

    public static LocationResponseDto convertToDTO(Location location) {
        if (location == null) return null;
        return LocationResponseDto.builder()
                .x(location.getX())
                .y(location.getY())
                .z(location.getZ())
                .name(location.getName())
                .build();
    }
}
