package ru.danmax.soa_lab2_first_service.entities;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.ResultSet;
import java.sql.SQLException;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Person implements Entity{
    private Integer id;
    private String name; //Поле не может быть null, Строка не может быть пустой
    private String passportId; //Длина строки должна быть не меньше 10, Длина строки не должна быть больше 32, Поле может быть null
    private Location location; //Поле не может быть null

    @Override
    public String getTableName() {
        return "persons";
    }

    public static Person createRawPersonFromResultSet(ResultSet rs) throws SQLException {
        return Person.builder()
                .id(rs.getInt("id"))
                .name(rs.getString("name"))
                .passportId(rs.getString("passport_id"))
                .build();
    }
}
