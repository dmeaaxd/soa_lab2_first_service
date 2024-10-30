package ru.danmax.soa_lab2_first_service.datasource.repositories;

import ru.danmax.soa_lab2_first_service.datasource.DataBase;
import ru.danmax.soa_lab2_first_service.entities.Location;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;

public class LocationRepository {
    public static Location findById(long id) throws SQLException {
        Location location = null;
        Connection connection = DataBase.getConnection();

        try {
            ResultSet rs = connection.createStatement().executeQuery(
                    String.format("""
                                select * from locations
                                where locations.id = %d;
                            """, id)
            );
            location = (rs.next()) ? Location.createLocationFromResultSet(rs) : null;
        } catch (SQLException ignored) {
        }
        return location;
    }
}
