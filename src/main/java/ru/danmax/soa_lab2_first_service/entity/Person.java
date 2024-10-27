package ru.danmax.soa_lab2_first_service.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "persons")
public class Person {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    @Size(min = 10, max = 32)
    @Column(nullable = false)
    private String passportId;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(nullable = false)
    private Location location;
}
