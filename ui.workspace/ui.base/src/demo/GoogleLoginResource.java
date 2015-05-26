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

@Component(provides = Object.class)
@Path("googlelogin")
public class GoogleLoginResource {
	@ServiceDependency(required = true)
	private volatile TokenProvider m_tokenProvider;

	public static class GoogleUser {
		private String m_id;
		private String m_accessToken;

		public String getId() {
			return m_id;
		}

		public void setId(String id) {
			m_id = id;
		}

		public String getAccessToken() {
			return m_accessToken;
		}

		public void setAccessToken(String token) {
			m_accessToken = token;
		}
	}

	@POST
	@Consumes("application/json")
	public Response googleLogin(GoogleUser user) throws TokenProviderException {
		SortedMap<String, String> userMap = new TreeMap<>();
		userMap.put(TokenProvider.USERNAME, user.getId());
		userMap.put("googletoken", user.getAccessToken());
		String token = m_tokenProvider.generateToken(userMap);

		return Response.ok().cookie(new NewCookie("amdatu_token", token))
				.build();
	}

	@GET
	@Produces("application/json")
	public String getGoogleAuthToken(@Context HttpServletRequest request)
			throws TokenProviderException, InvalidTokenException {
		String token = m_tokenProvider.getTokenFromRequest(request);
		String result = "{}";
		if (token != null) {
			SortedMap<String, String> tokenProperties = m_tokenProvider
					.verifyToken(token);
			result = tokenProperties.get("googletoken");
		}
		return result;
	}
}
