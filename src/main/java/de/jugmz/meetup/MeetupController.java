package de.jugmz.meetup;

import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import java.util.List;
import java.util.stream.Collectors;

@Path("/api/meetup")
@Produces(MediaType.APPLICATION_JSON)
public class MeetupController {

    private MeetupService meetupService;

    private MeetupMapper meetupMapper;

    @Inject
    public MeetupController(MeetupService meetupService, MeetupMapper meetupMapper) {
        this.meetupService = meetupService;
        this.meetupMapper = meetupMapper;
    }

    @GET
    @Path("/upcoming")
    public List<EventDto> getUpcoming() {
        return meetupService.getUpcoming()
                .stream()
                .map(meetupMapper::toDto)
                .collect(Collectors.toList());
    }

    @GET
    @Path("/past")
    public List<EventDto> getPast() {
        return meetupService.getPast()
                .stream()
                .map(meetupMapper::toDto)
                .collect(Collectors.toList());
    }
}
