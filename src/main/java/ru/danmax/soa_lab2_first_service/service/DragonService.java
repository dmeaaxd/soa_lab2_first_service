package ru.danmax.soa_lab2_first_service.service;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.ws.rs.BadRequestException;
import jakarta.ws.rs.InternalServerErrorException;
import jakarta.ws.rs.NotFoundException;
import jakarta.xml.bind.ValidationException;
import ru.danmax.soa_lab2_first_service.converter.DragonConverter;
import ru.danmax.soa_lab2_first_service.converter.DragonRequestDtoConverter;
import ru.danmax.soa_lab2_first_service.dto.request.DragonRequestDto;
import ru.danmax.soa_lab2_first_service.dto.response.DragonResponseDto;
import ru.danmax.soa_lab2_first_service.entity.Dragon;
import ru.danmax.soa_lab2_first_service.entity.enums.DragonCharacter;
import ru.danmax.soa_lab2_first_service.exception.DatabaseException;
import ru.danmax.soa_lab2_first_service.repository.DragonRepository;
import ru.danmax.soa_lab2_first_service.service.parser.FilterParser;
import ru.danmax.soa_lab2_first_service.service.parser.SortParser;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@ApplicationScoped
public class DragonService {

    @Inject
    private DragonRepository dragonRepository;

    public List<DragonResponseDto> getDragons(
            String sort,
            String filter,
            Integer page,
            Integer size) {
        try {
            if (sort != null && !sort.isEmpty()) {
                sort = SortParser.parse(sort);
            }

            if (filter != null && !filter.isEmpty()) {
                filter = FilterParser.parse(filter);
            }

            if (size == null) {
                if (page != null) {
                    throw new ValidationException("Page must be null when size is null");
                }
            }
            else {
                if (page == null) {
                    throw new ValidationException("Page cannot be null when size not null");
                }
                if (page < 0) {
                    throw new ValidationException("Page cannot be less than zero");
                }
            }

            List<Dragon> dragons = dragonRepository.findAll(sort, filter, page, size);
            List<DragonResponseDto> dragonResponseDtos = new ArrayList<>();
            for (Dragon dragon : dragons) {
                dragonResponseDtos.add(DragonConverter.convertToDragonResponseDto(dragon));
            }
            return dragonResponseDtos;
        } catch (ValidationException validationException) {
            throw new BadRequestException(validationException.getMessage());
        } catch (DatabaseException databaseException){
            throw new InternalServerErrorException(databaseException.getMessage());
        }
    }

    public void addDragon(DragonRequestDto dragonRequestDto) {
        Validator validator = Validation.buildDefaultValidatorFactory().getValidator();
        Set<ConstraintViolation<DragonRequestDto>> validationResult = validator.validate(dragonRequestDto);
        for (ConstraintViolation<DragonRequestDto> violation : validationResult) {
            throw new BadRequestException(violation.getMessage());
        }
        Dragon dragon = DragonRequestDtoConverter.convertToDragon(dragonRequestDto);

        try {
            dragonRepository.save(dragon);
        } catch (DatabaseException databaseException){
            throw new InternalServerErrorException(databaseException.getMessage());
        }
    }

    public DragonResponseDto getDragonById(Integer id){
        if (id == null) {
            throw new BadRequestException("Id can not be null");
        }

        try {
            Dragon dragon = dragonRepository.findById(id);
            if (dragon == null) {
                throw new NotFoundException("Dragon not found");
            }
            return DragonConverter.convertToDragonResponseDto(dragon);
        } catch (DatabaseException databaseException){
            throw new InternalServerErrorException(databaseException.getMessage());
        }
    }

    public void updateDragon(Integer id, DragonRequestDto dragonRequestDto) {
        if (id == null) {
            throw new BadRequestException("Id can not be null");
        }

        Validator validator = Validation.buildDefaultValidatorFactory().getValidator();
        Set<ConstraintViolation<DragonRequestDto>> validationResult = validator.validate(dragonRequestDto);
        for (ConstraintViolation<DragonRequestDto> violation : validationResult) {
            throw new BadRequestException(violation.getMessage());
        }
        Dragon newDragon = DragonRequestDtoConverter.convertToDragon(dragonRequestDto);

        try {
            Dragon dragon = dragonRepository.findById(id);
            if (dragon == null) {
                throw new NotFoundException("Dragon not found");
            }

            newDragon.setId(id);
            dragonRepository.save(newDragon);
        } catch (DatabaseException databaseException){
            throw new InternalServerErrorException(databaseException.getMessage());
        }
    }

    public void deleteDragon(Integer id) {
        if (id == null) {
            throw new BadRequestException("Id can not be null");
        }

        try {
            Dragon dragon = dragonRepository.findById(id);
            if (dragon == null) {
                throw new NotFoundException("Dragon not found");
            }

            dragonRepository.deleteById(id);
        } catch (DatabaseException databaseException){
            throw new InternalServerErrorException(databaseException.getMessage());
        }
    }

    public List<DragonResponseDto> searchByName(String name) {
        if (name == null || name.isEmpty()) {
            throw new BadRequestException("Name cannot be null");
        }

        try {
            List<Dragon> dragons = dragonRepository.findAllByNameSubstring(name);
            List<DragonResponseDto> dragonResponseDtos = new ArrayList<>();
            for (Dragon dragon : dragons) {
                dragonResponseDtos.add(DragonConverter.convertToDragonResponseDto(dragon));
            }
            return dragonResponseDtos;
        } catch (DatabaseException databaseException){
            throw new InternalServerErrorException(databaseException.getMessage());
        }
    }

    public List<DragonResponseDto> filterByKiller(String passportId) {
        if (passportId == null || passportId.isEmpty()) {
            throw new BadRequestException("PassportId cannot be null");
        }

        try {
            List<Dragon> dragons = dragonRepository.findAllFilterByKiller(passportId);
            List<DragonResponseDto> dragonResponseDtos = new ArrayList<>();
            for (Dragon dragon : dragons) {
                dragonResponseDtos.add(DragonConverter.convertToDragonResponseDto(dragon));
            }
            return dragonResponseDtos;
        } catch (DatabaseException databaseException){
            throw new InternalServerErrorException(databaseException.getMessage());
        }
    }

    public List<DragonResponseDto> filterByCharacter(String character) {
        if (character == null || character.isEmpty()) {
            throw new BadRequestException("Character cannot be null");
        }

        try {
            DragonCharacter dragonCharacter = DragonCharacter.valueOf(character);
            List<Dragon> dragons = dragonRepository.findAllFilterByCharacter(dragonCharacter);
            List<DragonResponseDto> dragonResponseDtos = new ArrayList<>();
            for (Dragon dragon : dragons) {
                dragonResponseDtos.add(DragonConverter.convertToDragonResponseDto(dragon));
            }
            return dragonResponseDtos;
        } catch (DatabaseException databaseException){
            throw new InternalServerErrorException(databaseException.getMessage());
        } catch (IllegalArgumentException illegalArgumentException) {
            throw new BadRequestException(illegalArgumentException.getMessage());
        }
    }
}
