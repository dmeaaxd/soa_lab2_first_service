package ru.danmax.soa_lab2_first_service.errormapper;

import jakarta.ws.rs.NotFoundException;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.ext.ExceptionMapper;
import jakarta.ws.rs.ext.Provider;
import ru.danmax.soa_lab2_first_service.dto.response.ErrorResponseDto;

@Provider
public class NotFoundExceptionMapper implements ExceptionMapper<NotFoundException> {
    @Override
    public Response toResponse(NotFoundException e) {
        return Response
                .status(Response.Status.NOT_FOUND)
                .entity(ErrorResponseDto.builder()
                        .code(Response.Status.NOT_FOUND.getStatusCode())
                        .message(e.getMessage())
                        .build()
                )
                .build();
    }
}
