package task2;

import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * It is implementation of the interface with the implemented searching method
 * */
public class StrategySearchNone implements StrategySearch{

    @Override
    public Set<Integer> search(Map<String, Set<Integer>> invertedIndex, List<String> dataFromFile, String query) {
        Set<Integer> searchResult = new HashSet<>();
        ContextSearch searchMethodAny = new ContextSearch(new StrategySearchAny());
        Set<Integer> anyResult = searchMethodAny.search(invertedIndex, dataFromFile, query);

        for (int i = 0; i < dataFromFile.size(); i++) {
            if (!anyResult.contains(i)) {
                searchResult.add(i);
            }
        }
        return searchResult;
    }
}
