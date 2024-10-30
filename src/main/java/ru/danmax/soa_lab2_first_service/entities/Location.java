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
    private Long id;
    private int x;
    private double y;
    private int z;
    private String name;

    @Override
    public String getTableName() {
        return "locations";
    }

    @Override
    public String getSqlCreateTableScript() {
        return String.format("""
                CREATE TABLE %s (
                    id SERIAL PRIMARY KEY,
                    x INT NOT NULL,
                    y INT NOT NULL,
                    z INT NOT NULL,
                    name VARCHAR(255) NOT NULL
                );""", getTableName());
    }

    public static Location createLocationFromResultSet(ResultSet rs) throws SQLException {
        return Location.builder()
                .id(rs.getLong("id"))
                .x(rs.getInt("x"))
                .y(rs.getInt("y"))
                .z(rs.getInt("z"))
                .name(rs.getString("name"))
                .build();
    }
}
