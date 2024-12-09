package ru.danmax.soa_lab2_first_service.errormapper;

import jakarta.ws.rs.InternalServerErrorException;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.ext.ExceptionMapper;
import jakarta.ws.rs.ext.Provider;
import ru.danmax.soa_lab2_first_service.dto.response.ErrorResponseDto;

@Provider
public class InternalServerErrorExceptionMapper implements ExceptionMapper<InternalServerErrorException> {
    @Override
    public Response toResponse(InternalServerErrorException e) {
        return Response
                .status(Response.Status.INTERNAL_SERVER_ERROR)
                .entity(ErrorResponseDto.builder()
                        .code(Response.Status.INTERNAL_SERVER_ERROR.getStatusCode())
                        .message(e.getMessage())
                        .build()
                )
                .build();
    }
}
