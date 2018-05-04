package rest;

import dto.CreateTranslocationDto;
import dto.TranslocationDto;
import exceptions.TranslocationException;
import exceptions.VehicleException;
import io.sentry.Sentry;
import services.TranslocationService;
import util.JsonExceptionMapper;
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
	public Response create(CreateTranslocationDto createTranslocationDto){
		try{
			translocationService.createTranslocation(createTranslocationDto);
			return Response.ok().build();
		}
		catch(VehicleException ve){
			throw new WebApplicationException(Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(ve.getMessage()).build());
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
		catch(TranslocationException te){
			throw JsonExceptionMapper.mapException(Response.Status.INTERNAL_SERVER_ERROR, te.getMessage());
		}
		catch(Exception e){
			throw JsonExceptionMapper.mapException(Response.Status.INTERNAL_SERVER_ERROR, e.getMessage());
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
			return Response.serverError().build();
		}
	}
}
