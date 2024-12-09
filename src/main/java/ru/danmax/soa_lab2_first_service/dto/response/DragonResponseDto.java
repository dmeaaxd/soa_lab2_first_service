package ru.danmax.soa_lab2_first_service.dto.response;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.danmax.soa_lab2_first_service.entity.Dragon;
import ru.danmax.soa_lab2_first_service.entity.enums.Color;
import ru.danmax.soa_lab2_first_service.entity.enums.DragonCharacter;
import ru.danmax.soa_lab2_first_service.entity.enums.DragonType;

import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class DragonResponseDto {
    private Integer id;
    private String name;
    private CoordinatesResponseDto coordinates;
    private LocalDateTime creationDate;
    private Integer age;
    private Color color;
    private DragonType dragonType;
    private DragonCharacter dragonCharacter;
    private PersonResponseDto killer;

    public static DragonResponseDto toDTO(Dragon dragon) {
        if (dragon == null) return null;
        return DragonResponseDto.builder()
                .id(dragon.getId())
                .name(dragon.getName())
                .coordinates(
                        CoordinatesResponseDto
                                .builder()
                                .x(dragon.getCoordinates().getX())
                                .y(dragon.getCoordinates().getY())
                                .build()
                )
                .creationDate(dragon.getCreationDate())
                .age(dragon.getAge())
                .color(dragon.getColor())
                .dragonType(dragon.getDragonType())
                .dragonCharacter(dragon.getCharacter())
                .killer(PersonResponseDto.toDTO(dragon.getKiller()))
                .build();
    }

    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    static class CoordinatesResponseDto {
        private Integer x;
        private Float y;
    }

}
