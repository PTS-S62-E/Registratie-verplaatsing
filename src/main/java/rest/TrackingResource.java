package rest;

import dto.TrackingDto;
import dto.VehicleDto;
import entities.Tracking;
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
	public Response create(TrackingDto trackingDto){
		try{
			trackingService.createTracking(trackingDto);
			return Response.ok().build();
		}
		catch(Exception e){
			throw new WebApplicationException(Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(e.getMessage()).build());
		}
	}

	@DELETE
	@Path("/")
	@Consumes(MediaType.APPLICATION_JSON)
	public Response delete(TrackingDto trackingdto){
		try{
			trackingService.deleteTracking(trackingdto.getSessionId(), trackingdto.getLicensePlate());
			return Response.ok().build();
		}
		catch(Exception e){
			throw new WebApplicationException(Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(e.getMessage()).build());
		}
	}
}
