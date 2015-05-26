package demo;

import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Request;
import javax.ws.rs.core.UriInfo;

import org.apache.felix.dm.annotation.api.Component;

@Component(provides = Object.class)
@Path("/router")
public class Router {

	@Path("/{name}")
	@Produces(MediaType.APPLICATION_XHTML_XML)
	public String find(@Context UriInfo uriInfo, @Context Request request) {
		// Object found = /* some object found by name */

		return "<html></html>";
	}
}