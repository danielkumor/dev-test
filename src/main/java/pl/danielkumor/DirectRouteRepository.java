package pl.danielkumor;

import com.google.common.annotations.VisibleForTesting;
import com.google.common.base.Function;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.ImmutableSet;
import com.google.common.collect.Maps;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.locks.ReentrantReadWriteLock;

import static java.util.Collections.EMPTY_SET;

public class DirectRouteRepository {

    private Map<Integer, Set<Integer>> index = new HashMap<>();

    private final ReentrantReadWriteLock lock = new ReentrantReadWriteLock();

    public void addPath(int departureSID, int arrivalSID) {
        lock.writeLock().lock();
        try {
            Set<Integer> set = index.getOrDefault(departureSID, new HashSet<>());
            set.add(arrivalSID);
            index.putIfAbsent(departureSID, set);
        } finally {
            lock.writeLock().unlock();
        }
    }

    public boolean checkIfDirectionExists(int departureSID, int arrivalSID) {
        lock.readLock().lock();
        try {
            return index.getOrDefault(departureSID, EMPTY_SET).contains(arrivalSID);
        } finally {
            lock.readLock().lock();
        }
    }

    @VisibleForTesting
    Map<Integer, Set<Integer>> getIndex() {
        lock.readLock().lock();
        try {

            return ImmutableMap.copyOf(
                    Maps.transformValues(index, new Function<Set<Integer>, Set<Integer>>() {
                        @Override
                        public Set<Integer> apply(Set<Integer> input) {
                            return ImmutableSet.copyOf(input);
                        }
                    })
            );
        } finally {
            lock.readLock().lock();
        }

    }

    @Override
    public String toString() {
        return index.toString();
    }
}
