package de.jugmz.mobilizon;

import de.jugmz.meetup.EventDto;
import de.jugmz.mobilizon.model.Event;
import jakarta.enterprise.context.ApplicationScoped;

import java.time.ZoneId;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;

/**
 * Mapper for transforming Meetup API objects into DTO for our web application.
 */
@ApplicationScoped
public class MobilizonMapper {

    public EventDto toDto(Event apiEvent, String groupName) {

        String id = apiEvent.id();
        String name = apiEvent.title();
        String status = ""; //TBD
        String eventDate = apiEvent.beginsOn()
                .withZoneSameInstant(ZoneId.of("Europe/Berlin"))
                .format(DateTimeFormatter.ofLocalizedDateTime(FormatStyle.MEDIUM));
        String venue;
        if(null != apiEvent.physicalAddress()) {
            venue = apiEvent.physicalAddress().description();
        } else {
            venue = "TBD";
        }
        String link = apiEvent.url();
        String iCalLink = apiEvent.url() + "/export/ics";
        String details = apiEvent.description();
        String eventGroupName = groupName;
        boolean isPartnerEvent = false;

        return new EventDto(id, name, 0, 0, status, eventDate, venue, link, iCalLink, details, eventGroupName, isPartnerEvent);
    }

}
