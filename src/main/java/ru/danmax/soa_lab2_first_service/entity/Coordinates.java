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
public class Coordinates {
    @Column(nullable = false)
    Integer x; //Поле не может быть null
    Float y;
}
