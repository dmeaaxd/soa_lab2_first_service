package ru.danmax.soa_lab2_first_service.resources;


import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.Response;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import ru.danmax.soa_lab2_first_service.entities.Dragon;
import ru.danmax.soa_lab2_first_service.entities.enums.DragonCharacter;
import ru.danmax.soa_lab2_first_service.services.DragonService;

import java.util.List;


@Path("dragons")
public class DragonResource {

    private final DragonService dragonService;

    @Inject
    public DragonResource(DragonService dragonService) {
        this.dragonService = dragonService;
    }

    @GET
    public List<Dragon> getDragons(
            @QueryParam("sort") String sort,
            @QueryParam("filter") String filter,
            @QueryParam("page") Integer page,
            @QueryParam("size") Integer size
    ) {
        return dragonService.getDragons(sort, filter, page, size);
    }

    @POST
    public Response addDragon(Dragon dragon) {
        dragonService.addDragon(dragon);
        return Response.ok().build();
    }

    @GET
    @Path("{id}")
    public Dragon getDragonById(@PathParam("id") Long id) {
        return dragonService.getDragonById(id);
    }

    @PUT
    @Path("{id}")
    public Response updateDragon(@PathParam("id") Long id, Dragon dragon) {
        dragonService.updateDragon(id, dragon);
        return Response.ok().build();
    }

    @DELETE
    @Path("{id}")
    public Response deleteDragon(@PathParam("id") Long id) {
        dragonService.deleteDragon(id);
        return Response.ok().build();
    }

    @GET
    @Path("search-by-name")
    public List<Dragon> searchByName(@QueryParam("name") String name) {
        return dragonService.searchByName(name);
    }

    @GET
    @Path("filter-by-killer")
    public List<Dragon> filterByKiller(@QueryParam("passportId") Long passportId) {
        return dragonService.filterByKiller(passportId);
    }

    @GET
    @Path("filter-by-character")
    public List<Dragon> filterByCharacter(@QueryParam("character") DragonCharacter character) {
        return dragonService.filterByCharacter(character);
    }
}
