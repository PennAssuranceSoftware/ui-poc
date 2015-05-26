package demo;

import java.io.IOException;
import java.util.SortedMap;
import java.util.TreeMap;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;

import org.amdatu.security.tokenprovider.TokenProvider;
import org.amdatu.security.tokenprovider.TokenProviderException;
import org.amdatu.security.tokenprovider.TokenUtil;
import org.osgi.service.log.LogService;

public class SecurityFilter implements Filter {
	private volatile LogService m_log;
	private volatile TokenProvider m_tokenProvider;

	@Override
	public void destroy() {
		log("Filter: destroy");

	}

	private void log(String message) {
		if (m_log != null) {
			m_log.log(LogService.LOG_INFO, message);
		}

	}

	@Override
	public void doFilter(ServletRequest request, ServletResponse response,
			FilterChain chain) throws IOException, ServletException {
		log("Filter: filter");

		try {
			// TODO: This code would be in web service to login
			SortedMap<String, String> userMap = new TreeMap<>();
			userMap.put(TokenProvider.USERNAME, "user-id1");
			userMap.put("googletoken", "24234jklj2kljk234jkl");
			String token = m_tokenProvider.generateToken(userMap);
			log("Generated Token: " + token + " From Provider: "
					+ m_tokenProvider.getClass());
			log( "Existing Token: "  + TokenUtil.getTokenFromRequest((HttpServletRequest) request) );
		} catch (TokenProviderException exception) {
			log("Failed to generate token");
		}

		chain.doFilter(request, response);
	}

	@Override
	public void init(FilterConfig config) throws ServletException {
		log("Filter: init");

	}

}
