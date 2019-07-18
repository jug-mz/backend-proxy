package de.jugmz.general.http;

import io.undertow.servlet.spec.HttpServletRequestImpl;
import io.undertow.servlet.spec.HttpServletResponseImpl;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * We don't want the application to be accessible via plain HTTP, so we redirect every attempt to do so to use HTTPS
 */
public class HttpsForwardHeaderFilter implements Filter {

    private final static String HTTP_SCHEME = "http";

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        if (servletRequest.getScheme().equals(HTTP_SCHEME)) {
            HttpServletResponse response = new HttpServletResponseImpl(((HttpServletRequestImpl) servletRequest).getExchange(), ((HttpServletRequestImpl) servletRequest).getServletContext());
            response.sendRedirect(String.format("https://%s", ((HttpServletRequestImpl) servletRequest).getServerName()));
        } else {
            filterChain.doFilter(servletRequest, servletResponse);
        }
    }

}