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
    private Long id;
    private int x;
    private int y;

    @Override
    public String getTableName() {
        return "coordinates";
    }

    @Override
    public String getSqlCreateTableScript() {
        return String.format("""
                CREATE TABLE %s (
                    id SERIAL PRIMARY KEY,
                    x INT NOT NULL,
                    y INT NOT NULL
                );""", getTableName());
    }

    public static Coordinates createCoordinatesFromResultSet(ResultSet rs) throws SQLException {
        return Coordinates.builder()
                .id(rs.getLong("id"))
                .x(rs.getInt("x"))
                .y(rs.getInt("y"))
                .build();
    }
}
