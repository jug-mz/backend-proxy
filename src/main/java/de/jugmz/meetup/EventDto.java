package de.jugmz.meetup;

import java.io.Serializable;

/**
 * Event class meant for the Frontend
 */
public class EventDto implements Serializable {

    long id;

    String name;

    int rsvpLimit;

    int openRsvp;

    String status;

    String eventDate;

    String venue;

    String link;

    String details;

    String eventGroupName;

    boolean isPartnerEvent;

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

    public String getEventGroupName() {
        return eventGroupName;
    }

    public boolean isPartnerEvent() {
        return isPartnerEvent;
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
