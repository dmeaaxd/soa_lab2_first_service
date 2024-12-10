package ru.danmax.soa_lab2_first_service.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PersonResponseDto {
    private Integer id;
    private String name;
    private String passportId;
    private LocationResponseDto location;

    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class LocationResponseDto {
        private Integer x;
        private Double y;
        private Integer z;
        private String name;
    }
}
