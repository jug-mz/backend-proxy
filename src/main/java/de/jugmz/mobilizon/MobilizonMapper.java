package de.jugmz.mobilizon;

import de.jugmz.meetup.EventDto;
import de.jugmz.mobilizon.model.Event;
import jakarta.enterprise.context.ApplicationScoped;

/**
 * Mapper for transforming Meetup API objects into DTO for our web application.
 */
@ApplicationScoped
public class MobilizonMapper {

    public EventDto toDto(Event apiEvent, String groupName) {

        String id = apiEvent.id();
        String name = apiEvent.title();
        String status = ""; //TBD
        String eventDate = apiEvent.beginsOn().toString();
        String venue = apiEvent.physicalAddress().description();
        String link = apiEvent.url();
        String iCalLink = null;
        String details = apiEvent.description();
        String eventGroupName = groupName;
        boolean isPartnerEvent = false;

        return new EventDto(id, name, 0, 0, status, eventDate, venue, link, iCalLink, details, eventGroupName, isPartnerEvent);
    }

}
