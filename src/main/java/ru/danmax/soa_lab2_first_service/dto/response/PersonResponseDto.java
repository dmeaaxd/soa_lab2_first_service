package ru.danmax.soa_lab2_first_service.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.danmax.soa_lab2_first_service.entities.Person;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PersonResponseDto {
    private Integer id;
    private String name;
    private String passportId;
    private LocationResponseDto location;

    public static PersonResponseDto convertToDTO(Person person) {
        if (person == null) return null;
        return PersonResponseDto.builder()
                .id(person.getId())
                .name(person.getName())
                .passportId(person.getPassportId())
                .location(LocationResponseDto.convertToDTO(person.getLocation()))
                .build();
    }
}
