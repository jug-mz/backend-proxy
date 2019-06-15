package de.jugmz.general.comparators;

import java.util.Comparator;

/**
 * A comparator which does not change the order of the elements at all.
 *
 * @param <T>
 */
public final class NoOpComparator<T> implements Comparator<T> {
    @Override
    public int compare(T t1, T t2) {
        return 0;
    }

}