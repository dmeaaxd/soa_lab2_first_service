package ru.danmax.soa_lab2_first_service.datasource.repositories;

import ru.danmax.soa_lab2_first_service.datasource.DataBase;
import ru.danmax.soa_lab2_first_service.entities.Person;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;

public class PersonRepository {
    public static Person findById(long id) throws SQLException {
        Person person = null;
        Connection connection = DataBase.getConnection();

        try {
            ResultSet rs = connection.createStatement().executeQuery(
                    String.format("""
                                select * from persons
                                where persons.id = %d;
                            """, id)
            );
            person = (rs.next()) ? Person.createRawPersonFromResultSet(rs) : null;
            if (person != null && rs.getObject("location_id") != null){
                person.setLocation(LocationRepository.findById(rs.getLong("location_id")));
            }
        } catch (SQLException ignored) {
        }
        return person;
    }
}
