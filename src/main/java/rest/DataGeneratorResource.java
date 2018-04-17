package rest;

import datagenerator.DataGenerator;

import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.core.Response;

@Path("/datagenerator")
public class DataGeneratorResource {

	@Inject
	DataGenerator dataGenerator;

	@GET
	@Path("/generate")
	public Response get(){
		try{
			dataGenerator.generateTestData();
			return Response.ok().build();
		}
		catch(Exception e){
			return Response.serverError().build();
		}
	}
}
