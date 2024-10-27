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

import java.sql.ResultSet;
import java.sql.SQLException;
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
                    coordinates_id INT NOT NULL,
                    creation_date timestamp NOT NULL,
                    age INT NOT NULL,
                    color %s NOT NULL,
                    dragon_type %s NOT NULL,
                    character %s NOT NULL,
                    killer_id INT,
                    FOREIGN KEY (coordinates_id) REFERENCES %s (id) ON DELETE CASCADE,
                    FOREIGN KEY (killer_id) REFERENCES %s (id) ON DELETE RESTRICT,
                    CONSTRAINT dragon_chk_age CHECK (age > 0)
                );""", getTableName(), Color.BLACK.getEnumName(), DragonType.AIR.getEnumName(), DragonCharacter.CHAOTIC.getEnumName(), new Coordinates().getTableName(), new Person().getTableName());
    }

    public static Dragon createDragonFromResultSet(ResultSet rs) throws SQLException {
        return Dragon.builder()
                .id(rs.getLong("id"))
                .name(rs.getString("name"))
                .creationDate(rs.getTimestamp("creation_date").toLocalDateTime())
                .age(rs.getInt("age"))
                .color(Color.valueOf(rs.getString("dragon_type").trim()))
                .dragonType(DragonType.valueOf(rs.getString("dragon_type").trim()))
                .character(DragonCharacter.valueOf(rs.getString("character").trim()))
                .build();
    }
}

