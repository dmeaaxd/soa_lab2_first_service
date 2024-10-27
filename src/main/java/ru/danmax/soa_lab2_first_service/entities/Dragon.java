package ru.danmax.soa_lab2_first_service.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.danmax.soa_lab2_first_service.entities.enums.DragonCharacter;
import ru.danmax.soa_lab2_first_service.entities.enums.DragonType;
import ru.danmax.soa_lab2_first_service.entities.enums.Color;

import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Dragon implements Entity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(nullable = false)
    private Coordinates coordinates;

    @Column(nullable = false)
    private LocalDateTime creationDate;

    @Positive
    private int age;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Color color;

    @Enumerated(EnumType.STRING)
    private DragonType dragonType;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private DragonCharacter character;

    @ManyToOne(cascade = CascadeType.ALL)
    private Person killer;

    @Override
    public String getTableName() {
        return "dragons";
    }

    @Override
    public String getSqlCreateTableScript() {
        return String.format("""
                CREATE TABLE %s (
                    id INT PRIMARY KEY AUTO_INCREMENT,
                    name VARCHAR(255) NOT NULL,
                    creation_date timestamp NOT NULL,
                    age INT NOT NULL,
                    color %s NOT NULL,
                    dragon_type %s NOT NULL,
                    dragon_character %s NOT NULL,
                    killer_id INT,
                    FOREIGN KEY (killer_id) REFERENCES %s (id),
                    CONSTRAINT dragon_chk_age CHECK (age > 0)
                );""", getTableName(), Color.BLACK.getEnumName(), DragonType.AIR.getEnumName(), DragonCharacter.CHAOTIC.getEnumName(), new Person().getTableName());
    }
}

