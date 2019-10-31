package de.jugmz.general.http;

import io.quarkus.vertx.http.runtime.filters.Filters;

import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.event.Observes;
import java.time.Duration;
import java.time.OffsetDateTime;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@ApplicationScoped
public class CacheHeaderFilter {

    private final static String EXPIRES_HEADER = "Expires";
    private final List<String> longCacheableContent;
    private final List<String> mediumCacheableContent;

    public CacheHeaderFilter() {
        this.longCacheableContent = Stream.of("png", "jpg", "svg").collect(Collectors.toList());
        this.mediumCacheableContent = Stream.of("js", "css").collect(Collectors.toList());
    }

    public void registerMyFilter(@Observes Filters filters) {
        filters.register(rc -> {
            if (longCacheableContent.stream().anyMatch(fileExtension -> rc.normalisedPath().endsWith(fileExtension))) {
                rc.response().putHeader(EXPIRES_HEADER, buildDateStringPlusDays(365));
            } else if (mediumCacheableContent.stream().anyMatch(fileExtension -> rc.normalisedPath().endsWith(fileExtension))) {
                rc.response().putHeader(EXPIRES_HEADER, buildDateStringPlusDays(30));
            }
            rc.next();
        }, 101);
    }

    private String buildDateStringPlusDays(int days) {
        OffsetDateTime oneHourFromNow = OffsetDateTime.now(ZoneOffset.UTC).plus(Duration.ofDays(days));
        return DateTimeFormatter.RFC_1123_DATE_TIME.format(oneHourFromNow);
    }
}
