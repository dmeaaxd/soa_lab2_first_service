package ru.danmax.soa_lab2_first_service.datasource;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DataBase {
    //  Database credentials
    static final String URL = "jdbc:postgresql://147.45.190.117:5432/soa_DANMAX";
    static final String USERNAME = "danmax";
    static final String PASSWORD = "soaLabs2024";

    private static Connection connection = null;

    public static Connection getConnection() throws SQLException {
        if (checkConnection()){
            return connection;
        } else {
            restoreConnection();
            if (checkConnection()){
                return connection;
            }
            else {
                throw new SQLException("Can't connect to database");
            }
        }
    }

    private static void connect() throws SQLException {
        connection = DriverManager.getConnection(URL, USERNAME, PASSWORD);
    }

    private static void disconnect() throws SQLException {
        connection.close();
    }

    private static boolean checkConnection() {
        try{
            return (connection != null && connection.isValid(1) && !connection.isClosed());
        } catch (SQLException exception){
            return false;
        }
    }

    private static void restoreConnection(){
        try{
            if (connection!= null && !connection.isClosed()){
                disconnect();
            }
            connect();
        } catch (SQLException ignored) {}
    }

    private static void checkAndCreateTables(){

    }
}
