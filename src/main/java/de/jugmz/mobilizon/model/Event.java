package de.jugmz.mobilizon.model;

import java.time.LocalDateTime;

public record Event(String id,
                    String title,
                    String slug,
                    String description,
                    LocalDateTime beginsOn,
                    Address physicalAddress,
                    String url) {
}
