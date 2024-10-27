package ru.danmax.soa_lab2_first_service.entities.enums;

public enum DragonType implements EnumEntity{
    WATER,
    UNDERGROUND,
    AIR,
    FIRE;

    @Override
    public String getEnumName() {
        return "DragonType";
    }

    @Override
    public String getSqlCreateScript() {
        return String.format("CREATE TYPE %s AS ENUM ('WATER', 'UNDERGROUND', 'AIR', 'FIRE');", getEnumName());
    }
}
