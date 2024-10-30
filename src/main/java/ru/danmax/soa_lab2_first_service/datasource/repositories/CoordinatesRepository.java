package ru.danmax.soa_lab2_first_service.datasource.repositories;

import ru.danmax.soa_lab2_first_service.datasource.DataBase;
import ru.danmax.soa_lab2_first_service.entities.Coordinates;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;

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
}
