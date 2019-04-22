package de.jugmz.meetup;

import javax.json.bind.annotation.JsonbProperty;
import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalTime;

public class MeetupEvent implements Serializable {

    private long id;

    private String name;

    @JsonbProperty("rsvp_limit")
    private int rsvpLimit;

    @JsonbProperty("yes_rsvp_count")
    private int rsvpCount;

    private String status;

    @JsonbProperty("local_date")
    private LocalDate localDate;

    @JsonbProperty("local_time")
    private LocalTime localTime;

    @JsonbProperty("venue")
    private MeetupEventVenue venue;

    private String description;

    private String link;

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

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public LocalDate getLocalDate() {
        return localDate;
    }

    public void setLocalDate(LocalDate localDate) {
        this.localDate = localDate;
    }

    public LocalTime getLocalTime() {
        return localTime;
    }

    public void setLocalTime(LocalTime localTime) {
        this.localTime = localTime;
    }

    public int getRsvpCount() {
        return rsvpCount;
    }

    public void setRsvpCount(int rsvpCount) {
        this.rsvpCount = rsvpCount;
    }

    public MeetupEventVenue getVenue() {
        return venue;
    }

    public void setVenue(MeetupEventVenue venue) {
        this.venue = venue;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
