package de.jugmz.general.http;

import io.undertow.servlet.ServletExtension;
import io.undertow.servlet.api.DeploymentInfo;
import io.undertow.servlet.api.FilterInfo;

import javax.servlet.DispatcherType;
import javax.servlet.ServletContext;

public class ApplicationServletExtension implements ServletExtension {

    private final static String HEADER_FILTER_NAME = "header-filter";
    private final static String HTTPS_FORWARD_FILTER_NAME = "https-filter";

    @Override
    public void handleDeployment(final DeploymentInfo deploymentInfo, final ServletContext servletContext) {
        deploymentInfo.addFilter(new FilterInfo(HEADER_FILTER_NAME, CacheHeaderFilter.class));
        deploymentInfo.addFilterUrlMapping(HEADER_FILTER_NAME, "/*", DispatcherType.REQUEST);

        deploymentInfo.addFilter(new FilterInfo(HTTPS_FORWARD_FILTER_NAME, HttpsForwardHeaderFilter.class));
        deploymentInfo.addFilterUrlMapping(HTTPS_FORWARD_FILTER_NAME, "/*", DispatcherType.REQUEST);
    }

}
