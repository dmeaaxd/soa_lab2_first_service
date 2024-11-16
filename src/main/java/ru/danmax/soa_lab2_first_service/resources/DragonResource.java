package ru.danmax.soa_lab2_first_service.resources;


import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import ru.danmax.soa_lab2_first_service.dto.request.DragonRequestDto;
import ru.danmax.soa_lab2_first_service.dto.response.ErrorResponseDto;
import ru.danmax.soa_lab2_first_service.exceptions.EntityAlreadyExists;
import ru.danmax.soa_lab2_first_service.services.DragonService;

import java.sql.SQLException;
import java.util.NoSuchElementException;


@Path("dragons")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class DragonResource {

    //TODO: сделать page и size
    //TODO: добавить разные коды возврата
    @GET
    public Response getDragons(
            @QueryParam("sort") String sort,
            @QueryParam("filter") String filter,
            @QueryParam("page") Integer page,
            @QueryParam("size") Integer size
    ) {
        try {
            return addCorsHeaders(Response
                    .ok(DragonService.getDragons(sort, filter, page, size)))
                    .build();
        } catch (SQLException sqlException) {
            Response.ResponseBuilder responseBuilder = Response
                    .status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity(ErrorResponseDto
                            .builder()
                            .code(Response.Status.INTERNAL_SERVER_ERROR.getStatusCode())
                            .message(sqlException.getMessage())
                            .build());
            return addCorsHeaders(responseBuilder).build();
        } catch (IllegalArgumentException illegalArgumentException) {
            Response.ResponseBuilder responseBuilder = Response
                    .status(Response.Status.BAD_REQUEST)
                    .entity(ErrorResponseDto
                            .builder()
                            .code(Response.Status.BAD_REQUEST.getStatusCode())
                            .message(illegalArgumentException.getMessage())
                            .build());
            return addCorsHeaders(responseBuilder).build();
        }
    }

    @POST
    public Response addDragon(DragonRequestDto dragonRequestDto) {
        try {
            DragonService.addDragon(dragonRequestDto);
            return addCorsHeaders(Response.status(Response.Status.CREATED)).build();
        } catch (SQLException sqlException) {
            Response.ResponseBuilder responseBuilder = Response
                    .status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity(ErrorResponseDto
                            .builder()
                            .code(Response.Status.INTERNAL_SERVER_ERROR.getStatusCode())
                            .message(sqlException.getMessage())
                            .build());
            return addCorsHeaders(responseBuilder).build();
        } catch (IllegalArgumentException illegalArgumentException) {
            Response.ResponseBuilder responseBuilder = Response
                    .status(Response.Status.BAD_REQUEST)
                    .entity(ErrorResponseDto
                            .builder()
                            .code(Response.Status.BAD_REQUEST.getStatusCode())
                            .message(illegalArgumentException.getMessage())
                            .build());
            return addCorsHeaders(responseBuilder).build();
        } catch (EntityAlreadyExists entityAlreadyExists){
            Response.ResponseBuilder responseBuilder = Response
                    .status(Response.Status.CONFLICT)
                    .entity(ErrorResponseDto
                            .builder()
                            .code(Response.Status.CONFLICT.getStatusCode())
                            .message(entityAlreadyExists.getMessage())
                            .build());
            return addCorsHeaders(responseBuilder).build();
        }
    }

    @GET
    @Path("/{id}")
    public Response getDragonById(@PathParam("id") Integer id){
        try {
            return addCorsHeaders(Response.ok(DragonService.getDragonById(id))).build();
        } catch (SQLException sqlException) {
            Response.ResponseBuilder responseBuilder = Response
                    .status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity(ErrorResponseDto
                            .builder()
                            .code(Response.Status.INTERNAL_SERVER_ERROR.getStatusCode())
                            .message(sqlException.getMessage())
                            .build());
            return addCorsHeaders(responseBuilder).build();
        } catch (NoSuchElementException noSuchElementException) {
            Response.ResponseBuilder responseBuilder = Response
                    .status(Response.Status.NOT_FOUND)
                    .entity(ErrorResponseDto
                            .builder()
                            .code(Response.Status.NOT_FOUND.getStatusCode())
                            .message(noSuchElementException.getMessage())
                            .build());
            return addCorsHeaders(responseBuilder).build();
        }
    }

    @PUT
    @Path("/{id}")
    public Response updateDragon(@PathParam("id") Integer id, DragonRequestDto dragonRequestDto) {
        try {
            DragonService.updateDragon(id, dragonRequestDto);
            return addCorsHeaders(Response.ok()).build();
        } catch (SQLException sqlException) {
            Response.ResponseBuilder responseBuilder = Response
                    .status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity(ErrorResponseDto
                            .builder()
                            .code(Response.Status.INTERNAL_SERVER_ERROR.getStatusCode())
                            .message(sqlException.getMessage())
                            .build());
            return addCorsHeaders(responseBuilder).build();
        } catch (IllegalArgumentException illegalArgumentException) {
            Response.ResponseBuilder responseBuilder = Response
                    .status(Response.Status.BAD_REQUEST)
                    .entity(ErrorResponseDto
                            .builder()
                            .code(Response.Status.BAD_REQUEST.getStatusCode())
                            .message(illegalArgumentException.getMessage())
                            .build());
            return addCorsHeaders(responseBuilder).build();
        } catch (NoSuchElementException noSuchElementException){
            Response.ResponseBuilder responseBuilder = Response
                    .status(Response.Status.NOT_FOUND)
                    .entity(ErrorResponseDto
                            .builder()
                            .code(Response.Status.NOT_FOUND.getStatusCode())
                            .message(noSuchElementException.getMessage())
                            .build());
            return addCorsHeaders(responseBuilder).build();
        }
    }

    @DELETE
    @Path("{id}")
    public Response deleteDragon(@PathParam("id") Integer id) {
        try {
            DragonService.deleteDragon(id);
            return addCorsHeaders(Response.ok()).build();
        } catch (SQLException sqlException) {
            Response.ResponseBuilder responseBuilder = Response
                    .status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity(ErrorResponseDto
                            .builder()
                            .code(Response.Status.INTERNAL_SERVER_ERROR.getStatusCode())
                            .message(sqlException.getMessage())
                            .build());
            return addCorsHeaders(responseBuilder).build();
        } catch (NoSuchElementException noSuchElementException){
            Response.ResponseBuilder responseBuilder = Response
                    .status(Response.Status.NOT_FOUND)
                    .entity(ErrorResponseDto
                            .builder()
                            .code(Response.Status.NOT_FOUND.getStatusCode())
                            .message(noSuchElementException.getMessage())
                            .build());
            return addCorsHeaders(responseBuilder).build();
        }
    }

    @GET
    @Path("search-by-name")
    public Response searchByName(@QueryParam("name") String name) {
        try {
            return addCorsHeaders(Response.ok(DragonService.searchByName(name))).build();
        } catch (SQLException sqlException) {
            Response.ResponseBuilder responseBuilder = Response
                    .status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity(ErrorResponseDto
                            .builder()
                            .code(Response.Status.INTERNAL_SERVER_ERROR.getStatusCode())
                            .message(sqlException.getMessage())
                            .build());
            return addCorsHeaders(responseBuilder).build();
        }
    }

    @GET
    @Path("filter-by-killer")
    public Response filterByKiller(@QueryParam("passport-id") String passportId) {
        try {
            return addCorsHeaders(Response
                    .ok(DragonService.filterByKiller(passportId)))
                    .build();
        } catch (SQLException sqlException) {
            Response.ResponseBuilder responseBuilder = Response
                    .status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity(ErrorResponseDto
                            .builder()
                            .code(Response.Status.INTERNAL_SERVER_ERROR.getStatusCode())
                            .message(sqlException.getMessage())
                            .build());
            return addCorsHeaders(responseBuilder).build();
        } catch (IllegalArgumentException illegalArgumentException) {
            Response.ResponseBuilder responseBuilder = Response
                    .status(Response.Status.BAD_REQUEST)
                    .entity(ErrorResponseDto
                            .builder()
                            .code(Response.Status.BAD_REQUEST.getStatusCode())
                            .message(illegalArgumentException.getMessage())
                            .build());
            return addCorsHeaders(responseBuilder).build();
        }
    }

    @GET
    @Path("filter-by-character")
    public Response filterByCharacter(@QueryParam("character") String character) {
        try {
            return addCorsHeaders(Response.ok(DragonService.filterByCharacter(character))).build();
        } catch (SQLException sqlException) {
            Response.ResponseBuilder responseBuilder = Response
                    .status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity(ErrorResponseDto
                            .builder()
                            .code(Response.Status.INTERNAL_SERVER_ERROR.getStatusCode())
                            .message(sqlException.getMessage())
                            .build());
            return addCorsHeaders(responseBuilder).build();
        } catch (IllegalArgumentException illegalArgumentException) {
            Response.ResponseBuilder responseBuilder = Response
                    .status(Response.Status.BAD_REQUEST)
                    .entity(ErrorResponseDto
                            .builder()
                            .code(Response.Status.BAD_REQUEST.getStatusCode())
                            .message(illegalArgumentException.getMessage())
                            .build());
            return addCorsHeaders(responseBuilder).build();
        }
    }

    private Response.ResponseBuilder addCorsHeaders(Response.ResponseBuilder responseBuilder){
        return responseBuilder
                .header("Access-Control-Allow-Origin", "*")
                .header("Access-Control-Allow-Credentials", "true")
                .header("Access-Control-Allow-Headers",
                        "origin, content-type, accept, authorization")
                .header("Access-Control-Allow-Methods",
                        "GET, POST, PUT, DELETE, OPTIONS, HEAD");
    }
}
