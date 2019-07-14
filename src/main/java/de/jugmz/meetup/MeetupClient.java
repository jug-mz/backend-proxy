package de.jugmz.meetup;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import java.util.List;

@Path("/JUG-Mainz/events")
public interface MeetupClient {

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    List<MeetupEvent> getEvents(@QueryParam("status") String status, @QueryParam("desc") boolean desc);

    @GET
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    MeetupEvent getEvent(@PathParam("id") String id, @QueryParam("desc") boolean desc);

}
