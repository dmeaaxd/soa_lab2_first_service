package ru.danmax.soa_lab2_first_service.services;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import ru.danmax.soa_lab2_first_service.datasource.DataBase;
import ru.danmax.soa_lab2_first_service.datasource.repositories.CoordinatesRepository;
import ru.danmax.soa_lab2_first_service.datasource.repositories.DragonRepository;
import ru.danmax.soa_lab2_first_service.dto.request.DragonRequestDto;
import ru.danmax.soa_lab2_first_service.dto.response.DragonResponseDto;
import ru.danmax.soa_lab2_first_service.entities.Dragon;
import ru.danmax.soa_lab2_first_service.entities.enums.DragonCharacter;
import ru.danmax.soa_lab2_first_service.exceptions.EntityAlreadyExists;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;


// TODO: реализовать логику
public class DragonService {


    public static List<DragonResponseDto> getDragons(String sort, String filter, Integer page, Integer size) throws SQLException, IllegalArgumentException {
        List<Dragon> dragons = DragonRepository.findAll(sort, filter, page, size);
        List<DragonResponseDto> dragonResponseDtos = new ArrayList<>();
        for (Dragon dragon : dragons) {
            dragonResponseDtos.add(DragonResponseDto.convertToDTO(dragon));
        }
        return dragonResponseDtos;
    }


    public static void addDragon(DragonRequestDto dragonRequestDto) throws SQLException, IllegalArgumentException, EntityAlreadyExists {
        // Валидируем пришедшие данные
        Validator validator = Validation.buildDefaultValidatorFactory().getValidator();
        Set<ConstraintViolation<DragonRequestDto>> validationResult = validator.validate(dragonRequestDto);
        for (ConstraintViolation<DragonRequestDto> violation : validationResult) {
            throw new IllegalArgumentException(violation.getMessage());
        }

        //Конвертируем DTO в объект Dragon
        Dragon dragon = DragonRequestDto.convertToObject(dragonRequestDto);

        // Транзакция добавления дракона в БД
        Connection connection = DataBase.getConnection();
        try {
            connection.setAutoCommit(false);

            // Сохранить координаты дракона в БД
            if (dragon.getCoordinates().getId() == 0) {
                dragon.setCoordinates(CoordinatesRepository.insert(dragon.getCoordinates()));
            }

            // Сохранить дракона в БД
            DragonRepository.insert(dragon);

            connection.commit();
        } catch (SQLException | IllegalArgumentException exception) {
            connection.rollback();
            connection.setAutoCommit(true);
            throw exception;
        }
    }


    public static DragonResponseDto getDragonById(Integer id) throws SQLException {
        return DragonResponseDto.convertToDTO(DragonRepository.findById(id));
    }

    public static void updateDragon(Integer id, DragonRequestDto dragonRequestDto) {
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
