package ru.danmax.soa_lab2_first_service.datasource.repositories.additional;

import ru.danmax.soa_lab2_first_service.datasource.DataBase;
import ru.danmax.soa_lab2_first_service.entities.Dragon;
import ru.danmax.soa_lab2_first_service.entities.enums.Color;
import ru.danmax.soa_lab2_first_service.entities.enums.DragonCharacter;
import ru.danmax.soa_lab2_first_service.entities.enums.DragonType;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class AdditionalMethods {

    private static List<String> DRAGON_TABLE_COLUMNS = new ArrayList<>();
    private static List<String> DRAGON_COLUMN_DATATYPES = new ArrayList<>();

    private static void updateDragonTableColumns() throws SQLException {
        Connection connection = DataBase.getConnection();
        ResultSet rs = connection.createStatement().executeQuery(
                String.format("""
                                SELECT column_name, data_type
                                FROM information_schema.columns
                                WHERE table_schema = 'public' AND table_name = '%s';
                            """, new Dragon().getTableName())
        );
        DRAGON_TABLE_COLUMNS = new ArrayList<>();
        DRAGON_COLUMN_DATATYPES = new ArrayList<>();

        while (rs.next()){
            DRAGON_TABLE_COLUMNS.add(rs.getString("column_name"));
            DRAGON_COLUMN_DATATYPES.add(rs.getString("data_type"));
        }
    }

    private static boolean isFieldInDragons(String field) throws SQLException {
        if (DRAGON_TABLE_COLUMNS.isEmpty()){
            updateDragonTableColumns();
        }
        return DRAGON_TABLE_COLUMNS.contains(field);
    }

    private static String getFieldDataType(String field) throws SQLException {
        if (DRAGON_TABLE_COLUMNS.isEmpty()){
            updateDragonTableColumns();
        }
        return DRAGON_COLUMN_DATATYPES.get(DRAGON_TABLE_COLUMNS.indexOf(field));
    }

    public static String parseSort(String sort) throws SQLException, IllegalArgumentException {
        StringBuilder sortBuilder = new StringBuilder();
        String[] sortFields = sort.split(",\\s*");

        for (String field : sortFields) {
            if (!sortBuilder.isEmpty()) {
                sortBuilder.append(", ");
            }
            if (isFieldInDragons(field)){
                sortBuilder.append(field.replace("-", "")).append(sort.startsWith("-") ? " DESC" : " ASC");
            }
            else {
                throw new IllegalArgumentException(String.format("""
                                                                Invalid sort field: %s
                                                                Acceptable fields: %s
                                                                """, field, Arrays.toString(DRAGON_TABLE_COLUMNS.toArray())));
            }
        }
        return sortBuilder.toString();
    }

    public static String parseFilter(String filter) throws SQLException, IllegalArgumentException {
        String regex = "(\\w+)\\s+(eq|ne|lt|le|gt|ge)\\s+(\\w+)";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(filter);
        String newFilter = filter;

        // Проверяем поля фильтров и их значения
        while (matcher.find()) {
            int start=matcher.start();
            int end=matcher.end();
            String startMatch = filter.substring(start,end);
            String match = startMatch;

            String field = matcher.group(1);
            String startFieldValue = matcher.group(3);
            String fieldValue = startFieldValue;

            if (!isFieldInDragons(field)) {
                throw new IllegalArgumentException(String.format("""
                        Invalid filer field: %s
                        Acceptable fields: %s
                        """, field, Arrays.toString(DRAGON_TABLE_COLUMNS.toArray())));
            }

            String fieldDataType = getFieldDataType(field);
            switch (fieldDataType) {
                case  ("integer"):
                    try{
                        Integer.parseInt(fieldValue);
                    } catch (NumberFormatException ignored){
                        throw new IllegalArgumentException(String.format("""
                            Invalid field value %s,
                            Type of value must be %s
                            """, fieldValue, fieldDataType));
                    }
                    break;
                case  ("USER-DEFINED"):
                    try{
                        switch (field){
                            case ("color"):
                                Color.valueOf(fieldValue);
                                break;
                            case ("dragon_type"):
                                DragonType.valueOf(fieldValue);
                                break;
                            case ("character"):
                                DragonCharacter.valueOf(fieldValue);
                                break;
                        }
                    } catch (NumberFormatException ignored){
                        throw new IllegalArgumentException(String.format("""
                            Invalid field value %s,
                            Type of value must be one off enum %s
                            """, fieldValue, field));
                    }
                    break;
                default:
                    fieldValue = "'" + fieldValue + "'";
                    break;
            }
            match = match.replace(startFieldValue, fieldValue);
            int startIndex = newFilter.indexOf(startMatch);
            int endIndex = startIndex + startMatch.length();

            newFilter = newFilter.substring(0, startIndex) + match + newFilter.substring(endIndex);
        }

        // Заменяем знаки на поддерживаемые
        return newFilter.replace("eq", "=")
                .replace("ne", "!=")
                .replace("lt", "<")
                .replace("le", "<=")
                .replace("gt", ">")
                .replace("ge", ">=");
    }

    public static boolean checkPage(Integer page) throws IllegalArgumentException{
        if (page <= 0) {
            throw new IllegalArgumentException("page must be greater than zero");
        }
        return true;
    }

    public static boolean checkSize(Integer size) throws IllegalArgumentException{
        if (size <= 0) {
            throw new IllegalArgumentException("size must be greater than zero");
        }
        return true;
    }
}
