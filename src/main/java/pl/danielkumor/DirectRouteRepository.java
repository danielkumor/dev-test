package pl.danielkumor;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import static java.util.Collections.EMPTY_SET;


public class DirectRouteRepository {

    Map<Integer, Set<Integer>> index = new HashMap<>();

    public void addPath(int departureSID, int arrivalSID) {
        Set<Integer> set = index.getOrDefault(departureSID, new HashSet<>());
        set.add(arrivalSID);
        index.putIfAbsent(departureSID, set);
    }

    public boolean checkIfDirectionExists(int departureSID, int arrivalSID) {
        return index.getOrDefault(departureSID, EMPTY_SET).contains(arrivalSID);
    }

    @Override
    public String toString() {
        return index.toString();
    }
}
