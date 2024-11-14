package ru.danmax.soa_lab2_first_service;

import jakarta.ws.rs.core.Response;
import ru.danmax.soa_lab2_first_service.resources.DragonResource;

import java.sql.SQLException;

public class Test {
    public static void main(String[] args) {
        DragonResource dragonResource = new DragonResource();

        // Тест 1
        Response response = dragonResource.getDragons(null, null, null, null);
        System.out.println(response);
    }
}
