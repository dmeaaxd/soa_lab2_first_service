package ru.danmax.soa_lab2_first_service;

import jakarta.ws.rs.core.Response;
import ru.danmax.soa_lab2_first_service.datasource.repositories.CoordinatesRepository;
import ru.danmax.soa_lab2_first_service.datasource.repositories.DragonRepository;
import ru.danmax.soa_lab2_first_service.datasource.repositories.additional.AdditionalMethods;
import ru.danmax.soa_lab2_first_service.dto.request.CoordinatesRequestDto;
import ru.danmax.soa_lab2_first_service.dto.request.DragonRequestDto;
import ru.danmax.soa_lab2_first_service.entities.Coordinates;
import ru.danmax.soa_lab2_first_service.exceptions.EntityAlreadyExists;
import ru.danmax.soa_lab2_first_service.resources.DragonResource;
import ru.danmax.soa_lab2_first_service.services.DragonService;

import java.awt.dnd.DragGestureEvent;
import java.sql.SQLException;

public class Test {
    public static void main(String[] args) throws SQLException, EntityAlreadyExists {
        System.out.println(DragonService.filterByKiller("a123123123123123123"));
    }
}
