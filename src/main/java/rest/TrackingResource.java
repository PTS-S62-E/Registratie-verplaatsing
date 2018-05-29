package rest;


import services.TrackingService;
import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Path("/tracking")
public class TrackingResource {

	@Inject
	TrackingService trackingService;

	@POST
	@Path("/")
	@Consumes(MediaType.APPLICATION_JSON)
	public Response create(String licensePlate){
		try{
			trackingService.createTracking(licensePlate);
			return Response.ok().build();
		}
		catch(Exception e){
			throw new WebApplicationException(Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(e.getMessage()).build());
		}
	}

	@DELETE
	@Path("/")
	@Consumes(MediaType.APPLICATION_JSON)
	public Response delete(String licensePlate){
		try{
			trackingService.deleteTracking(licensePlate);
			return Response.ok().build();
		}
		catch(Exception e){
			throw new WebApplicationException(Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(e.getMessage()).build());
		}
	}
}
