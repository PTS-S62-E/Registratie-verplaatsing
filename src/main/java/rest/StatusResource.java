package rest;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.core.Response;

@Path("/status")
public class StatusResource {

    @GET
    @Path("/")
    public Response getStatus() {
        return Response.ok().build();
    }
}
