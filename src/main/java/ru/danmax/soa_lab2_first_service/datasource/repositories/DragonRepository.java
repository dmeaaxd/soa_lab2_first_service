package ru.danmax.soa_lab2_first_service.datasource.repositories;

import ru.danmax.soa_lab2_first_service.entities.Dragon;
import ru.danmax.soa_lab2_first_service.datasource.DataBase;


import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import static ru.danmax.soa_lab2_first_service.datasource.repositories.additional.AdditionalMethods.*;

public class DragonRepository {

    public static List<Dragon> findAll(String sort, String filter, Integer page, Integer size) throws SQLException, IllegalArgumentException{
        Connection conn = DataBase.getConnection();
        String sql = String.format("SELECT * FROM %s", new Dragon().getTableName());

        if (filter != null && !filter.isEmpty()) {
            sql += " WHERE " + parseFilter(filter);
        }

        if (sort != null && !sort.isEmpty()) {
            sql += " ORDER BY " + parseSort(sort);
        }

        if (size != null && checkSize(size)) {
            sql += " LIMIT " + size;

            if (page != null && checkPage(page)) {
                sql += " OFFSET " + page*size;
            }
        }

        sql += ";";

        ResultSet rs = conn.createStatement().executeQuery(sql);

        List<Dragon> dragons = new ArrayList<>();
        while (rs.next()) {
            Dragon dragon = createDragonFromResultSet(rs);
            if (dragon != null) {
                dragons.add(dragon);
            }
        }

        return dragons;
    }

    public static Dragon findById(int id) throws SQLException {
        Dragon dragon = null;
        Connection connection = DataBase.getConnection();

        try {
            ResultSet rs = connection.createStatement().executeQuery(
                    String.format("""
                                select * from %s
                                where dragons.id = %d;
                            """, new Dragon().getTableName(), id)
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
                dragon.setCoordinates(CoordinatesRepository.findById(rs.getInt("coordinates_id")));
            }

            if (dragon != null && rs.getObject("killer_id") != null) {
                dragon.setKiller(PersonRepository.findById(rs.getInt("killer_id")));
            }
        } catch (SQLException ignored) {}

        return dragon;
    }
}