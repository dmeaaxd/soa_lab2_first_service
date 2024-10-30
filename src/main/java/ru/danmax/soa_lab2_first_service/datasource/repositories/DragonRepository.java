package ru.danmax.soa_lab2_first_service.datasource.repositories;

import ru.danmax.soa_lab2_first_service.entities.Dragon;
import ru.danmax.soa_lab2_first_service.datasource.DataBase;


import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import static ru.danmax.soa_lab2_first_service.datasource.repositories.additional.AdditionalMethods.*;

public class DragonRepository {

    public static List<Dragon> findAll(String sort, String filter) {
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
                if (dragon != null) {
                    dragons.add(dragon);
                }
            }
        } catch (SQLException ignored) {
        }
        return dragons;
    }

    public static Dragon findById(long id) throws SQLException {
        Dragon dragon = null;
        Connection connection = DataBase.getConnection();

        try {
            ResultSet rs = connection.createStatement().executeQuery(
                    String.format("""
                                select * from dragons
                                where dragons.id = %d;
                            """, id)
            );
            dragon = (rs.next()) ? createDragonFromResultSet(rs) : null;
        } catch (SQLException ignored) {}

        return dragon;
    }

    private static Dragon createDragonFromResultSet(ResultSet rs){
        Dragon dragon = null;
        try {
            dragon = Dragon.createRawDragonFromResultSet(rs);

            if (dragon != null && rs.getObject("coordinates_id") != null) {
                dragon.setCoordinates(CoordinatesRepository.findById(rs.getLong("coordinates_id")));
            }

            if (dragon != null && rs.getObject("killer_id") != null) {
                dragon.setKiller(PersonRepository.findById(rs.getLong("killer_id")));
            }
        } catch (SQLException ignored) {}

        return dragon;
    }
}