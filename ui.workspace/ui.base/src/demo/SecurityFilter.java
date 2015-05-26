package demo;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.amdatu.security.tokenprovider.InvalidTokenException;
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
		final String token = TokenUtil
				.getTokenFromRequest((HttpServletRequest) request);
		try {
			log("Existing Token: " + token);
			if (token != null) {
				m_tokenProvider.verifyToken(token);
			}
			else {
				final HttpServletResponse httpResponse = (HttpServletResponse) response;
				httpResponse.sendRedirect("/login.html");
			}
		} catch (InvalidTokenException exception) {
			log("Failed verify token: " + token);
			// Redirect
			final HttpServletResponse httpResponse = (HttpServletResponse) response;
			httpResponse.sendRedirect("/login.html");
			// TODO: This should be configured somewhere
		} catch (TokenProviderException exception) {
			// TODO: handle exception
			log("Failed verify token: " + token);
		}

		chain.doFilter(request, response);
	}

	@Override
	public void init(FilterConfig config) throws ServletException {
		log("Filter: init");

	}

}
