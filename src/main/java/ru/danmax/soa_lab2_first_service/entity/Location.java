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
    @Column(name = "location_x")
    private Integer x;

    @Column(name = "location_y", nullable = false)
    private Double y; //Поле не может быть null

    @Column(name = "location_z")
    private Integer z;

    @Column(name = "location_name", nullable = false)
    private String name; //Строка не может быть пустой, Поле не может быть null
}
