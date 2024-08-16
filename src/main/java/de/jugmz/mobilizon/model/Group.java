package de.jugmz.mobilizon.model;

import java.util.List;

public record Group(String name,
                    List<OrgainizedEvent> organizedEvents) {
}
