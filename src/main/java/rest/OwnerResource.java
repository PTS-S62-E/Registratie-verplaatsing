package rest;

import services.OwnerService;
import entities.Owner;

import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Path("/owner")
public class OwnerResource {

	@Inject
	OwnerService ownerService;

	@POST
	@Path("/create")
	@Produces(MediaType.APPLICATION_JSON)
	public Response create(Owner owner){
		try{
			ownerService.createOwner(owner);
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
					ownerService.getOwner(id)).build();
		}
		catch(Exception e){
			return Response.serverError().build();
		}
	}

	@PUT
	@Path("/update")
	@Produces(MediaType.APPLICATION_JSON)
	public Response update(Owner owner){
		try{
			ownerService.updateOwner(owner);
			return Response.ok().build();
		}
		catch(Exception e){
			return Response.serverError().build();
		}
	}

}
