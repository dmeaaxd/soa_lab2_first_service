package ru.danmax.soa_lab2_first_service.datasource.repositories.additional;

import java.sql.PreparedStatement;
import java.sql.SQLException;

public class AdditionalMethods {

    public static String parseSort(String sort) {
        StringBuilder sortBuilder = new StringBuilder();
        String[] sortFields = sort.split(",\\s*");

        for (String field : sortFields) {
            if (sortBuilder.length() > 0) {
                sortBuilder.append(", ");
            }
            sortBuilder.append(field.replace("-", "")).append(sort.startsWith("-") ? " DESC" : " ASC");
        }
        return sortBuilder.toString();
    }

    public static String parseFilter(String filter) {
        return filter.replace("eq", "=")
                .replace("ne", "!=")
                .replace("lt", "<")
                .replace("le", "<=")
                .replace("gt", ">")
                .replace("ge", ">=");
    }

    public static void setFilterParameters(PreparedStatement stmt, String filter) throws SQLException {

    }
}
