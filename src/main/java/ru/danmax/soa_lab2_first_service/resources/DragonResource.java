package ru.danmax.soa_lab2_first_service.resources;


import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import ru.danmax.soa_lab2_first_service.dto.request.DragonRequestDto;
import ru.danmax.soa_lab2_first_service.dto.response.DragonResponseDto;
import ru.danmax.soa_lab2_first_service.dto.response.ErrorResponseDto;
import ru.danmax.soa_lab2_first_service.entities.Dragon;
import ru.danmax.soa_lab2_first_service.exceptions.EntityAlreadyExists;
import ru.danmax.soa_lab2_first_service.services.DragonService;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;


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
            return Response.ok(DragonService.getDragons(sort, filter, page, size)).build();
        } catch (SQLException sqlException) {
            return Response
                    .status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity(ErrorResponseDto
                            .builder()
                            .code(Response.Status.INTERNAL_SERVER_ERROR.getStatusCode())
                            .message(sqlException.getMessage())
                            .build())
                    .build();
        } catch (IllegalArgumentException illegalArgumentException) {
            return Response
                    .status(Response.Status.BAD_REQUEST)
                    .entity(ErrorResponseDto
                            .builder()
                            .code(Response.Status.BAD_REQUEST.getStatusCode())
                            .message(illegalArgumentException.getMessage())
                            .build())
                    .build();
        }
    }

    @POST
    public Response addDragon(DragonRequestDto dragonRequestDto) {
        try {
            DragonService.addDragon(dragonRequestDto);
            return Response.ok().build();
        } catch (SQLException sqlException) {
            return Response
                    .status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity(ErrorResponseDto
                            .builder()
                            .code(Response.Status.INTERNAL_SERVER_ERROR.getStatusCode())
                            .message(sqlException.getMessage())
                            .build())
                    .build();
        } catch (IllegalArgumentException illegalArgumentException) {
            return Response
                    .status(Response.Status.BAD_REQUEST)
                    .entity(ErrorResponseDto
                            .builder()
                            .code(Response.Status.BAD_REQUEST.getStatusCode())
                            .message(illegalArgumentException.getMessage())
                            .build())
                    .build();
        } catch (EntityAlreadyExists entityAlreadyExists){
            return Response
                    .status(Response.Status.CONFLICT)
                    .entity(ErrorResponseDto
                            .builder()
                            .code(Response.Status.CONFLICT.getStatusCode())
                            .message(entityAlreadyExists.getMessage())
                            .build())
                    .build();
        }
    }

    @GET
    @Path("/{id}")
    public Response getDragonById(@PathParam("id") Integer id){
        try {
            return Response.ok(DragonService.getDragonById(id)).build();
        } catch (SQLException sqlException) {
            return Response
                    .status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity(ErrorResponseDto
                            .builder()
                            .code(Response.Status.INTERNAL_SERVER_ERROR.getStatusCode())
                            .message(sqlException.getMessage())
                            .build())
                    .build();
        } catch (IllegalArgumentException illegalArgumentException) {
            return Response
                    .status(Response.Status.NOT_FOUND)
                    .entity(ErrorResponseDto
                            .builder()
                            .code(Response.Status.NOT_FOUND.getStatusCode())
                            .message(illegalArgumentException.getMessage())
                            .build())
                    .build();
        }
    }

//    @PUT
//    @Path("/{id}")
//    public Response updateDragon(@PathParam("id") Integer id, DragonRequestDto dragonRequestDto) {
//        try {
//            DragonService.updateDragon(id, dragonRequestDto);
//            return Response.ok().build();
//        } catch (SQLException sqlException) {
//            return Response
//                    .status(Response.Status.INTERNAL_SERVER_ERROR)
//                    .entity(ErrorResponseDto
//                            .builder()
//                            .code(Response.Status.INTERNAL_SERVER_ERROR.getStatusCode())
//                            .message(sqlException.getMessage())
//                            .build())
//                    .build();
//        } catch (IllegalArgumentException illegalArgumentException) {
//            return Response
//                    .status(Response.Status.BAD_REQUEST)
//                    .entity(ErrorResponseDto
//                            .builder()
//                            .code(Response.Status.BAD_REQUEST.getStatusCode())
//                            .message(illegalArgumentException.getMessage())
//                            .build())
//                    .build();
//        } catch (EntityAlreadyExists entityAlreadyExists){
//            return Response
//                    .status(Response.Status.CONFLICT)
//                    .entity(ErrorResponseDto
//                            .builder()
//                            .code(Response.Status.CONFLICT.getStatusCode())
//                            .message(entityAlreadyExists.getMessage())
//                            .build())
//                    .build();
//        }
//    }

//    @DELETE
//    @Path("{id}")
//    public Response deleteDragon(@PathParam("id") Integer id) {
//        dragonService.deleteDragon(id);
//        return Response.ok().build();
//    }
//
//    @GET
//    @Path("search-by-name")
//    public List<Dragon> searchByName(@QueryParam("name") String name) {
//        return dragonService.searchByName(name);
//    }
//
//    @GET
//    @Path("filter-by-killer")
//    public List<Dragon> filterByKiller(@QueryParam("passportId") Long passportId) {
//        return dragonService.filterByKiller(passportId);
//    }
//
//    @GET
//    @Path("filter-by-character")
//    public List<Dragon> filterByCharacter(@QueryParam("character") DragonCharacter character) {
//        return dragonService.filterByCharacter(character);
//    }
}
