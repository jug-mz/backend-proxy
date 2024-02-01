package de.jugmz.general.http;

import io.quarkus.vertx.http.runtime.filters.Filters;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.enterprise.event.Observes;

@ApplicationScoped
public class SecurityHeaderFilter {

    public void registerMyFilter(@Observes Filters filters) {
        filters.register(rc -> {
            rc.response().putHeader("Strict-Transport-Security", "max-age=63072000");
            rc.response().putHeader("Content-Security-Policy", "default-src 'self'; font-src data: 'self'; img-src data: 'self'; frame-ancestors 'none'");
            rc.response().putHeader("X-Content-Type-Options", "nosniff");
            rc.response().putHeader("X-Frame-Options", "deny");
            rc.response().putHeader("X-XSS-Protection", "1; mode=block");

            rc.next();
        }, 100);
    }
}
