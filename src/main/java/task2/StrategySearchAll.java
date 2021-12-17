package task2;

import java.util.*;
import java.util.stream.Collectors;

/**
 * It is implementation of the interface with the implemented searching method
 */
public class StrategySearchAll implements StrategySearch {

    @Override
    public Set<Integer> search(Map<String, Set<Integer>> invertedIndex, List<String> dataFromFile, String query) {
        Set<String> querySet = Arrays.stream(query.trim().split(" ")).collect(Collectors.toSet());

        Set<Integer> searchResult = new HashSet<>();

        for (int i = 0; i < dataFromFile.size(); i++) {
            Set<String> tempLine = Arrays
                    .stream(dataFromFile.get(i)
                            .trim()
                            .split(" "))
                    .collect(Collectors.toSet());
            if (tempLine.containsAll(querySet)) {
                searchResult.add(i);
            }
        }
        return searchResult;
    }
}