package ru.danmax.soa_lab2_first_service.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import ru.danmax.soa_lab2_first_service.entity.enums.Color;
import ru.danmax.soa_lab2_first_service.entity.enums.DragonCharacter;
import ru.danmax.soa_lab2_first_service.entity.enums.DragonType;

import java.time.LocalDateTime;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "dragons")
public class Dragon {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Integer id; //Поле не может быть null, Значение поля должно быть больше 0, Значение этого поля должно быть уникальным, Значение этого поля должно генерироваться автоматически

    @Column(nullable = false)
    private String name; //Поле не может быть null, Строка не может быть пустой

    @Embedded
    @Column(nullable = false)
    private Coordinates coordinates; //Поле не может быть null

    @CreationTimestamp
    private LocalDateTime creationDate; //Поле не может быть null, Значение этого поля должно генерироваться автоматически

    @Column(nullable = false)
    private Integer age; //Значение поля должно быть больше 0, Поле не может быть null

    @Enumerated(EnumType.STRING)
    private Color color; //Поле может быть null

    @Enumerated(EnumType.STRING)
    private DragonType dragonType; //Поле может быть null

    @Enumerated(EnumType.STRING)
    private DragonCharacter character; //Поле может быть null

    @ManyToOne(cascade = CascadeType.ALL)
    private Person killer; //Поле может быть null


    @Transient
    public static final List<String> DRAGON_COLUMNS =
            List.of("age", "character", "color", "dragontype", "id", "killer_id", "x", "y", "creationdate", "name");
    @Transient
    public static final List<String> DRAGON_COLUMNS_DATATYPE =
            List.of("integer","character varying", "character varying", "character varying", "integer", "integer", "integer", "real", "timestamp without time zone", "character varying");

}

