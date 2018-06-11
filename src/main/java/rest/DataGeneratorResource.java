package rest;

import com.fasterxml.jackson.databind.JsonNode;
import datagenerator.DataGenerator;
import dto.VehicleDto;

import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.core.Response;

@Path("/datagenerator")
public class DataGeneratorResource {

	@Inject
	DataGenerator dataGenerator;

	@Inject
	VehicleResource vehicleResource;

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

	@POST
	@Path("/vehicles")
	public Response createVehicles(JsonNode data) {
		for(int i = 0; i < data.size(); i++) {
			VehicleDto dto = new VehicleDto();

			dto.setCountryCode(data.get(i).get("country").asText());
			dto.setLicensePlate("AB-" + (i + 10) + "-CD");
			dto.setSerialNumber(data.get(i).get("id").asText());

			if(i < 25) {
				dto.setCategory("TRUCK");
				dto.setBrand("Scania");
				dto.setType("TRUCK");
			} else if(25 < i && i < 50) {
				dto.setCategory("CAR");
				dto.setBrand("Audi");
				dto.setType("CAR");
			} else if(50 < i && i < 75) {
				dto.setCategory("TRUCK");
				dto.setBrand("DAF");
				dto.setType("TRUCK");
			} else if(75 < i && i < 100) {
				dto.setCategory("CAR");
				dto.setBrand("BMW");
				dto.setType("CAR");
			} else {
				dto.setCategory("CAR");
				dto.setBrand("Peugeot");
				dto.setType("CAR");
			}

			vehicleResource.create(dto);
		}

		return Response.ok().build();
	}
}
