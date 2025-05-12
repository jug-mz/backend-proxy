package de.jugmz.mobilizon;

import de.jugmz.SimplestCache;
import de.jugmz.mobilizon.model.Event;
import de.jugmz.mobilizon.model.OrganizedEvent;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import org.eclipse.microprofile.config.inject.ConfigProperty;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.net.URI;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;

@ApplicationScoped
public class MobilizonService {

    private final static String MEETUP_STATUS_UPCOMING = "upcoming";
    private final static String MEETUP_STATUS_PAST = "past";
    private final static int CACHE_DURATION_IN_SECONDS = 600;
    private static final Logger log = LoggerFactory.getLogger(MobilizonService.class);


    private final URI meetupUri;
    private final String homeId;
    private final String[] partnerIds;
    private final MobilizonClient client;

    private SimplestCache<List<Event>> upcomingCache;
    private SimplestCache<List<Event>> pastCache;

    @Inject
    public MobilizonService(@ConfigProperty(name = "service.meetup.uri") URI meetupUri,
                            @ConfigProperty(name = "service.meetup.homeId") String homeId,
                            @ConfigProperty(name = "service.meetup.partnerIds") Optional<String> partnerIds,
                            MobilizonClient mobilizonClient) {
        this.meetupUri = meetupUri;
        this.homeId = homeId;
        this.partnerIds = partnerIds.map(val -> val.split(",")).orElse(new String[0]);
        this.client = mobilizonClient;
        this.upcomingCache = new SimplestCache<>(CACHE_DURATION_IN_SECONDS, () -> this.requestAllUpcomingEvents(homeId));
        this.pastCache = new SimplestCache<>(CACHE_DURATION_IN_SECONDS, () -> this.requestAllUpcomingEvents(homeId)); //TODO past events
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
            return client.group(homeId).organizedEvents().elements()
                    .stream()
                    .sorted(Comparator.comparing(Event::beginsOn))
                    .toList();
        } catch (Exception e) {
            log.error("Upsi", e);
            throw e;
        }

    }

    private String[] appendToArray(String newElement, String[] sourceArray) {
        int partnerCount = sourceArray.length;
        String[] allIds = Arrays.copyOf(sourceArray, partnerCount + 1);
        allIds[partnerCount] = newElement;
        return allIds;
    }

}
