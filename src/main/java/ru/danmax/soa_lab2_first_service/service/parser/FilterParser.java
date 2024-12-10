package ru.danmax.soa_lab2_first_service.service.parser;

import ru.danmax.soa_lab2_first_service.entity.Dragon;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class FilterParser {

    public static String parseFilter(String filter) throws IllegalArgumentException {
        validateBrackets(filter);
        return recursionParseFilter(filter);
    }

    private static void validateBrackets(String filter) throws IllegalArgumentException{
        int openBracketsCount = 0;
        for (int i = 0; i < filter.length(); i++) {
            if (filter.charAt(i) == '(') {
                openBracketsCount++;
            }
            else if (filter.charAt(i) == ')') {
                openBracketsCount--;
            }
        }
        if (openBracketsCount < 0) {
            throw new IllegalArgumentException("Filter brackets not opened");
        }
        if (openBracketsCount > 0) {
            throw new IllegalArgumentException("Filter brackets not closed");
        }
    }

    private static String recursionParseFilter(String filter) throws IllegalArgumentException {
        String regex = "(\\w+)\\s+(eq|ne|lt|le|gt|ge)\\s+(\\w+)";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(filter);

        if (matcher.find()) {
            // Сохраняем исходные индексы для дальнейшей замены
            int start=matcher.start();
            int end=matcher.end();

            //Проверяем найденный фильтр
            String field = matcher.group(1);
            String operator = matcher.group(2);
            String value = matcher.group(3);
            String formatedMatch = validateAndFormatMatch(field, operator, value);

            //Обрезаем найденный фильтр и повторяем операцию
            String leftFilterPart = filter.substring(0, start);
            String rightFilterPart = filter.substring(end);
            validateFilterPart(leftFilterPart);

            filter = recursionParseFilter(rightFilterPart);
            filter = leftFilterPart + formatedMatch + filter;
        }
        else {
            validateFilterPart(filter);
        }

        return filter;
    }

    private static String validateAndFormatMatch(String field, String operator, String value) throws IllegalArgumentException {
        if (!Dragon.DRAGON_COLUMNS.contains(field)){
            throw new IllegalArgumentException("Invalid field " + field + " in filter. Use one of " + Dragon.DRAGON_COLUMNS);
        }

        switch (operator) {
            case "eq":
                operator = "=";
                break;
            case "ne":
                operator = "!=";
                break;
            case "lt":
                operator = "<";
                break;
            case "le":
                operator = "<=";
                break;
            case "gt":
                operator = ">";
                break;
            case "ge":
                operator = ">=";
                break;
            default:
                throw new IllegalArgumentException("Invalid operator " + operator + " in filter");
        }

        String datatype = Dragon.DRAGON_COLUMNS_DATATYPE.get(Dragon.DRAGON_COLUMNS.indexOf(field));
        switch (datatype) {
            case  ("integer"):
                try{
                    Integer.parseInt(value);
                } catch (NumberFormatException ignored){
                    throw new IllegalArgumentException(String.format("""
                            Invalid field value %s,
                            Type of value must be %s
                            """, value, datatype));
                }
                break;
            case  ("real"):
                try{
                    Float.parseFloat(value);
                } catch (NumberFormatException ignored){
                    throw new IllegalArgumentException(String.format("""
                            Invalid field value %s,
                            Type of value must be %s
                            """, value, datatype));
                }
                break;
            default:
                value = "'" + value + "'";
                break;
        }

        return field + " " + operator + " " + value;
    }

    private static void validateFilterPart(String filterPart) throws IllegalArgumentException {
        filterPart = " " + filterPart + " ";
        filterPart = filterPart.replaceAll("\\)", " ");
        filterPart = filterPart.replaceAll("\\(", " ");
        filterPart = filterPart.replaceAll(" and ", " ");
        filterPart = filterPart.replaceAll(" or ", " ");
        filterPart = filterPart.replaceAll(" ", "");

        if (!filterPart.isEmpty()) {
            throw new IllegalArgumentException("Filter contains extra characters");
        }
    }

}
