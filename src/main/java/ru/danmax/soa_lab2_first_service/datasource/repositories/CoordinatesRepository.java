package ru.danmax.soa_lab2_first_service.datasource.repositories;

import ru.danmax.soa_lab2_first_service.datasource.DataBase;
import ru.danmax.soa_lab2_first_service.entities.Coordinates;

import java.sql.*;

public class CoordinatesRepository {
    public static Coordinates findById(int id) throws SQLException {
        Connection connection = DataBase.getConnection();
        ResultSet rs = connection.createStatement().executeQuery(
                String.format("""
                                select * from coordinates
                                where coordinates.id = %d;
                            """, id)
        );
        return (rs.next()) ? Coordinates.createCoordinatesFromResultSet(rs) : null;
    }

    public static Coordinates insert(Coordinates coordinates) throws SQLException, IllegalArgumentException {
        if (coordinates == null) {
            throw new IllegalArgumentException("Coordinates cannot be null");
        }
        Connection connection = DataBase.getConnection();

        Coordinates resultCoordinates = null;

        String query = "INSERT INTO " + coordinates.getTableName() + " (x, y) VALUES (?, ?)";
        PreparedStatement statement = connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
        statement.setInt(1, coordinates.getX());
        statement.setFloat(2, coordinates.getY());
        int affectedRows = statement.executeUpdate();

        if (affectedRows > 0) {
            try (ResultSet generatedKeys = statement.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    int id = generatedKeys.getInt(1);
                    resultCoordinates = CoordinatesRepository.findById(id);
                }
            }
        }
        else {
            throw new SQLException("Could not insert coordinates record");
        }
        return resultCoordinates;
    }

    public static Coordinates update(Coordinates coordinates) throws SQLException, IllegalArgumentException {
        if (coordinates == null) {
            throw new IllegalArgumentException("Coordinates cannot be null");
        }
        Connection connection = DataBase.getConnection();

        String query = "UPDATE " + coordinates.getTableName() + " SET \n";
        query += "x = " + coordinates.getX() + ", ";
        query += "y = " + coordinates.getY();
        query += " WHERE id = " + coordinates.getId() + ";";

        connection.createStatement().execute(query);
        return findById(coordinates.getId());
    }

    public static void delete(Integer id) throws SQLException {
        Connection connection = DataBase.getConnection();
        connection.createStatement().execute("DELETE FROM " + new Coordinates().getTableName() + " WHERE id = " + id);
    }
}
