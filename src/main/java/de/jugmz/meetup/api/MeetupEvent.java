package de.jugmz.meetup.api;

import jakarta.json.bind.annotation.JsonbProperty;
import java.time.LocalDate;
import java.time.LocalTime;

public record MeetupEvent(long id,
                          String name,
                          @JsonbProperty("rsvp_limit") Integer rsvpLimit,
                          @JsonbProperty("yes_rsvp_count") Integer rsvpCount,
                          String status,
                          @JsonbProperty("local_date") LocalDate localDate,
                          @JsonbProperty("local_time")LocalTime localTime,
                          @JsonbProperty("venue") MeetupEventVenue venue,
                          @JsonbProperty("group") MeetupGroup group,
                          String description,
                          String link) {

}
