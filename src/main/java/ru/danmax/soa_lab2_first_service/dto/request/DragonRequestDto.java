package ru.danmax.soa_lab2_first_service.dto.request;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.danmax.soa_lab2_first_service.dto.validators.ValueOfEnum;
import ru.danmax.soa_lab2_first_service.entity.enums.Color;
import ru.danmax.soa_lab2_first_service.entity.enums.DragonCharacter;
import ru.danmax.soa_lab2_first_service.entity.enums.DragonType;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class DragonRequestDto {
    @NotNull
    @NotBlank(message = "Name не может быть пустым")
    @Size(max = 255, message = "Name не может превышать длину 255 символов")
    private String name;

    @NotNull(message = "Coordinates не может быть пустым")
    private CoordinatesRequestDto coordinates;

    @NotNull(message = "Age не может быть пустым")
    @Min(value = 1, message = "Age не может быть меньше 1")
    private Integer age;

    @ValueOfEnum(enumClass = Color.class, message = "color должно быть одним из enum Color")
    private String color;

    @ValueOfEnum(enumClass = DragonType.class, message = "type должно быть одним из enum DragonType")
    private String type;

    @ValueOfEnum(enumClass = DragonCharacter.class, message = "character должно быть одним из enum DragonCharacter")
    private String character;

    private Integer killer;

    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class CoordinatesRequestDto {
        @NotNull
        private Integer x;
        private Float y;
    }

}
