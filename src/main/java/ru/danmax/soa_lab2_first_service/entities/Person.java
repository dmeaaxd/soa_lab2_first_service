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
    private Long id;
    private String name;
    private String passportId;
    private Location location;

    @Override
    public String getTableName() {
        return "persons";
    }

    @Override
    public String getSqlCreateTableScript() {
        return String.format("""
                CREATE TABLE %s (
                    id SERIAL PRIMARY KEY,
                    name VARCHAR(255) NOT NULL,
                    passport_id VARCHAR(32) NOT NULL,
                    location_id INT NOT NULL,
                    FOREIGN KEY (location_id) REFERENCES %s (id) ON DELETE CASCADE,
                    CONSTRAINT person_chk_passport CHECK (char_length(passport_id) > 9 AND char_length(passport_id) < 33)
                );""", getTableName(), new Location().getTableName());
    }

    public static Person createRawPersonFromResultSet(ResultSet rs) throws SQLException {
        return Person.builder()
                .id(rs.getLong("id"))
                .name(rs.getString("name"))
                .passportId(rs.getString("passport_id"))
                .build();
    }
}
