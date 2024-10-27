package ru.danmax.soa_lab2_first_service.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Location implements Entity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private int x;

    @Column(nullable = false)
    private double y;

    @Column(nullable = false)
    private int z;

    @Column(nullable = false)
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
}
