package de.jugmz.meetup;

import org.eclipse.microprofile.config.inject.ConfigProperty;
import org.eclipse.microprofile.rest.client.RestClientBuilder;

import javax.annotation.PostConstruct;
import javax.enterprise.context.ApplicationScoped;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.stream.Collectors;

@Path("/api/meetup")
@ApplicationScoped
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
    @Produces(MediaType.APPLICATION_JSON)
    public Response getUpcoming() {
        List<MeetupEvent> meetupEvents = client.getEvents("upcoming", false);
        List<EventDto> responseEvents = meetupEvents.stream().map(EventDto::new).collect(Collectors.toList());
        return Response.ok(responseEvents).build();
    }

    @GET
    @Path("/past")
    @Produces(MediaType.APPLICATION_JSON)
    public Response hello() {
        List<MeetupEvent> meetupEvents = client.getEvents("past", true);
        List<EventDto> responseEvents = meetupEvents.stream().map(EventDto::new).collect(Collectors.toList());
        return Response.ok(responseEvents).build();
    }
}