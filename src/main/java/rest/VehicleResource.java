package rest;

import dto.CreateVehicleDto;
import entities.Vehicle;
import exceptions.VehicleException;
import services.VehicleService;
import util.JsonExceptionMapper;

import javax.inject.Inject;
import javax.json.JsonObject;
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
	public Response create(CreateVehicleDto createVehicleDto){
		try{
			vehicleService.createVehicle(createVehicleDto);
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
		catch(VehicleException e){
			throw JsonExceptionMapper.mapException(Response.Status.INTERNAL_SERVER_ERROR, e.getMessage());
		}
	}

	@PUT
	@Path("/update")
	@Produces(MediaType.APPLICATION_JSON)
	public Response update(CreateVehicleDto createVehicleDto){
		try{
			vehicleService.updateVehicle(createVehicleDto);
			return Response.ok().build();
		}
		catch(Exception e){
			return Response.serverError().build();
		}
	}
}
