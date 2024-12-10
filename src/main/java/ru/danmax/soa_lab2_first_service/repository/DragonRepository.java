package ru.danmax.soa_lab2_first_service.repository;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import ru.danmax.soa_lab2_first_service.config.DatabaseSessionManager;
import ru.danmax.soa_lab2_first_service.entity.Dragon;

import java.util.ArrayList;
import java.util.List;

@ApplicationScoped
public class DragonRepository {

    @Inject
    private DatabaseSessionManager sessionManager;


}
