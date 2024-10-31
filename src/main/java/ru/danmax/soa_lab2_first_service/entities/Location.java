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
public class Location implements Entity {
    private int id;
    private float x;
    private float y; //Поле не может быть null
    private float z;
    private String name; //Строка не может быть пустой, Поле не может быть null

    @Override
    public String getTableName() {
        return "locations";
    }

    public static Location createLocationFromResultSet(ResultSet rs) throws SQLException {
        return Location.builder()
                .id(rs.getInt("id"))
                .x(rs.getFloat("x"))
                .y(rs.getFloat("y"))
                .z(rs.getFloat("z"))
                .name(rs.getString("name"))
                .build();
    }
}
