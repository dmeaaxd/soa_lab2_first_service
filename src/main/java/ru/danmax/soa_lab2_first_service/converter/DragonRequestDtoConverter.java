package ru.danmax.soa_lab2_first_service.converter;

import ru.danmax.soa_lab2_first_service.dto.request.DragonRequestDto;
import ru.danmax.soa_lab2_first_service.entity.Coordinates;
import ru.danmax.soa_lab2_first_service.entity.Dragon;
import ru.danmax.soa_lab2_first_service.entity.Person;
import ru.danmax.soa_lab2_first_service.entity.enums.Color;
import ru.danmax.soa_lab2_first_service.entity.enums.DragonCharacter;
import ru.danmax.soa_lab2_first_service.entity.enums.DragonType;

public class DragonRequestDtoConverter {
    public static Dragon convertToDragon(DragonRequestDto dragonRequestDto) {
        if (dragonRequestDto == null) return null;
        return Dragon.builder()
                .name(dragonRequestDto.getName())
                .coordinates(
                        Coordinates.builder()
                                .x(dragonRequestDto.getCoordinates().getX())
                                .y(dragonRequestDto.getCoordinates().getY()).build()
                )
                .age(dragonRequestDto.getAge())
                .color(dragonRequestDto.getColor() != null ? Color.valueOf(dragonRequestDto.getColor()) : null)
                .dragonType(dragonRequestDto.getType() != null ? DragonType.valueOf(dragonRequestDto.getType()) : null)
                .character(dragonRequestDto.getCharacter() != null ? DragonCharacter.valueOf(dragonRequestDto.getCharacter()) : null)
                .killer(dragonRequestDto.getKiller() != null ? Person.builder().id(dragonRequestDto.getKiller()).build() : null)
                .build();
    }
}
