package de.jugmz.general.comparators;

import java.util.Comparator;
import java.util.IdentityHashMap;
import java.util.Map;
import java.util.Random;

/**
 * This comparator randomizes the order of all elements in a stream.
 *
 * @param <T>
 */
public final class RandomComparator<T> implements Comparator<T> {

    private final Map<T, Integer> map = new IdentityHashMap<>();
    private final Random random;

    public RandomComparator() {
        this.random = new Random();
    }

    @Override
    public int compare(T t1, T t2) {
        return Integer.compare(valueFor(t1), valueFor(t2));
    }

    private int valueFor(T t) {
        synchronized (map) {
            return map.computeIfAbsent(t, ignore -> random.nextInt());
        }
    }

}