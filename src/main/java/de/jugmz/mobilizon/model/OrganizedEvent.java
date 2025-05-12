package de.jugmz.mobilizon.model;

import java.util.List;

public record OrganizedEvent(Integer total,
                             List<Event> elements) {
}
