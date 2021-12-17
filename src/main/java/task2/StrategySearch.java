package task2;

import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * It is interface searching data in invertedIndex
 * */
public interface StrategySearch {

    Set<Integer> search(Map<String, Set<Integer>> invertedIndex, List<String> dataFromFile, String query);
}