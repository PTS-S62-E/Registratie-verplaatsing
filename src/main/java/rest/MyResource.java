package rest;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.core.Response;

@Path("/myResource")
public class MyResource {

	@GET
	@Path("/helloWorld")
	public Response helloWorld(){
		try{
			return Response.ok("Hello World!").build();
		}
		catch(Exception e){
			return Response.serverError().build();
		}
	}
}
