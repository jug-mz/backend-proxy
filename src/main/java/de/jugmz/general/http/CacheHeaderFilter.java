package de.jugmz.general.http;

import io.undertow.servlet.spec.HttpServletRequestImpl;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpServletResponseWrapper;
import java.io.IOException;
import java.time.Duration;
import java.time.OffsetDateTime;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * Static resources should send a Cache Header to the browser
 */
public class CacheHeaderFilter implements Filter {

    private final static String EXPIRES_HEADER = "Expires";
    private final List<String> longCachableContent;
    private final List<String> mediumCachableContent;

    public CacheHeaderFilter() {
        this.longCachableContent = Stream.of("png", "jpg", "svg").collect(Collectors.toList());
        this.mediumCachableContent = Stream.of("js", "css").collect(Collectors.toList());
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {

        if (longCachableContent.stream().anyMatch(fileExtension -> ((HttpServletRequestImpl) servletRequest).getServletPath().endsWith(fileExtension))) {
            HttpServletResponseWrapper wrapper = new HttpServletResponseWrapper((HttpServletResponse) servletResponse);
            wrapper.setHeader(EXPIRES_HEADER, buildDateStringPlusDays(365));
            filterChain.doFilter(servletRequest, wrapper);
        } else if (mediumCachableContent.stream().anyMatch(fileExtension -> ((HttpServletRequestImpl) servletRequest).getServletPath().endsWith(fileExtension))) {
            HttpServletResponseWrapper wrapper = new HttpServletResponseWrapper((HttpServletResponse) servletResponse);
            wrapper.setHeader(EXPIRES_HEADER, buildDateStringPlusDays(30));
            filterChain.doFilter(servletRequest, wrapper);
        } else {
            filterChain.doFilter(servletRequest, servletResponse);
        }

    }

    private String buildDateStringPlusDays(int days) {
        OffsetDateTime oneHourFromNow = OffsetDateTime.now(ZoneOffset.UTC).plus(Duration.ofDays(days));
        return DateTimeFormatter.RFC_1123_DATE_TIME.format(oneHourFromNow);
    }

}
