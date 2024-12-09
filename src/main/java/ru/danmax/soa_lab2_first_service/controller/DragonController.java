package ru.danmax.soa_lab2_first_service.controller;

import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import org.hibernate.Session;
import org.hibernate.Transaction;
import ru.danmax.soa_lab2_first_service.DatabaseSessionManager;
import ru.danmax.soa_lab2_first_service.dto.request.DragonRequestDto;
import jakarta.inject.Inject;
import ru.danmax.soa_lab2_first_service.entity.Dragon;

import java.util.ArrayList;
import java.util.List;

@Path("dragons")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class DragonController {

    @Inject
    private DatabaseSessionManager databaseSessionManager;

    @GET
    @Path("/db")
    public Response getDb() {
        Session session = databaseSessionManager.getSession();
        List<Dragon> dragonList = new ArrayList<>();
        try {
            session.beginTransaction();
            dragonList = session.createQuery("FROM Dragon", Dragon.class).getResultList();
        } catch (Exception e) {
            e.printStackTrace();
            if (session.getTransaction().isActive()) {
                session.getTransaction().rollback();
            }
            throw e;
        } finally {
            if (session.isOpen()) {
                databaseSessionManager.closeSession(session);
            }
        }
        return Response.ok(dragonList).build();
    }

    @GET
    public Response getAllDragons(
            @QueryParam("sort") String sort,
            @QueryParam("filter") String filter,
            @QueryParam("page") Integer page,
            @QueryParam("size") Integer size
    ) {
        return Response.ok("Ok").build();
    }

    @POST
    public Response addDragon(DragonRequestDto dragonRequestDto) {
        return Response.ok("Ok").build();
    }

    @GET
    @Path("/{id}")
    public Response getDragonById(@PathParam("id") Integer id){
        return Response.ok("Ok").build();
    }

    @PUT
    @Path("/{id}")
    public Response updateDragon(@PathParam("id") Integer id, DragonRequestDto dragonRequestDto) {
        return Response.ok("Ok").build();
    }

    @DELETE
    @Path("{id}")
    public Response deleteDragon(@PathParam("id") Integer id) {
        return Response.ok("Ok").build();
    }

    @GET
    @Path("search-by-name")
    public Response searchByName(@QueryParam("name") String name) {
        return Response.ok("Ok").build();
    }

    @GET
    @Path("filter-by-killer")
    public Response filterByKiller(@QueryParam("passport-id") String passportId) {
        return Response.ok("Ok").build();
    }

    @GET
    @Path("filter-by-character")
    public Response filterByCharacter(@QueryParam("character") String character) {
        return Response.ok("Ok").build();
    }
}
