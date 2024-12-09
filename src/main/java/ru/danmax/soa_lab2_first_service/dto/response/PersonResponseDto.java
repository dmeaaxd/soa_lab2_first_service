package ru.danmax.soa_lab2_first_service.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.danmax.soa_lab2_first_service.entity.Person;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PersonResponseDto {
    private Integer id;
    private String name;
    private String passportId;
    private LocationResponseDto location;

    public static PersonResponseDto toDTO(Person person) {
        if (person == null) return null;
        return PersonResponseDto.builder()
                .id(person.getId())
                .name(person.getName())
                .passportId(person.getPassportId())
                .location(
                        LocationResponseDto.builder()
                                .x(person.getLocation().getX())
                                .y(person.getLocation().getY())
                                .z(person.getLocation().getZ())
                                .name(person.getLocation().getLocationName())
                                .build()
                )
                .build();
    }

    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    static class LocationResponseDto {
        private Integer x;
        private Double y;
        private Integer z;
        private String name;
    }
}
