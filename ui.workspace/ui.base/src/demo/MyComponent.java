package demo;

import java.util.Arrays;
import java.util.List;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.apache.felix.dm.annotation.api.Component;

@Component(provides = Object.class)
@Path("demo")
public class MyComponent {

	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public List<String> list() {
		return Arrays.asList("test", "test2", "test3");
	}

}