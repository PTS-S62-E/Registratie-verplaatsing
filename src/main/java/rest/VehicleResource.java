package rest;

import entities.Vehicle;
import exceptions.VehicleException;
import services.VehicleService;
import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Path("/vehicle")
public class VehicleResource {

	@Inject
	VehicleService vehicleService;

	@POST
	@Path("/create")
	@Consumes(MediaType.APPLICATION_JSON)
	public Response create(Vehicle vehicle){
		try{
			vehicleService.createVehicle(vehicle);
			return Response.ok().build();
		}
		catch(VehicleException e){
			throw new WebApplicationException(Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(e.getMessage()).build());
		}
	}

	@GET
	@Path("/get/{id}")
	@Produces(MediaType.APPLICATION_JSON)
	public Response get(@PathParam("id") long id){
		try{
			return Response.ok(
					vehicleService.getVehicle(id)).build();
		}
		catch(Exception e){
			return Response.serverError().build();
		}
	}

	@PUT
	@Path("/update")
	@Produces(MediaType.APPLICATION_JSON)
	public Response update(Vehicle vehicle){
		try{
			vehicleService.updateVehicle(vehicle);
			return Response.ok().build();
		}
		catch(Exception e){
			return Response.serverError().build();
		}
	}
}
