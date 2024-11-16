FROM maven:3.8.3-openjdk-17

WORKDIR /app

COPY pom.xml .
RUN mvn dependency:go-offline

COPY . .

CMD ["mvn", "clean", "package", "cargo:run"]