package de.jugmz.mobilizon.model;

public record Address(String description,
                      String street,
                      String postalCode,
                      String locality) {
}
