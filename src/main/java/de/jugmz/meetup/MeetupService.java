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
import java.util.List;

@ApplicationScoped
public class MeetupService {

    private final static String MEETUP_STATUS_UPCOMING = "upcoming";
    private final static String MEETUP_STATUS_PAST = "past";
    private final static int CACHE_DURATION_IN_SECONDS = 600;

    private URI meetupUri;
    private String homeId;
    private List<String> partnerIds;

    public MeetupService() {}

    @Inject
    public MeetupService(@ConfigProperty(name = "service.meetup.uri") URI meetupUri,
                         @ConfigProperty(name = "service.meetup.homeId") String homeId,
                         @ConfigProperty(name = "service.meetup.partnerIds")List<String> partnerIds) {
        this.meetupUri = meetupUri;
        this.homeId = homeId;
        this.partnerIds = partnerIds;
    }

    private MeetupClient client;

    private SimplestCache<List<MeetupEvent>> upcomingCache;
    private SimplestCache<List<MeetupEvent>> pastCache;

    @PostConstruct
    public void initialize() {
        this.client = RestClientBuilder
                .newBuilder()
                .baseUri(meetupUri)
                .build(MeetupClient.class);

        this.upcomingCache = new SimplestCache<>(CACHE_DURATION_IN_SECONDS, () -> client.getEvents(homeId, MEETUP_STATUS_UPCOMING, false));
        this.pastCache = new SimplestCache<>(CACHE_DURATION_IN_SECONDS, () -> client.getEvents(homeId, MEETUP_STATUS_PAST, true));
    }

    public List<MeetupEvent> getUpcoming() {
        return upcomingCache.loadOrGet();
    }

    public List<MeetupEvent> getPast() {
        return pastCache.loadOrGet();
    }

}
