package rest;

import dto.OwnershipDto;
import services.OwnershipService;

import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Path("/ownership")
public class OwnershipResource {

	@Inject
	OwnershipService ownershipService;

	@POST
	@Path("/create")
	@Produces(MediaType.APPLICATION_JSON)
	public Response create(OwnershipDto ownershipDto){
		try{
			ownershipService.createOwnership(ownershipDto);
			return Response.ok().build();
		}
		catch(Exception e){
			return Response.serverError().build();
		}
	}
}
