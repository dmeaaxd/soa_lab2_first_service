package ru.danmax.soa_lab2_first_service.datasource.repositories;

import ru.danmax.soa_lab2_first_service.entities.Dragon;
import ru.danmax.soa_lab2_first_service.datasource.DataBase;
import ru.danmax.soa_lab2_first_service.entities.enums.Color;
import ru.danmax.soa_lab2_first_service.entities.enums.DragonType;
import ru.danmax.soa_lab2_first_service.entities.enums.DragonCharacter;


import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class DragonRepository {

    public List<Dragon> findAll(String sort, String filter) {
        List<Dragon> dragons = new ArrayList<>();
        String sql = "SELECT * FROM dragons";

        if (filter != null && !filter.isEmpty()) {
            sql += " WHERE name ILIKE ?";
        }

        if (sort != null && !sort.isEmpty()) {
            sql += " ORDER BY " + sort.replace("-", "") + (sort.startsWith("-") ? " DESC" : " ASC");
        }

        try (Connection conn = DataBase.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            if (filter != null && !filter.isEmpty()) {
                stmt.setString(1, "%" + filter + "%");
            }

            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                Dragon dragon = Dragon.builder()
                        .id(rs.getLong("id"))
                        .name(rs.getString("name"))
                        .creationDate(rs.getTimestamp("creation_date").toLocalDateTime())
                        .age(rs.getInt("age"))
                        .color(Color.valueOf(rs.getString("color")))
                        .dragonType(DragonType.valueOf(rs.getString("dragon_type")))
                        .character(DragonCharacter.valueOf(rs.getString("character")))
                        .build();
                dragons.add(dragon);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return dragons;
    }
}