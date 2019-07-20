package de.jugmz.general.http;

import io.undertow.servlet.ServletExtension;
import io.undertow.servlet.api.DeploymentInfo;
import io.undertow.servlet.api.FilterInfo;

import javax.servlet.DispatcherType;
import javax.servlet.ServletContext;

public class ApplicationServletExtension implements ServletExtension {

    private final static String CACHE_HEADER_FILTER_NAME = "cache-header-filter";
    private final static String SECURITY_HEADER_FILTER_NAME = "security-header-filter";

    @Override
    public void handleDeployment(final DeploymentInfo deploymentInfo, final ServletContext servletContext) {
        deploymentInfo.addFilter(new FilterInfo(CACHE_HEADER_FILTER_NAME, CacheHeaderFilter.class));
        deploymentInfo.addFilterUrlMapping(CACHE_HEADER_FILTER_NAME, "/*", DispatcherType.REQUEST);

        deploymentInfo.addFilter(new FilterInfo(SECURITY_HEADER_FILTER_NAME, SecurityHeaderFilter.class));
        deploymentInfo.addFilterUrlMapping(SECURITY_HEADER_FILTER_NAME, "/*", DispatcherType.REQUEST);
    }

}
