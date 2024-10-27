package ru.danmax.soa_lab2_first_service.datasource.repositories;

import ru.danmax.soa_lab2_first_service.entities.Dragon;
import ru.danmax.soa_lab2_first_service.datasource.DataBase;
import ru.danmax.soa_lab2_first_service.entities.enums.Color;
import ru.danmax.soa_lab2_first_service.entities.enums.DragonType;
import ru.danmax.soa_lab2_first_service.entities.enums.DragonCharacter;


import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import static ru.danmax.soa_lab2_first_service.datasource.repositories.additional.AdditionalMethods.*;
import static ru.danmax.soa_lab2_first_service.entities.Dragon.createDragonFromResultSet;

public class DragonRepository {

    public List<Dragon> findAll(String sort, String filter) {
        List<Dragon> dragons = new ArrayList<>();
        StringBuilder sql = new StringBuilder("SELECT * FROM dragons");


        if (filter != null && !filter.isEmpty()) {
            sql.append(" WHERE ").append(parseFilter(filter));
        }

        if (sort != null && !sort.isEmpty()) {
            sql.append(" ORDER BY ").append(parseSort(sort));
        }


        try (Connection conn = DataBase.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql.toString())) {


            if (filter != null && !filter.isEmpty()) {
                setFilterParameters(stmt, filter);
            }

            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                Dragon dragon = createDragonFromResultSet(rs);
                dragons.add(dragon);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return dragons;
    }
}