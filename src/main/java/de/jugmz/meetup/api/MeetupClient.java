package de.jugmz.meetup.api;

import org.eclipse.microprofile.metrics.annotation.Counted;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import java.util.List;

@Path("/")
public interface MeetupClient {

    @GET
    @Path("/{meetupId}/events")
    @Produces(MediaType.APPLICATION_JSON)
    @Counted(name = "meetupCallsEvents")
    List<MeetupEvent> getEvents(@PathParam("meetupId") String meetupId, @QueryParam("status") String status, @QueryParam("desc") boolean desc);

}
