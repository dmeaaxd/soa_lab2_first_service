package ru.danmax.soa_lab2_first_service.services;

import ru.danmax.soa_lab2_first_service.datasource.repositories.DragonRepository;
import ru.danmax.soa_lab2_first_service.entities.Dragon;
import ru.danmax.soa_lab2_first_service.entities.enums.DragonCharacter;

import java.sql.SQLException;
import java.util.List;


// TODO: реализовать логику
public class DragonService {


    public static List<Dragon> getDragons(String sort, String filter, Integer page, Integer size) throws SQLException, IllegalArgumentException {
        return DragonRepository.findAll(sort, filter, page, size);
    }


    public void addDragon(Dragon dragon) {
    }


    public Dragon getDragonById(Long id) {
        return null;
    }

    public void updateDragon(Long id, Dragon dragon) {
    }

    public void deleteDragon(Long id) {
    }

    public List<Dragon> searchByName(String name) {
        return null;
    }


    public List<Dragon> filterByKiller(Long passportId) {
        return null;
    }

    public List<Dragon> filterByCharacter(DragonCharacter character) {
        return null;
    }
}
