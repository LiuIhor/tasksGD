package task2;

import java.util.*;
import java.util.stream.Collectors;

/**
 * It is implementation of the interface with the implemented searching method
 * */
public class StrategySearchAny implements StrategySearch{

    @Override
    public Set<Integer> search(Map<String, Set<Integer>> invertedIndex, List<String> dataFromFile, String query) {
        Set<String> querySet = Arrays.stream(query.trim().split(" ")).collect(Collectors.toSet());
        Set<Integer> searchResult = new HashSet<>();

        for (String q : querySet) {
            Set<Integer> values = invertedIndex.getOrDefault(q.toUpperCase(Locale.ROOT), new HashSet<>(Set.of()));
            searchResult.addAll(new HashSet<>(values));
        }
        return searchResult;
    }
}