package de.jugmz.meetup;

import java.io.Serializable;

public class MeetupEventVenue implements Serializable {

    private String name;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}