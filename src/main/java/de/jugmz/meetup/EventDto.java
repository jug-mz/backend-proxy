package de.jugmz.meetup;

import java.io.Serializable;
import java.time.format.DateTimeFormatter;

/**
 * Event class meant for the Frontend
 */
public class EventDto implements Serializable {

    private final static DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("dd.MM.yyyy");
    private final static DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("HH:mm");


    public EventDto() {

    }

    public EventDto(MeetupEvent event) {
        this.id = event.getId();
        this.name = event.getName();
        this.rsvpLimit = event.getRsvpLimit();
        this.openRsvp = event.getRsvpLimit() - event.getRsvpCount();
        this.status = event.getStatus();
        this.eventDate = formatEventDate(event);
        this.venue = event.getVenue().getName();
        this.link = event.getLink();
        this.details = event.getDescription();
    }

    private long id;

    private String name;

    private int rsvpLimit;

    private int openRsvp;

    private String status;

    private String eventDate;

    private String venue;

    private String link;

    private String details;

    public long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public int getRsvpLimit() {
        return rsvpLimit;
    }

    public int getOpenRsvp() {
        return openRsvp;
    }

    public String getStatus() {
        return status;
    }

    public String getEventDate() {
        return eventDate;
    }

    public String getVenue() {
        return venue;
    }

    public String getLink() {
        return link;
    }

    public String getDetails() {
        return details;
    }

    private String formatEventDate(MeetupEvent event) {
        String dateString = event.getLocalDate().format(dateFormatter);
        String timeString = event.getLocalTime().format(timeFormatter);
        return String.format("%s, %s Uhr", dateString, timeString);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        EventDto eventDto = (EventDto) o;

        if (name != null ? !name.equals(eventDto.name) : eventDto.name != null) return false;
        return eventDate != null ? eventDate.equals(eventDto.eventDate) : eventDto.eventDate == null;

    }

    @Override
    public int hashCode() {
        int result = name != null ? name.hashCode() : 0;
        result = 31 * result + (eventDate != null ? eventDate.hashCode() : 0);
        return result;
    }

}
