package ru.danmax.soa_lab2_first_service.datasource.repositories.additional;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

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
        String regex = "\\((\\w+)\\s+(eq|ne|lt|le|gt|ge)\\s+([^()]+)\\)";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(filter);

        int parameterIndex = 1;

        while (matcher.find()) {
            String field = matcher.group(1);
            String operator = matcher.group(2);
            String value = matcher.group(3).trim();

            if ("id".equals(field)) {
                stmt.setLong(parameterIndex++, Long.parseLong(value));
            } else if ("name".equals(field)) {
                stmt.setString(parameterIndex++, value);
            } else if ("age".equals(field)) {
                stmt.setInt(parameterIndex++, Integer.parseInt(value));
            } else if ("color".equals(field)) {
                stmt.setString(parameterIndex++, value);
            } else if ("dragon_type".equals(field)) {
                stmt.setString(parameterIndex++, value);
            } else if ("character".equals(field)) {
                stmt.setString(parameterIndex++, value);
            }
        }
    }
}
