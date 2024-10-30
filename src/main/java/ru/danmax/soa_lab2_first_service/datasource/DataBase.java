package ru.danmax.soa_lab2_first_service.datasource;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DataBase {
    //  Database credentials
    static final String URL = "";
    static final String USERNAME = "";
    static final String PASSWORD = "";

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

//    public static void checkAndCreateTables() throws SQLException {
//        getConnection();
//
//        // Создаем enum-ы
//        createRelation(Color.BLACK.getSqlCreateScript());
//        createRelation(DragonCharacter.FICKLE.getSqlCreateScript());
//        createRelation(DragonType.WATER.getSqlCreateScript());
//
//        // Создаем таблицы
//        createRelation(new Coordinates().getSqlCreateTableScript());
//        createRelation(new Location().getSqlCreateTableScript());
//        createRelation(new Person().getSqlCreateTableScript());
//        createRelation(new Dragon().getSqlCreateTableScript());
//    }
//
//    private static void createRelation(String sql) throws SQLException {
//        try {
//            connection.createStatement().execute(sql);
//        } catch (SQLException alreadyExist) {
//            System.out.println(alreadyExist.getMessage());
//            System.out.println(alreadyExist.getSQLState());
//            System.out.println(alreadyExist.getErrorCode());
//        }
//    }
}
