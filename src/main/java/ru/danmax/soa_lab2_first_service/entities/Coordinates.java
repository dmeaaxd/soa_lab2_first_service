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
public class Coordinates implements Entity{
    private Integer id;
    private Integer x; //Поле не может быть null
    private Float y;

    @Override
    public String getTableName() {
        return "coordinates";
    }

    public static Coordinates createCoordinatesFromResultSet(ResultSet rs) throws SQLException {
        return Coordinates.builder()
                .id(rs.getInt("id"))
                .x(rs.getInt("x"))
                .y(rs.getFloat("y"))
                .build();
    }
}
