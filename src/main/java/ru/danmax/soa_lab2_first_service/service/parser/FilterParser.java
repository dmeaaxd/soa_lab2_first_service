package ru.danmax.soa_lab2_first_service.service.parser;

import ru.danmax.soa_lab2_first_service.entity.Dragon;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class FilterParser {
    private static final String REGEX = "(\\w+)\\s+(eq|ne|lt|le|gt|ge)\\s+(\\w+)";

    public static String parse(String filter) throws IllegalArgumentException {
        validateExtraCharacter(filter);
        validateBrackets(filter);
        return parseFilterSqlString(filter);
    }

    private static void validateExtraCharacter(String filter) throws IllegalArgumentException {
        filter = " " + filter + " ";
        filter = filter.replaceAll(REGEX, " ")
                .replaceAll("\\)", " ")
                .replaceAll("\\(", " ")
                .replaceAll(" and ", " ")
                .replaceAll(" or ", " ")
                .replaceAll(" ", "");
        if (!filter.isEmpty()){
            throw new IllegalArgumentException("Extra characters in filter: " + filter);
        }
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

    private static String parseFilterSqlString(String filter) throws IllegalArgumentException {
        List<String> filterPartsWithoutFilterQuery = new ArrayList<>();
        List<String> filterQueries = new ArrayList<>();

        Pattern pattern = Pattern.compile(REGEX);
        Matcher matcher = pattern.matcher(filter);

        String leftFilterPart;
        String rightFilterPart = null;
        while (matcher.find()) {
            leftFilterPart = null;
            rightFilterPart = null;

            //Форматируем найденный filterQuery
            String field = matcher.group(1);
            String operator = matcher.group(2);
            String value = matcher.group(3);
            filterQueries.add(formatFilterQuery(field, operator, value));

            //Выделяем левую и правую часть фильтра
            leftFilterPart = filter.substring(0, matcher.start());
            rightFilterPart = filter.substring(matcher.end());

            filterPartsWithoutFilterQuery.add(leftFilterPart);
            filter = rightFilterPart;
            matcher = pattern.matcher(rightFilterPart);
        }
        filterPartsWithoutFilterQuery.add(rightFilterPart);

        StringBuilder stringBuilder = new StringBuilder();
        for (int i = 0; i < filterQueries.size(); i++) {
            stringBuilder
                    .append(filterPartsWithoutFilterQuery.get(i))
                    .append(filterQueries.get(i));
        }
        stringBuilder.append(filterPartsWithoutFilterQuery.get(filterPartsWithoutFilterQuery.size() - 1));

        return stringBuilder.toString();
    }

    private static String formatFilterQuery(String field, String operator, String value) throws IllegalArgumentException {
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

}
