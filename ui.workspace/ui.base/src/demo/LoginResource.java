package demo;

import java.util.SortedMap;
import java.util.TreeMap;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.NewCookie;
import javax.ws.rs.core.Response;

import org.amdatu.security.tokenprovider.InvalidTokenException;
import org.amdatu.security.tokenprovider.TokenProvider;
import org.amdatu.security.tokenprovider.TokenProviderException;
import org.apache.felix.dm.annotation.api.Component;
import org.apache.felix.dm.annotation.api.ServiceDependency;

import com.fasterxml.jackson.annotation.JsonProperty;

@Component(provides = Object.class)
@Path("login")
public class LoginResource {
	@ServiceDependency(required = true)
	private volatile TokenProvider m_tokenProvider;

	public static class Credentials {
		@JsonProperty("id")
		private String m_id;
		@JsonProperty("password")
		private String m_password;

		public String getId() {
			return m_id;
		}

		public void setId(String id) {
			m_id = id;
		}

		public String getPassword() {
			return m_password;
		}

		public void setPassword(String password) {
			m_password = password;
		}
	}

	@POST
	@Consumes("application/json")
	public Response login(Credentials credentials)
			throws TokenProviderException {
		// TODO: Need actual login logic to verify credentials
		final SortedMap<String, String> userMap = new TreeMap<>();
		userMap.put(TokenProvider.USERNAME, credentials.getId());
		userMap.put("password", credentials.getPassword());
		final String token = m_tokenProvider.generateToken(userMap);

		return Response.ok().cookie(new NewCookie("amdatu_token", token))
				.build();
	}

	@GET
	@Produces("application/json")
	public String token(@Context HttpServletRequest request)
			throws TokenProviderException, InvalidTokenException {
		final String token = m_tokenProvider.getTokenFromRequest(request);
		String result = "{}";
		if (token != null) {
			SortedMap<String, String> tokenProperties = m_tokenProvider
					.verifyToken(token);
			result = String.format("{user:'%s', token: '%s'}",
					tokenProperties.get(TokenProvider.USERNAME), token);
		}
		return result;
	}
}
