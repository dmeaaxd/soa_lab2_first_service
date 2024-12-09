package ru.danmax.soa_lab2_first_service.errormapper;

import jakarta.ws.rs.BadRequestException;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.ext.ExceptionMapper;
import jakarta.ws.rs.ext.Provider;
import ru.danmax.soa_lab2_first_service.dto.response.ErrorResponseDto;

@Provider
public class BadRequestExceptionMapper implements ExceptionMapper<BadRequestException> {
    @Override
    public Response toResponse(BadRequestException e) {
        return Response
                .status(Response.Status.BAD_REQUEST)
                .entity(ErrorResponseDto.builder()
                        .code(Response.Status.BAD_REQUEST.getStatusCode())
                        .message(e.getMessage())
                        .build()
                )
                .build();
    }
}
