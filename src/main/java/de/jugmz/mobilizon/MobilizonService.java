package de.jugmz.mobilizon;

import de.jugmz.SimplestCache;
import de.jugmz.mobilizon.model.Event;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import org.eclipse.microprofile.config.inject.ConfigProperty;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.net.URI;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;

@ApplicationScoped
public class MobilizonService {

    private final static String MEETUP_STATUS_UPCOMING = "upcoming";
    private final static String MEETUP_STATUS_PAST = "past";
    private final static int CACHE_DURATION_IN_SECONDS = 600;
    private static final Logger log = LoggerFactory.getLogger(MobilizonService.class);

    private final MobilizonClient client;

    private SimplestCache<List<Event>> upcomingCache;
    private SimplestCache<List<Event>> pastCache;

    @Inject
    public MobilizonService(MobilizonClient mobilizonClient,
                            @ConfigProperty(name = "service.meetup.homeId") String homeId) {
        this.client = mobilizonClient;
        this.upcomingCache = new SimplestCache<>(CACHE_DURATION_IN_SECONDS, () -> this.requestAllUpcomingEvents(homeId));
        this.pastCache = new SimplestCache<>(CACHE_DURATION_IN_SECONDS, () -> this.requestAllPastEvents(homeId));
    }

    public List<Event> getUpcoming() {
        return upcomingCache.loadOrGet();
    }

    public List<Event> getUpcomingFromCache() {
        List<Event> cachedEvents = upcomingCache.getCached();
        return (cachedEvents == null) ? new ArrayList<>() : cachedEvents;
    }

    public List<Event> getPast() {
        return pastCache.loadOrGet();
    }

    public List<Event> getPastFromCache() {
        List<Event> cachedEvents = pastCache.getCached();
        return (cachedEvents == null) ? new ArrayList<>() : cachedEvents;
    }

    private List<Event> requestAllUpcomingEvents(String homeId) {
        try {
            LocalDate today = LocalDate.now();
            return client.group(homeId).organizedEvents().elements()
                    .stream()
                    .filter(event -> !event.beginsOn().toLocalDate().isBefore(today))
                    .sorted(Comparator.comparing(Event::beginsOn))
                    .toList();
        } catch (Exception e) {
            log.error("Upsi", e);
            throw e;
        }
    }

    private List<Event> requestAllPastEvents(String homeId) {

        try {
            LocalDate today = LocalDate.now();
            return client.group(homeId).organizedEvents().elements()
                    .stream()
                    .filter(event -> event.beginsOn().toLocalDate().isBefore(today))
                    .sorted(Comparator.comparing(Event::beginsOn))
                    .toList();
        } catch (Exception e) {
            log.error("Upsi", e);
            throw e;
        }

    }

}
