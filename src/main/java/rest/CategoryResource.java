package rest;

import dto.VehicleDto;
import entities.Category;
import exceptions.CategoryException;
import exceptions.VehicleException;
import services.CategoryService;

import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Path("/category")
public class CategoryResource {

	@Inject
	CategoryService categoryService;

	@POST
	@Path("/create")
	@Consumes(MediaType.APPLICATION_JSON)
	public Response create(Category category){
		try{
			categoryService.createCategory(category);
			return Response.ok().build();
		}
		catch(CategoryException ce){
			throw new WebApplicationException(Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(ce.getMessage()).build());
		}
		catch(Exception e){
			return Response.serverError().build();
		}
	}
}
