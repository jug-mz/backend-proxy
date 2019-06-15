package de.jugmz.meetup;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * Event class meant for the Frontend
 */
public class EventDto implements Serializable {

    public EventDto() {

    }

    public EventDto(MeetupEvent event) {
        this.id = event.getId();
        this.name = event.getName();
        this.rsvpLimit = event.getRsvpLimit();
        this.openRsvp = event.getRsvpLimit() - event.getRsvpCount();
        this.status = event.getStatus();
        this.eventDate = LocalDateTime.of(event.getLocalDate(), event.getLocalTime());
        this.venue = event.getVenue().getName();
        this.link = event.getLink();
        this.details = event.getDescription();
    }

    private long id;

    private String name;

    private int rsvpLimit;

    private int openRsvp;

    private String status;

    private LocalDateTime eventDate;

    private String venue;

    private String link;

    private String details;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getRsvpLimit() {
        return rsvpLimit;
    }

    public void setRsvpLimit(int rsvpLimit) {
        this.rsvpLimit = rsvpLimit;
    }

    public int getOpenRsvp() {
        return openRsvp;
    }

    public void setOpenRsvp(int openRsvp) {
        this.openRsvp = openRsvp;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public LocalDateTime getEventDate() {
        return eventDate;
    }

    public void setEventDate(LocalDateTime eventDate) {
        this.eventDate = eventDate;
    }

    public String getVenue() {
        return venue;
    }

    public void setVenue(String venue) {
        this.venue = venue;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public String getDetails() {
        return details;
    }

    public void setDetails(String details) {
        this.details = details;
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
