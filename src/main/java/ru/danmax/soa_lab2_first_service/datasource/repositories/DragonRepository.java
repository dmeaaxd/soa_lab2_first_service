package ru.danmax.soa_lab2_first_service.datasource.repositories;

import ru.danmax.soa_lab2_first_service.datasource.DataBase;
import ru.danmax.soa_lab2_first_service.entities.Dragon;

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

    public static Dragon findById(int id) throws SQLException{
        Connection connection = DataBase.getConnection();
        ResultSet rs = connection.createStatement().executeQuery(
                String.format("""
                                select * from %s
                                where dragons.id = %d;
                            """, new Dragon().getTableName(), id)
        );
        return (rs.next()) ? createDragonFromResultSet(rs) : null;
    }

    public static Dragon insert(Dragon dragon) throws SQLException, IllegalArgumentException {
        if (dragon == null) {
            throw new IllegalArgumentException("Dragon cannot be null");
        }
        Connection connection = DataBase.getConnection();

        Dragon resultDragon = null;

        String query = "INSERT INTO " + dragon.getTableName() + " (name, coordinates_id, age, color, dragon_type, character, killer_id) VALUES (?, ?, ?, ?::color, ?::dragontype, ?::dragoncharacter, ?)";
        PreparedStatement statement = connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
        statement.setString(1, dragon.getName());
        statement.setInt(2, dragon.getCoordinates().getId());
        statement.setInt(3, dragon.getAge());
        statement.setString(4, dragon.getColor() != null ? dragon.getColor().toString() : null);
        statement.setString(5, dragon.getDragonType() != null ? dragon.getDragonType().toString() : null);
        statement.setString(6, dragon.getCharacter() != null ? dragon.getCharacter().toString() : null);

        if (dragon.getKiller() != null) statement.setInt(7, dragon.getKiller().getId());
        else statement.setNull(7, Types.INTEGER);


        int affectedRows = statement.executeUpdate();

        if (affectedRows > 0) {
            try (ResultSet generatedKeys = statement.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    int id = generatedKeys.getInt(1);
                    System.out.println("Inserted dragon record's ID: " + id);
                    resultDragon = DragonRepository.findById(id);
                }
            }
        }
        else {
            throw new SQLException("Could not insert dragon record");
        }
        return resultDragon;

    }

    public static Dragon update(Dragon dragon) throws SQLException, IllegalArgumentException{
        if (dragon == null) {
            throw new IllegalArgumentException("Dragon cannot be null");
        }
        Connection connection = DataBase.getConnection();

        String query = "UPDATE " + dragon.getTableName() + " SET \n";
        query += "\"name\" = " + dragon.getName() + ", ";
        query += "age = " + dragon.getAge() + ", ";
        query += "color = " + dragon.getColor() + ", ";
        query += "dragon_type = " + dragon.getDragonType() + ", ";
        query += "character = " + dragon.getCharacter() + ", ";
        query += "killer_id = " + dragon.getKiller().getId() + ", ";

        connection.createStatement().execute(query);
        return findById(dragon.getId());
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