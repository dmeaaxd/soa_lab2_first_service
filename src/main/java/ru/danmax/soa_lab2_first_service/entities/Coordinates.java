package ru.danmax.soa_lab2_first_service.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.Max;
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
                    id INT PRIMARY KEY AUTO_INCREMENT,
                    x INT NOT NULL,
                    y INT NOT NULL
                );""", getTableName());
    }
}
