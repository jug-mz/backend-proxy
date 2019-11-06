package de.jugmz;

import java.time.LocalDateTime;
import java.util.List;
import java.util.function.Supplier;

/**
 * The simplest possible cache mechanism. Works as a cache wrapper around the provided supplier.
 *
 * @param <T> The value to be cached
 */
public class SimplestCache<T extends List> {

    private final int cacheDuration;
    private final Supplier<T> supplier;

    private T content;
    private LocalDateTime lastUpdate;

    /**
     * Sets the cache duration length in seconds and the supplier which is responsible for retrieving the values, if the cache is invalid
     *
     * @param durationInSeconds The caching time in seconds
     * @param supplier          The supplier which fetches the cached value
     */
    public SimplestCache(int durationInSeconds, Supplier<T> supplier) {
        this.cacheDuration = durationInSeconds;
        this.supplier = supplier;
        this.lastUpdate = LocalDateTime.of(0, 1, 1, 0, 0, 0);
    }

    /**
     * Returns the cached value. If the cache is invalid (because it is too old), new values are retrieved automatically
     *
     * @return The content of the cache, which might be updated upon calling this method
     */
    public synchronized T loadOrGet() {
        if (isInvalid()) {
            content = this.supplier.get();
            lastUpdate = LocalDateTime.now();
        }
        return content;
    }

    private boolean isInvalid() {
        return lastUpdate.plusSeconds(cacheDuration).isBefore(LocalDateTime.now());
    }
}
