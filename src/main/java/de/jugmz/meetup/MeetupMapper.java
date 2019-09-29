package de.jugmz.meetup;

import de.jugmz.meetup.api.MeetupEvent;
import org.eclipse.microprofile.config.inject.ConfigProperty;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import java.time.format.DateTimeFormatter;

/**
 * Mapper for transforming Meetup API objects into DTO for our web application.
 */
@ApplicationScoped
public class MeetupMapper {

    private final static DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ofPattern("dd.MM.yyyy");
    private final static DateTimeFormatter TIME_FORMATTER = DateTimeFormatter.ofPattern("HH:mm");

    private String homeId;

    public MeetupMapper() {}

    @Inject
    public MeetupMapper(@ConfigProperty(name = "service.meetup.homeId") String homeId) {
        this.homeId = homeId;
    }

    public EventDto toDto(MeetupEvent apiEvent) {

        EventDto dtoEvent = new EventDto();

        dtoEvent.id = apiEvent.getId();
        dtoEvent.name = apiEvent.getName();
        dtoEvent.rsvpLimit = apiEvent.getRsvpLimit();
        dtoEvent.openRsvp = apiEvent.getRsvpLimit() - apiEvent.getRsvpCount();
        dtoEvent.status = apiEvent.getStatus();
        dtoEvent.eventDate = formatEventDate(apiEvent);
        dtoEvent.venue = apiEvent.getVenue().getName();
        dtoEvent.link = apiEvent.getLink();
        dtoEvent.details = apiEvent.getDescription();
        dtoEvent.eventGroupName = apiEvent.getGroup().getName();
        dtoEvent.isPartnerEvent = isPartnerEvent(apiEvent);

        return dtoEvent;
    }


    private String formatEventDate(MeetupEvent event) {
        String dateString = event.getLocalDate().format(DATE_FORMATTER);
        String timeString = event.getLocalTime().format(TIME_FORMATTER);
        return String.format("%s, %s Uhr", dateString, timeString);
    }

    private boolean isPartnerEvent(MeetupEvent event) {
        return !event.getGroup().getUrlname().equals(this.homeId);
    }

}
