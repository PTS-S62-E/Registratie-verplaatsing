package rest;

import com.pts62.common.finland.util.JsonExceptionMapper;
import dto.TranslocationDto;
import exceptions.VehicleException;
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
		catch(VehicleException ve){
			throw new WebApplicationException(Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(ve.getMessage()).build());
		}
		catch(Exception e){
			return Response.serverError().build();
		}
	}

	@GET
	@Path("/{id}/{startdate}/{enddate}")
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
			throw JsonExceptionMapper.mapException(Response.Status.INTERNAL_SERVER_ERROR, e.getMessage());
		}
	}
}
