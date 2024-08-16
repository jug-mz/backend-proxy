package de.jugmz.meetup;

import de.jugmz.mobilizon.fallback.MobilizonPastFallbackHandler;
import de.jugmz.mobilizon.fallback.MobilizonUpcomingFallbackHandler;
import de.jugmz.mobilizon.MobilizonMapper;
import de.jugmz.mobilizon.MobilizonService;
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

@Path("/api/meetup")
@Produces(MediaType.APPLICATION_JSON)
@ApplicationScoped
public class MeetupController {

    private final MobilizonService mobilizonService;
    private final MobilizonMapper mobilizonMapper;

    @Inject
    public MeetupController(MobilizonService mobilizonService, MobilizonMapper mobilizonMapper) {
        this.mobilizonService = mobilizonService;
        this.mobilizonMapper = mobilizonMapper;
    }

    @GET
    @Path("/upcoming")
    @Counted(name = "serviceCallsUpcoming")
    @Timeout(value = 1000L)
    @Retry(abortOn = SocketException.class)
    @Fallback(value = MobilizonUpcomingFallbackHandler.class)
    public List<EventDto> getUpcoming() {

        return mobilizonService.getUpcoming()
                .stream()
                .map(event -> mobilizonMapper.toDto(event, "JUG Mainz")) // FIXME
                .toList();
    }

    @GET
    @Path("/past")
    @Counted(name = "serviceCallsPast")
    @Timeout(value = 1000L)
    @Retry(abortOn = SocketException.class)
    @Fallback(value = MobilizonPastFallbackHandler.class)
    public List<EventDto> getPast() {
        return mobilizonService.getPast()
                .stream()
                .map(event -> mobilizonMapper.toDto(event, "JUG Mainz")) // FIXME
                .toList();
    }
}
