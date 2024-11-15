package ru.danmax.soa_lab2_first_service.services;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import ru.danmax.soa_lab2_first_service.datasource.DataBase;
import ru.danmax.soa_lab2_first_service.datasource.repositories.CoordinatesRepository;
import ru.danmax.soa_lab2_first_service.datasource.repositories.DragonRepository;
import ru.danmax.soa_lab2_first_service.dto.request.DragonRequestDto;
import ru.danmax.soa_lab2_first_service.dto.response.DragonResponseDto;
import ru.danmax.soa_lab2_first_service.entities.Coordinates;
import ru.danmax.soa_lab2_first_service.entities.Dragon;
import ru.danmax.soa_lab2_first_service.entities.enums.DragonCharacter;
import ru.danmax.soa_lab2_first_service.exceptions.EntityAlreadyExists;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
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
            dragon.setCoordinates(CoordinatesRepository.insert(dragon.getCoordinates()));

            // Сохранить дракона в БД
            DragonRepository.insert(dragon);

            connection.commit();
        } catch (SQLException | IllegalArgumentException exception) {
            connection.rollback();
            connection.setAutoCommit(true);
            throw exception;
        }
    }


    public static DragonResponseDto getDragonById(Integer id) throws SQLException, NoSuchElementException {
        Dragon dragon = DragonRepository.findById(id);
        if (dragon == null) {
            throw new NoSuchElementException("Dragon not found");
        }
        return DragonResponseDto.convertToDTO(dragon);
    }

    public static void updateDragon(Integer id, DragonRequestDto dragonRequestDto) throws SQLException, IllegalArgumentException, NoSuchElementException {
        // Валидируем пришедшие данные
        Validator validator = Validation.buildDefaultValidatorFactory().getValidator();
        Set<ConstraintViolation<DragonRequestDto>> validationResult = validator.validate(dragonRequestDto);
        for (ConstraintViolation<DragonRequestDto> violation : validationResult) {
            throw new IllegalArgumentException(violation.getMessage());
        }

        // Подготавливаем дракона
        Dragon updatedDragon = DragonRequestDto.convertToObject(dragonRequestDto);
        updatedDragon.setId(id);

        // Подготавливаем новые координаты
        Coordinates updatedCoordinates = updatedDragon.getCoordinates();
        Dragon existDragon = DragonRepository.findById(updatedDragon.getId());
        if (existDragon == null) {
            throw new NoSuchElementException("Dragon not found");
        }

        int coordinatesId = existDragon.getCoordinates().getId();
        updatedCoordinates.setId(coordinatesId);

        // Транзакция обновления дракона в БД
        Connection connection = DataBase.getConnection();
        try {
            connection.setAutoCommit(false);

            // Обновляем координаты дракона в БД
            updatedCoordinates = CoordinatesRepository.update(updatedCoordinates);
            if (updatedCoordinates == null){
                throw new NoSuchElementException("Coordinates not found");
            }

            // Обновляем дракона в БД
            updatedDragon = DragonRepository.update(updatedDragon);
            if (updatedDragon == null){
                throw new NoSuchElementException("Dragon not found");
            }

            connection.commit();
        } catch (SQLException | IllegalArgumentException | NoSuchElementException exception) {
            connection.rollback();
            connection.setAutoCommit(true);
            throw exception;
        }
    }

    public static void deleteDragon(Integer id) throws SQLException, NoSuchElementException {
        // Получаем дракона
        Dragon dragon = DragonRepository.findById(id);
        if (dragon == null) {
            throw new NoSuchElementException("Dragon not found");
        }

        // Получаем идентификатор координат
        int coordinatesId = dragon.getCoordinates().getId();

        // Транзакция удаления
        Connection connection = DataBase.getConnection();
        try {
            connection.setAutoCommit(false);

            CoordinatesRepository.delete(coordinatesId);
            DragonRepository.delete(id);

            connection.commit();
        } catch (SQLException | NoSuchElementException exception) {
            connection.rollback();
            connection.setAutoCommit(true);
            throw exception;
        }
    }

    public static List<DragonResponseDto> searchByName(String name) throws SQLException {
        List<Dragon> dragons = DragonRepository.findAllByNameSubstring(name);
        List<DragonResponseDto> dragonResponseDtos = new ArrayList<>();
        for (Dragon dragon : dragons) {
            dragonResponseDtos.add(DragonResponseDto.convertToDTO(dragon));
        }
        return dragonResponseDtos;
    }


    public static List<DragonResponseDto> filterByKiller(String passportId) throws SQLException, IllegalArgumentException {
        if (passportId == null) {
            throw new IllegalArgumentException("passportId является обязательным параметром");
        }

        List<Dragon> dragons = DragonRepository.findAllFilterByKiller(passportId);
        List<DragonResponseDto> dragonResponseDtos = new ArrayList<>();
        for (Dragon dragon : dragons) {
            dragonResponseDtos.add(DragonResponseDto.convertToDTO(dragon));
        }
        return dragonResponseDtos;
    }

    public static List<DragonResponseDto> filterByCharacter(String character) throws IllegalArgumentException, SQLException {
        if (character == null) {
            throw new IllegalArgumentException("character является обязательным параметром");
        }
        try{
            List<Dragon> dragons = DragonRepository.findAllFilterByCharacter(DragonCharacter.valueOf(character));
            List<DragonResponseDto> dragonResponseDtos = new ArrayList<>();
            for (Dragon dragon : dragons) {
                dragonResponseDtos.add(DragonResponseDto.convertToDTO(dragon));
            }
            return dragonResponseDtos;
        } catch (IllegalArgumentException illegalArgumentException){
            throw new IllegalArgumentException("character должно быть одним из enum DragonCharacter");
        }

    }
}
