package de.jugmz.meetup;

import de.jugmz.meetup.fallback.MeetupPastFallbackHandler;
import de.jugmz.meetup.fallback.MeetupUpcomingFallbackHandler;
import org.eclipse.microprofile.faulttolerance.Fallback;
import org.eclipse.microprofile.faulttolerance.Retry;
import org.eclipse.microprofile.faulttolerance.Timeout;
import org.eclipse.microprofile.metrics.annotation.Counted;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import java.net.SocketException;
import java.util.List;
import java.util.stream.Collectors;

@Path("/api/meetup")
@Produces(MediaType.APPLICATION_JSON)
@ApplicationScoped
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
    @Counted(name = "serviceCallsUpcoming")
    @Timeout(value = 1000L)
    @Retry(abortOn = SocketException.class)
    @Fallback(value = MeetupUpcomingFallbackHandler.class)
    public List<EventDto> getUpcoming() {
        return meetupService.getUpcoming()
                .stream()
                .map(meetupMapper::toDto)
                .collect(Collectors.toList());
    }

    @GET
    @Path("/past")
    @Counted(name = "serviceCallsPast")
    @Timeout(value = 1000L)
    @Retry(abortOn = SocketException.class)
    @Fallback(value = MeetupPastFallbackHandler.class)
    public List<EventDto> getPast() {
        return meetupService.getPast()
                .stream()
                .map(meetupMapper::toDto)
                .collect(Collectors.toList());
    }
}
