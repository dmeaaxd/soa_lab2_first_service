package ru.danmax.soa_lab2_first_service.datasource.repositories;

import ru.danmax.soa_lab2_first_service.datasource.DataBase;
import ru.danmax.soa_lab2_first_service.entities.Coordinates;

import java.sql.*;

public class CoordinatesRepository {
    public static Coordinates findById(long id) throws SQLException {
        Coordinates coordinates = null;
        Connection connection = DataBase.getConnection();

        try {
            ResultSet rs = connection.createStatement().executeQuery(
                    String.format("""
                                select * from coordinates
                                where coordinates.id = %d;
                            """, id)
            );
            coordinates = (rs.next()) ? Coordinates.createCoordinatesFromResultSet(rs) : null;
        } catch (SQLException ignored) {
        }
        return coordinates;
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
                    System.out.println("Inserted coordinates record's ID: " + id);
                    resultCoordinates = CoordinatesRepository.findById(id);
                }
            }
        }
        else {
            throw new SQLException("Could not insert coordinates record");
        }
        return resultCoordinates;
    }
}
