package ru.danmax.soa_lab2_first_service.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
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

    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class CoordinatesResponseDto {
        private Integer x;
        private Float y;
    }

}
