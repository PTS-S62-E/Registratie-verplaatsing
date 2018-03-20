package rest;

import entities.Translocation;
import services.TranslocationService;

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
	@Produces(MediaType.APPLICATION_JSON)
	public Response create(Translocation translocation){
		try{
			return Response.ok(
					translocationService.createTranslocation(translocation);
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
	@Produces(MediaType.APPLICATION_JSON)
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
