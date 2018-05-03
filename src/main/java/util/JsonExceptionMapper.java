package util;

import javax.annotation.PostConstruct;
import javax.ejb.Singleton;
import javax.ejb.Startup;
import javax.json.Json;
import javax.json.JsonBuilderFactory;
import javax.json.JsonObject;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.Response;
import java.util.HashMap;
import java.util.Map;

@Startup
@Singleton
public class JsonExceptionMapper {

	private static Map<String, Object> config;

	@PostConstruct
	public void setup(){
		config = new HashMap<>();
		config.put("javax.json.stream.JsonGenerator.prettyPrinting", Boolean.valueOf(true));
	}

	private static JsonObject mapObject(Response.Status status, String message){
		JsonBuilderFactory factory = Json.createBuilderFactory(config);
		JsonObject jsonObject = factory.createObjectBuilder()
				.add("error", factory.createObjectBuilder()
				.add("code", Integer.toString(status.getStatusCode()))
				.add("status", status.toString())
				.add("message", message))
				.build();

		return jsonObject;
	}

	public static WebApplicationException mapException(Response.Status status, String message){
		return new WebApplicationException(Response.status(status)
				.entity(mapObject(status, message))
				.build());
	}

}
