package ru.danmax.soa_lab2_first_service.converter;

import ru.danmax.soa_lab2_first_service.dto.response.PersonResponseDto;
import ru.danmax.soa_lab2_first_service.entity.Person;

public class PersonConverter {
    public static PersonResponseDto convertToPersonResponseDto(Person person) {
        if (person == null) return null;
        return PersonResponseDto.builder()
                .id(person.getId())
                .name(person.getName())
                .passportId(person.getPassportId())
                .location(
                        PersonResponseDto.LocationResponseDto.builder()
                                .x(person.getLocation().getX())
                                .y(person.getLocation().getY())
                                .z(person.getLocation().getZ())
                                .name(person.getLocation().getName())
                                .build()
                )
                .build();
    }
}
