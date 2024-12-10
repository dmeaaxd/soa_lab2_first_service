package ru.danmax.soa_lab2_first_service.service;

import jakarta.enterprise.context.ApplicationScoped;
import ru.danmax.soa_lab2_first_service.dto.request.DragonRequestDto;
import ru.danmax.soa_lab2_first_service.dto.response.DragonResponseDto;

import java.util.List;

@ApplicationScoped
public class DragonService {

    public List<DragonResponseDto> getAllDragons(
            String sort,
            String filter,
            Integer page,
            Integer size) {
        return null;
    }

    public void addDragon(DragonRequestDto dragonRequestDto) {
    }

    public DragonResponseDto getDragonById(Integer id){
        return null;
    }

    public void updateDragon(Integer id, DragonRequestDto dragonRequestDto) {
    }

    public void deleteDragon(Integer id) {
    }

    public List<DragonResponseDto> searchByName(String name) {
        return null;
    }

    public List<DragonResponseDto> filterByKiller(String passportId) {
        return null;
    }

    public List<DragonResponseDto> filterByCharacter(String character) {
        return null;
    }
}
