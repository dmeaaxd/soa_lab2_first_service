package ru.danmax.soa_lab2_first_service.service.parser;

import jakarta.xml.bind.ValidationException;
import ru.danmax.soa_lab2_first_service.entity.Dragon;

import java.util.Arrays;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class SortParser {
    private static final String REGEX = "(\\w*):\\s*(\\w*)";

    public static String parse(String sort) throws ValidationException {
        validateExtraCharacter(sort);
        return parseSortSqlString(sort);
    }

    private static void validateExtraCharacter(String sort) throws ValidationException {
        sort = sort
                .replaceAll(REGEX, " ")
                .replaceAll(",", " ")
                .replaceAll(" ", "");
        if (!sort.isEmpty()){
            throw new ValidationException("Extra characters in sort: " + sort);
        }
    }

    private static String parseSortSqlString(String sort) throws ValidationException {
        Pattern pattern = Pattern.compile(REGEX);
        Matcher matcher = pattern.matcher(sort);

        // Проверяем поля фильтров и их значения
        StringBuilder sortBuilder = new StringBuilder();
        while (matcher.find()) {
            String field = matcher.group(1);
            String direction = matcher.group(2);

            if (!Dragon.DRAGON_COLUMNS.contains(field)) {
                throw new ValidationException(String.format("""
                        Invalid sort field: %s
                        Acceptable fields: %s
                        """, field, Arrays.toString(Dragon.DRAGON_COLUMNS.toArray())));
            }

            if (!direction.equals("asc") && !direction.equals("desc")) {
                throw new ValidationException(String.format("""
                        Invalid sort direction: %s
                        """, direction));
            }

            sortBuilder.append(field).append(" ").append(direction).append(", ");
        }

        return sortBuilder.substring(0, sortBuilder.toString().length() - 2);
    }
}
