package de.jugmz.meetup;

import org.eclipse.microprofile.config.inject.ConfigProperty;
import org.eclipse.microprofile.rest.client.RestClientBuilder;

import javax.annotation.PostConstruct;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.stream.Collectors;

@Path("/api/meetup")
@Produces(MediaType.APPLICATION_JSON)
public class MeetupController {

    @ConfigProperty(name = "service.meetup.uri")
    private URI meetupUri;

    private MeetupClient client;

    @PostConstruct
    public void initialize() throws URISyntaxException {
        this.client = RestClientBuilder
                .newBuilder()
                .baseUri(meetupUri)
                .build(MeetupClient.class);
    }

    @GET
    @Path("/upcoming")
    public List<EventDto> getUpcoming() {
        List<MeetupEvent> meetupEvents = client.getEvents("upcoming", false);
        return meetupEvents.stream().map(EventDto::new).collect(Collectors.toList());
    }

    @GET
    @Path("/past")
    public List<EventDto> hello() {
        List<MeetupEvent> meetupEvents = client.getEvents("past", true);
        return meetupEvents.stream().map(EventDto::new).collect(Collectors.toList());
    }
}