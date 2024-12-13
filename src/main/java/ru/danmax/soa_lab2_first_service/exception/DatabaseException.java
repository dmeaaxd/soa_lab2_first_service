package ru.danmax.soa_lab2_first_service.exception;

public class DatabaseException extends Exception{
    public DatabaseException(String message){
        super(message);
    }
}
