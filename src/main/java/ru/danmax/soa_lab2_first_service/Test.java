package ru.danmax.soa_lab2_first_service;

import ru.danmax.soa_lab2_first_service.datasource.repositories.DragonRepository;

import java.sql.SQLException;

public class Test {
    public static void main(String[] args) throws SQLException {
        String filters = "(id eq 10) and (name eq Dragon)";
        System.out.println(DragonRepository.findAll(null, null));
    }
}
