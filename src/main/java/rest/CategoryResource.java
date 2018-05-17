package rest;

import entities.Category;
import exceptions.CategoryException;
import services.CategoryService;
import util.JsonExceptionMapper;
import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Path("/category")
public class CategoryResource {

	@Inject
	CategoryService categoryService;

	@GET
	@Path("/")
	@Produces(MediaType.APPLICATION_JSON)
	public Response getAll(){
		try{
			return Response.ok(categoryService.getCategories()).build();
		}
		catch(Exception e){
			throw JsonExceptionMapper.mapException(Response.Status.INTERNAL_SERVER_ERROR, e.getMessage());
		}
	}

	@GET
	@Path("/{name}")
	@Produces(MediaType.APPLICATION_JSON)
	public Response get(@PathParam("name") String name){
		try{
			return Response.ok(categoryService.getCategory(name)).build();
		}
		catch(Exception e){
			throw JsonExceptionMapper.mapException(Response.Status.INTERNAL_SERVER_ERROR, e.getMessage());
		}
	}

	@POST
	@Path("/")
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
