package ru.danmax.soa_lab2_first_service.entities;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

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
}
