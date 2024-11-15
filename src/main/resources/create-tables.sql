-- Создание типов данных
CREATE TYPE Color AS ENUM (
    'BLACK',
    'ORANGE',
    'WHITE',
    'BROWN'
    );

CREATE TYPE DragonCharacter AS ENUM (
    'CUNNING',
    'WISE',
    'CHAOTIC',
    'CHAOTIC_EVIL',
    'FICKLE'
    );

CREATE TYPE DragonType AS ENUM (
    'WATER',
    'UNDERGROUND',
    'AIR',
    'FIRE'
    );

-- Создание таблиц
CREATE TABLE locations (
    id SERIAL PRIMARY KEY,
    x FLOAT,
    y FLOAT NOT NULL,
    z FLOAT,
    name VARCHAR(255) NOT NULL,
    CONSTRAINT location_chk_name CHECK (char_length(name) > 0)
    );

CREATE TABLE persons (
    id SERIAL PRIMARY KEY,
    name VARCHAR(255) NOT NULL,
    passport_id VARCHAR(32),
    location_id INT NOT NULL,
    FOREIGN KEY (location_id) REFERENCES locations (id) ON DELETE CASCADE,
    CONSTRAINT person_chk_name CHECK (char_length(name) > 0),
    CONSTRAINT person_chk_passport CHECK (passport_id IS NULL OR (char_length(passport_id) > 9 AND char_length(passport_id) < 33))
    );

CREATE TABLE coordinates (
    id SERIAL PRIMARY KEY,
    x INT NOT NULL,
    y FLOAT
    );

CREATE TABLE dragons (
    id SERIAL PRIMARY KEY,
    name VARCHAR(255) NOT NULL,
    coordinates_id INT NOT NULL,
    creation_date timestamp NOT NULL default CURRENT_TIMESTAMP,
    age INT NOT NULL,
    color Color,
    dragon_type DragonType,
    character DragonCharacter,
    killer_id INT,
    FOREIGN KEY (coordinates_id) REFERENCES coordinates (id) ON DELETE CASCADE,
    FOREIGN KEY (killer_id) REFERENCES persons (id) ON DELETE RESTRICT,
    CONSTRAINT dragon_chk_age CHECK (age > 0),
    CONSTRAINT dragon_chk_name CHECK (char_length(name) > 0)
    );
