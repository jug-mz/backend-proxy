package de.jugmz.mobilizon.model;

import java.time.LocalDateTime;
import java.time.ZonedDateTime;

public record Event(String id,
                    String title,
                    String slug,
                    String description,
                    ZonedDateTime beginsOn,
                    Address physicalAddress,
                    String url) {
}
