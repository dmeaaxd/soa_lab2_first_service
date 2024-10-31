package ru.danmax.soa_lab2_first_service.entities.enums;

public enum Color implements EnumEntity{
    BLACK,
    ORANGE,
    WHITE,
    BROWN;

    @Override
    public String getEnumName() {
        return "Color";
    }
}