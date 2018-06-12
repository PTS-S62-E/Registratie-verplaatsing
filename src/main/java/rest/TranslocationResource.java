package rest;

import com.pts62.common.finland.util.JsonExceptionMapper;
import dto.TranslocationDto;
import exceptions.VehicleException;
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
	@Path("/")
	@Consumes(MediaType.APPLICATION_JSON)
	public Response create(TranslocationDto translocationDto){
		try{
			translocationService.createTranslocation(translocationDto);
			return Response.ok().build();
		}
		catch(Exception e){
			throw new WebApplicationException(Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(e.getMessage()).build());
		}
	}

	@GET
	@Path("/{id}/{startdate}/{enddate}")
	@Produces(MediaType.APPLICATION_JSON)
	public Response getByIdAndTimePeriod(@PathParam("id") long id,
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
			throw JsonExceptionMapper.mapException(Response.Status.INTERNAL_SERVER_ERROR, e.getMessage());
		}
	}

	@GET
	@Path("/licensePlate/{licensePlate}/{startdate}/{enddate}")
	@Produces(MediaType.APPLICATION_JSON)
	public Response getByLicensePlateAndTimePeriod(@PathParam("licensePlate") String licensePlate,
												   @PathParam("startdate") String startDate,
												   @PathParam("enddate") String endDate){
		try{
			return Response.ok(
					translocationService.getAdministrationDto(
							licensePlate,
							LocalDateTimeParser.stringToLocalDateTime(startDate),
							LocalDateTimeParser.stringToLocalDateTime(endDate))).build();
		}
		catch(Exception e){
			Sentry.capture(e.toString());
			throw JsonExceptionMapper.mapException(Response.Status.INTERNAL_SERVER_ERROR, e.getMessage());
		}
	}
}
