package com.mediscan.config;

import jakarta.servlet.*;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import java.io.IOException;

/**
 * Logs every incoming HTTP request (method, path, client IP) to console and file.
 */
@Slf4j
@Component
@Order(Ordered.HIGHEST_PRECEDENCE)
public class RequestLoggingFilter implements Filter {

	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
		throws IOException, ServletException {
		if (!(request instanceof HttpServletRequest httpRequest)) {
			chain.doFilter(request, response);
			return;
		}

		String method = httpRequest.getMethod();
		String uri = httpRequest.getRequestURI();
		String queryString = httpRequest.getQueryString();
		String fullPath = queryString != null ? uri + "?" + queryString : uri;
		String clientAddr = request.getRemoteAddr();

		log.info("[Request] {} {} | Client: {}", method, fullPath, clientAddr);

		chain.doFilter(request, response);
	}
}
