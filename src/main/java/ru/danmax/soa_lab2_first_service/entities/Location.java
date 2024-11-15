package ru.danmax.soa_lab2_first_service.entities;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.zip.DeflaterOutputStream;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Location implements Entity {
    private Integer id;
    private Integer x;
    private Double y; //Поле не может быть null
    private Integer z;
    private String name; //Строка не может быть пустой, Поле не может быть null

    @Override
    public String getTableName() {
        return "locations";
    }

    public static Location createLocationFromResultSet(ResultSet rs) throws SQLException {
        return Location.builder()
                .id(rs.getInt("id"))
                .x(rs.getInt("x"))
                .y(rs.getDouble("y"))
                .z(rs.getInt("z"))
                .name(rs.getString("name"))
                .build();
    }
}
