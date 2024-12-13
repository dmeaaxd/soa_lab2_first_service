package ru.danmax.soa_lab2_first_service.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "persons")
public class Person {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Integer id;

    @Column(nullable = false)
    private String name; //Поле не может быть null, Строка не может быть пустой

    @Column(name = "passport_id", length = 32)
    private String passportId; //Длина строки должна быть не меньше 10, Длина строки не должна быть больше 32, Поле может быть null

    @Embedded
    @Column(nullable = false)
    private Location location; //Поле не может быть null
}

