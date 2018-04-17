package rest;

import dto.TranslocationDto;
import io.sentry.Sentry;
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
	@Path("/get/{id}/{startdate}/{enddate}")
	@Produces(MediaType.APPLICATION_JSON)
	public Response getByLicensePlateAndTimePeriod(@PathParam("id") long id,
												   @PathParam("startdate") String startDate,
												   @PathParam("enddate") String endDate){
		try{
			return Response.ok(
					translocationService.getAdministrationDto(
							id,
							LocalDateTimeParser.stringToLocalDateTime(startDate),
							LocalDateTimeParser.stringToLocalDateTime(endDate))).build();
		}
		catch(Exception e){
			e.printStackTrace();
			Sentry.capture(e);
			return Response.serverError().build();
		}
	}
}
