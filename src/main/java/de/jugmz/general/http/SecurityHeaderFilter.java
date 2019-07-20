package de.jugmz.general.http;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpServletResponseWrapper;
import java.io.IOException;

/**
 * Collection of security headers
 */
public class SecurityHeaderFilter implements Filter {

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletResponseWrapper wrapper = new HttpServletResponseWrapper((HttpServletResponse) servletResponse);
        wrapper.setHeader("Strict-Transport-Security", "max-age=63072000");
        wrapper.setHeader("Content-Security-Policy", "default-src 'self'; font-src data: 'self'; img-src data: 'self'; frame-ancestors 'none'");
        wrapper.setHeader("X-Content-Type-Options", "nosniff");
        wrapper.setHeader("X-Frame-Options", "deny");
        wrapper.setHeader("X-XSS-Protection", "1; mode=block");
        filterChain.doFilter(servletRequest, wrapper);
    }

}
