package de.jugmz.meetup.api;

import org.eclipse.microprofile.metrics.annotation.Counted;

import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.QueryParam;
import jakarta.ws.rs.core.MediaType;
import java.util.List;

@Path("/")
public interface MeetupClient {

    @GET
    @Path("/{meetupId}/events")
    @Produces(MediaType.APPLICATION_JSON)
    @Counted(name = "meetupCallsEvents")
    List<MeetupEvent> getEvents(@PathParam("meetupId") String meetupId, @QueryParam("status") String status, @QueryParam("desc") boolean desc);

}
