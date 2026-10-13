package dev.demo.app;

import jakarta.inject.Inject;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;

import dev.demo.messagekit.MessageFormatter;

@Path("/hello")
@Produces(MediaType.TEXT_PLAIN)
public class GreetingResource {

    @Inject
    MessageFormatter formatter;

    @GET
    public String hello() {
        return formatter.format("Quarkus");
    }

    @GET
    @Path("/{name}")
    public String helloName(@PathParam("name") String name) {
        return formatter.format(name);
    }
}
