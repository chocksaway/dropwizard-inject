package com.chocksaway.resource;

import com.chocksaway.model.Person;
import com.chocksaway.service.RedisService;
import com.google.inject.Inject;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.DELETE;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.PUT;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.WebApplicationException;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

import java.util.Optional;

@Path("/person")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class PersonResource {
    private final RedisService redisService;

    @Inject
    public PersonResource(final RedisService redisService){
        this.redisService = redisService;
    }

    @POST
    public Response add(@NotNull @Valid Person person) {
        redisService.addPerson(person);
        return Response.ok()
                .build();
    }

    @Path("/{name}")
    @GET
    public Person getPerson(@PathParam("name") String name){
        Optional<Person> person = redisService.getPerson(name);
        return person.orElseThrow(()->new WebApplicationException(Response.Status.NOT_FOUND));
    }

    @Path("/{name}")
    @DELETE
    public Person removePerson(@PathParam("name") String name){
        Optional<Person> person = redisService.deletePerson(name);
        return person.orElseThrow(()->new WebApplicationException(Response.Status.NOT_FOUND));
    }


    @Path("/{name}")
    @PUT
    public Response updatePerson(@PathParam("name") String name, @NotNull @Valid Person person){
        redisService.updatePerson(name, person);
        return Response.ok()
                .build();
    }
}
