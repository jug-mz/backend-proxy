package de.jugmz.meetup;

import de.jugmz.SimplestCache;
import de.jugmz.meetup.api.MeetupClient;
import de.jugmz.meetup.api.MeetupEvent;
import org.eclipse.microprofile.config.inject.ConfigProperty;
import org.eclipse.microprofile.rest.client.RestClientBuilder;

import javax.annotation.PostConstruct;
import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import java.net.URI;
import java.util.Arrays;
import java.util.Collection;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@ApplicationScoped
public class MeetupService {

    private final static String MEETUP_STATUS_UPCOMING = "upcoming";
    private final static String MEETUP_STATUS_PAST = "past";
    private final static int CACHE_DURATION_IN_SECONDS = 600;


    private URI meetupUri;
    private String homeId;
    private String[] partnerIds;
    private MeetupClient client;
    private SimplestCache<List<MeetupEvent>> upcomingCache;
    private SimplestCache<List<MeetupEvent>> pastCache;

    public MeetupService() {
    }

    @Inject
    public MeetupService(@ConfigProperty(name = "service.meetup.uri") URI meetupUri,
                         @ConfigProperty(name = "service.meetup.homeId") String homeId,
                         @ConfigProperty(name = "service.meetup.partnerIds") Optional<String> partnerIds) {
        this.meetupUri = meetupUri;
        this.homeId = homeId;
        this.partnerIds = partnerIds.map(val -> val.split(",")).orElse(new String[0]);
    }

    @PostConstruct
    public void initialize() {
        this.client = RestClientBuilder
                .newBuilder()
                .baseUri(meetupUri)
                .build(MeetupClient.class);

        this.upcomingCache = new SimplestCache<>(CACHE_DURATION_IN_SECONDS, () -> this.requestAllUpcomingEvents(homeId, partnerIds));
        this.pastCache = new SimplestCache<>(CACHE_DURATION_IN_SECONDS, () -> client.getEvents(homeId, MEETUP_STATUS_PAST, true));
    }

    public List<MeetupEvent> getUpcoming() {
        return upcomingCache.loadOrGet();
    }

    public List<MeetupEvent> getPast() {
        return pastCache.loadOrGet();
    }


    private List<MeetupEvent> requestAllUpcomingEvents(String homeId, String[] partnerIds) {
        String[] allIds = appendToArray(homeId, partnerIds);

        return Arrays
                .stream(allIds)
                .parallel()
                .map(groupId -> client.getEvents(groupId, MEETUP_STATUS_UPCOMING, false))
                .flatMap(Collection::stream)
                .sorted(Comparator
                        .comparing(MeetupEvent::getLocalDate)
                        .thenComparing(MeetupEvent::getLocalTime))
                .collect(Collectors.toList());
    }

    private String[] appendToArray(String newElement, String[] sourceArray) {
        int partnerCount = sourceArray.length;
        String[] allIds = Arrays.copyOf(sourceArray, partnerCount + 1);
        allIds[partnerCount] = newElement;
        return allIds;
    }

}
