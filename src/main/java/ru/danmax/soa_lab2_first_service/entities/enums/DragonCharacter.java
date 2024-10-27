package ru.danmax.soa_lab2_first_service.entities.enums;

public enum DragonCharacter implements EnumEntity{
    CUNNING,
    WISE,
    CHAOTIC,
    CHAOTIC_EVIL,
    FICKLE;

    @Override
    public String getEnumName() {
        return "DragonCharacter";
    }

    @Override
    public String getSqlCreateScript() {
        return String.format("CREATE TYPE %s AS ENUM ('CUNNING', 'WISE', 'CHAOTIC', 'CHAOTIC_EVIL', 'FICKLE');", getEnumName());
    }
}
