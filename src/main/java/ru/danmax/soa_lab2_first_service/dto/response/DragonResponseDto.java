package ru.danmax.soa_lab2_first_service.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.danmax.soa_lab2_first_service.entities.Dragon;
import ru.danmax.soa_lab2_first_service.entities.enums.*;

import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class DragonResponseDto {
    private int id;
    private String name;
    private CoordinatesResponseDto coordinates;
    private LocalDateTime creationDate;
    private int age;
    private Color color;
    private DragonType dragonType;
    private DragonCharacter dragonCharacter;
    private PersonResponseDto killer;

    public static DragonResponseDto convertToDTO(Dragon dragon) {
        return DragonResponseDto.builder()
                .id(dragon.getId())
                .name(dragon.getName())
                .coordinates(CoordinatesResponseDto.convertToDTO(dragon.getCoordinates()))
                .creationDate(dragon.getCreationDate())
                .age(dragon.getAge())
                .color(dragon.getColor())
                .dragonType(dragon.getDragonType())
                .dragonCharacter(dragon.getCharacter())
                .killer(PersonResponseDto.convertToDTO(dragon.getKiller()))
                .build();
    }

}
