package de.jugmz.mobilizon.model;

import java.util.List;

public record OrgainizedEvent(Integer total,
                              List<Event> elements) {
}
