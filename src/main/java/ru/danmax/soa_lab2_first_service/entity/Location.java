package ru.danmax.soa_lab2_first_service.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Embeddable
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Location {
    private Integer x;

    @Column(nullable = false)
    private Double y; //Поле не может быть null

    private Integer z;

    @Column(nullable = false)
    private String locationName; //Строка не может быть пустой, Поле не может быть null
}
