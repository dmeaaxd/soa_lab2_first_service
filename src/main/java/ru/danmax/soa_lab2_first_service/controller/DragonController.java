package ru.danmax.soa_lab2_first_service.controller;

import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import ru.danmax.soa_lab2_first_service.dto.request.DragonRequestDto;
import ru.danmax.soa_lab2_first_service.service.DragonService;


@Path("dragons")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class DragonController {

    @Inject
    private DragonService dragonService;

    @GET
    public Response getAllDragons(
            @QueryParam("sort") String sort,
            @QueryParam("filter") String filter,
            @QueryParam("page") Integer page,
            @QueryParam("size") Integer size
    ) {
        return Response
                .ok(dragonService.getDragons(sort, filter, page, size))
                .build();
    }

    @POST
    public Response addDragon(DragonRequestDto dragonRequestDto) {
        dragonService.addDragon(dragonRequestDto);
        return Response.status(Response.Status.CREATED).build();
    }

    @GET
    @Path("/{id}")
    public Response getDragonById(@PathParam("id") Integer id){
        return Response
                .ok(dragonService.getDragonById(id))
                .build();
    }

    @PUT
    @Path("/{id}")
    public Response updateDragon(@PathParam("id") Integer id, DragonRequestDto dragonRequestDto) {
        dragonService.updateDragon(id, dragonRequestDto);
        return Response.ok().build();
    }

    @DELETE
    @Path("{id}")
    public Response deleteDragon(@PathParam("id") Integer id) {
        dragonService.deleteDragon(id);
        return Response.ok().build();
    }

    @GET
    @Path("search-by-name")
    public Response searchByName(@QueryParam("name") String name) {
        return Response
                .ok(dragonService.searchByName(name))
                .build();
    }

    @GET
    @Path("filter-by-killer")
    public Response filterByKiller(@QueryParam("passport-id") String passportId) {
        return Response
                .ok(dragonService.filterByKiller(passportId))
                .build();
    }

    @GET
    @Path("filter-by-character")
    public Response filterByCharacter(@QueryParam("character") String character) {
        return Response
                .ok(dragonService.filterByCharacter(character))
                .build();
    }
}
