package ru.danmax.soa_lab2_first_service;

import ru.danmax.soa_lab2_first_service.service.parser.FilterParser;

public class Main {
    public static void main(String[] args) {
        String filter = "a eq 10";
        System.out.println(FilterParser.parseFilter(filter));
    }
}
