package rest;

import dto.TranslocationDto;
import entities.Translocation;
import services.TranslocationService;
import util.LocalDateTimeParser;

import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Path("/translocation")
public class TranslocationResource {

	@Inject
	TranslocationService translocationService;

	@GET
	@Path("/test")
	@Produces(MediaType.APPLICATION_JSON)
	public Response test(){
		try{
			return Response.ok(
					"hello translocation").build();
		}
		catch(Exception e){
			return Response.serverError().build();
		}
	}

	@POST
	@Path("/create")
	@Consumes(MediaType.APPLICATION_JSON)
	public Response create(TranslocationDto translocationDto){
		try{
			translocationService.createTranslocation(translocationDto);
			return Response.ok().build();
		}
		catch(Exception e){
			return Response.serverError().build();
		}
	}

	@GET
	@Path("/get/{id}")
	@Produces(MediaType.APPLICATION_JSON)
	public Response get(@PathParam("id") long id){
		try{
			return Response.ok(
					translocationService.getTranslocation(id)).build();
		}
		catch(Exception e){
			return Response.serverError().build();
		}
	}

	@GET
	@Path("/get/{licenseplate}/{startdate}/{enddate}")
	@Produces(MediaType.APPLICATION_JSON)
	public Response getByLicensePlateAndTimePeriod(@PathParam("licenseplate") String licensePlate,
												   @PathParam("startdate") String startDate,
												   @PathParam("enddate") String endDate){
		try{
			return Response.ok(
					translocationService.getTranslocations(
							licensePlate,
							LocalDateTimeParser.stringToLocalDateTime(startDate),
							LocalDateTimeParser.stringToLocalDateTime(endDate))).build();
		}
		catch(Exception e){
			return Response.serverError().build();
		}
	}

	@GET
	@Path("/get/byVehicleId/{id}")
	@Produces(MediaType.APPLICATION_JSON)
	public Response getByVehicleId(@PathParam("id") long id){
		try{
			return Response.ok(
					translocationService.getTranslocationsByVehicleId(id)).build();
		}
		catch(Exception e){
			return Response.serverError().build();
		}
	}

	@PUT
	@Path("/update")
	@Consumes(MediaType.APPLICATION_JSON)
	public Response update(Translocation translocation){
		try{
			translocationService.updateTranslocation(translocation);
			return Response.ok().build();
		}
		catch(Exception e){
			return Response.serverError().build();
		}
	}
}
