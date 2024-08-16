package de.jugmz.meetup;

/**
 * Event class meant for the Frontend
 */
public record EventDto(String id,
                       String name,
                       Integer rsvpLimit,
                       Integer openRsvp,
                       String status,
                       String eventDate,
                       String venue,
                       String link,
                       String iCalLink,
                       String details,
                       String eventGroupName,
                       boolean isPartnerEvent) {
}
