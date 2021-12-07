package task2;

import java.util.List;
import java.util.Map;
import java.util.Set;

public class ContextSearch {

    private StrategySearch searchingMethod;

    ContextSearch(StrategySearch searchingMethod) {
        this.searchingMethod = searchingMethod;
    }

    ContextSearch() { }

    public void setSearchingMethod(StrategySearch searchingMethod) {
        this.searchingMethod = searchingMethod;
    }

    public Set<Integer> search(Map<String, Set<Integer>> invertedIndex, List<String> dataFromFile, String query) {
        return this.searchingMethod.search(invertedIndex, dataFromFile, query);
    }
}
