package rest;

import dto.VehicleDto;
import exceptions.CategoryException;
import exceptions.VehicleException;
import services.VehicleService;
import util.JsonExceptionMapper;
import util.LocalDateTimeParser;

import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Path("/vehicle")
public class VehicleResource {

	@Inject
	VehicleService vehicleService;

	@POST
	@Path("/")
	@Consumes(MediaType.APPLICATION_JSON)
	public Response create(VehicleDto vehicleDto){
		try{
			vehicleService.createVehicle(vehicleDto);
			return Response.ok().build();
		}
		catch(Exception e){
			throw new WebApplicationException(Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(e.getMessage()).build());
		}
	}

	@GET
	@Path("/id/{id}")
	@Produces(MediaType.APPLICATION_JSON)
	public Response get(@PathParam("id") long id){
		try{
			return Response.ok(
					vehicleService.getVehicle(id)).build();
		}
		catch(VehicleException ve){
			throw JsonExceptionMapper.mapException(Response.Status.INTERNAL_SERVER_ERROR, ve.getMessage());
		}
		catch(Exception e){
			throw JsonExceptionMapper.mapException(Response.Status.INTERNAL_SERVER_ERROR, e.getMessage());
		}
	}

	@GET
	@Path("/licensePlate/{licensePlate}")
	@Produces(MediaType.APPLICATION_JSON)
	public Response getByLicensePlate(@PathParam("licensePlate") String licensePlate){
		try{
			return Response.ok(
					vehicleService.getVehicle(licensePlate)).build();
		}
		catch(VehicleException ve){
			throw JsonExceptionMapper.mapException(Response.Status.INTERNAL_SERVER_ERROR, ve.getMessage());
		}
		catch(Exception e){
			throw JsonExceptionMapper.mapException(Response.Status.INTERNAL_SERVER_ERROR, e.getMessage());
		}
	}

	@GET
	@Path("foreignVehicles/{startdate}/{enddate}")
	@Produces(MediaType.APPLICATION_JSON)
	public Response getForeignVehiclesAndTranslocations(@PathParam("startdate") String startDate,
												   @PathParam("enddate") String endDate){
		try{
			return Response.ok(
					vehicleService.getForeignVehiclesAndTranslocations(
							LocalDateTimeParser.stringToLocalDateTime(startDate),
							LocalDateTimeParser.stringToLocalDateTime(endDate))).build();
		}
		catch(Exception e){
			throw JsonExceptionMapper.mapException(Response.Status.INTERNAL_SERVER_ERROR, e.getMessage());
		}
	}

	@PUT
	@Path("/")
	@Consumes(MediaType.APPLICATION_JSON)
	public Response update(VehicleDto vehicleDto){
		try{
			vehicleService.updateVehicle(vehicleDto);
			return Response.ok().build();
		}
		catch(VehicleException ve){
			throw new WebApplicationException(Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(ve.getMessage()).build());
		}
		catch(CategoryException ce){
			throw new WebApplicationException(Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(ce.getMessage()).build());
		}
		catch(Exception e){
			return Response.serverError().build();
		}
	}
}
