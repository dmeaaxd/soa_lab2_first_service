package ru.danmax.soa_lab2_first_service.entities;

public interface Entity {
    String getTableName();
    String getSqlCreateTableScript();
}
