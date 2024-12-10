package ru.danmax.soa_lab2_first_service.service.parser;

import ru.danmax.soa_lab2_first_service.entity.Dragon;

import java.util.Arrays;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class SortParser {
    public static String parse(String sort) throws IllegalArgumentException {
        validateExtraCharacter(sort);
        return parseSort(sort);
    }

    private static void validateExtraCharacter(String sort) throws IllegalArgumentException {
        sort = sort.replaceAll("(\\w*):\\s*(\\w*)", " ").replaceAll(",", " ").replaceAll(" ", "");
        if (!sort.isEmpty()){
            throw new IllegalArgumentException("Extra characters in sort: " + sort);
        }
    }

    private static String parseSort(String sort) throws IllegalArgumentException {
        String regex = "(\\w*):\\s*(\\w*)";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(sort);

        // Проверяем поля фильтров и их значения
        StringBuilder sortBuilder = new StringBuilder();
        while (matcher.find()) {
            String field = matcher.group(1);
            String direction = matcher.group(2);

            if (!Dragon.DRAGON_COLUMNS.contains(field)) {
                throw new IllegalArgumentException(String.format("""
                        Invalid sort field: %s
                        Acceptable fields: %s
                        """, field, Arrays.toString(Dragon.DRAGON_COLUMNS.toArray())));
            }

            if (!direction.equals("asc") && !direction.equals("desc")) {
                throw new IllegalArgumentException(String.format("""
                        Invalid sort direction: %s
                        """, direction));
            }

            sortBuilder.append(field).append(" ").append(direction).append(", ");
        }

        return sortBuilder.substring(0, sortBuilder.toString().length() - 2);
    }
}
