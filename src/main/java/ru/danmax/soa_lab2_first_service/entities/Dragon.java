package ru.danmax.soa_lab2_first_service.entities;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.danmax.soa_lab2_first_service.entities.enums.DragonCharacter;
import ru.danmax.soa_lab2_first_service.entities.enums.DragonType;
import ru.danmax.soa_lab2_first_service.entities.enums.Color;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Dragon implements Entity {
    private int id; //Поле не может быть null, Значение поля должно быть больше 0, Значение этого поля должно быть уникальным, Значение этого поля должно генерироваться автоматически
    private String name; //Поле не может быть null, Строка не может быть пустой
    private Coordinates coordinates; //Поле не может быть null
    private LocalDateTime creationDate; //Поле не может быть null, Значение этого поля должно генерироваться автоматически
    private int age; //Значение поля должно быть больше 0, Поле не может быть null
    private Color color; //Поле может быть null
    private DragonType dragonType; //Поле может быть null
    private DragonCharacter character; //Поле может быть null
    private Person killer; //Поле может быть null

    @Override
    public String getTableName() {
        return "dragons";
    }

    public static Dragon createRawDragonFromResultSet(ResultSet rs) throws SQLException {
        return Dragon.builder()
                .id(rs.getInt("id"))
                .name(rs.getString("name"))
                .creationDate(rs.getTimestamp("creation_date").toLocalDateTime())
                .age(rs.getInt("age"))
                .color(Color.valueOf(rs.getString("color").trim()))
                .dragonType(DragonType.valueOf(rs.getString("dragon_type").trim()))
                .character(DragonCharacter.valueOf(rs.getString("character").trim()))
                .build();
    }
}

