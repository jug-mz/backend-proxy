package de.jugmz.meetup.api;

import jakarta.json.bind.annotation.JsonbProperty;
import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Optional;

public class MeetupEvent implements Serializable {

    private long id;

    private String name;

    @JsonbProperty("rsvp_limit")
    private Integer rsvpLimit;

    @JsonbProperty("yes_rsvp_count")
    private Integer rsvpCount;

    private String status;

    @JsonbProperty("local_date")
    private LocalDate localDate;

    @JsonbProperty("local_time")
    private LocalTime localTime;

    @JsonbProperty("venue")
    private MeetupEventVenue venue;

    @JsonbProperty("group")
    private MeetupGroup group;

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

    public Optional<Integer> getRsvpLimit() {
        return Optional.ofNullable(rsvpLimit);
    }

    public void setRsvpLimit(Integer rsvpLimit) {
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

    public Integer getRsvpCount() {
        return rsvpCount;
    }

    public void setRsvpCount(Integer rsvpCount) {
        this.rsvpCount = rsvpCount;
    }

    public Optional<MeetupEventVenue> getVenue() {
        return Optional.ofNullable(venue);
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

    public MeetupGroup getGroup() {
        return group;
    }

    public void setGroup(MeetupGroup group) {
        this.group = group;
    }
}
