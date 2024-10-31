package ru.danmax.soa_lab2_first_service.dto.request;

import jakarta.validation.constraints.*;
import lombok.Data;
import ru.danmax.soa_lab2_first_service.dto.validators.ValueOfEnum;
import ru.danmax.soa_lab2_first_service.entities.Coordinates;
import ru.danmax.soa_lab2_first_service.entities.enums.*;

@Data
public class DragonRequestDto {
    @NotNull
    @NotBlank(message = "Name не может быть пустым")
    @Size(max = 255, message = "Name не может превышать длину 255 символов")
    private String name;

    @NotNull(message = "Coordinates не может быть пустым")
    private Coordinates coordinates;

    @NotNull(message = "Age не может быть пустым")
    @Min(value = 1, message = "Age не может быть меньше 1")
    private int age;

    @ValueOfEnum(enumClass = Color.class, message = "color должно быть одним из enum Color")
    private String color;

    @ValueOfEnum(enumClass = DragonType.class, message = "type должно быть одним из enum DragonType")
    private String type;

    @ValueOfEnum(enumClass = DragonCharacter.class, message = "character должно быть одним из enum DragonCharacter")
    private String character;
}
