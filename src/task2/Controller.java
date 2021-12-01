package task2;

import java.util.*;

/**
 * Class with program`s main logic
 * */
public class Controller {

    public static void menu(Map<String, Set<Integer>> invertedIndex, List<String> dataFromFile) {

        Scanner sc = new Scanner(System.in);
        boolean flag = true;

        while (flag) {
            System.out.println("\n=== Menu ===");
            System.out.println("1. Find a person\n" +
                    "2. Print all people\n" +
                    "0. Exit");

            int action = Integer.parseInt(sc.nextLine());

            switch (action) {
                case 1:
                    Set<Integer> searchResult = strategySearch(invertedIndex, dataFromFile);
                    printResult(searchResult, dataFromFile);
                    break;
                case 2:
                    printAllPeople(dataFromFile);
                    break;
                case 0:
                    flag = false;
                    System.out.println("\nBye!");
                    break;
                default:
                    System.out.println("\nIncorrect option! Try again.");
                    break;
            }
        }
        sc.close();
    }

    private static Set<Integer>  strategySearch(Map<String, Set<Integer>> invertedIndex, List<String> dataFromFile) {
        Scanner sc = new Scanner(System.in);
        System.out.println("Select a matching strategy: ALL, ANY, NONE");
        String searchingMethod = sc.nextLine();
        System.out.println("Enter a name or email to search all suitable people.");
        String query = sc.nextLine();
        ContextSearch contextSearch = new ContextSearch();
        switch (searchingMethod) {
            case "ALL":
                contextSearch.setSearchingMethod(new StrategySearchAll());
                break;
            case "ANY":
                contextSearch.setSearchingMethod(new StrategySearchAny());
                break;
            case "NONE":
                contextSearch.setSearchingMethod(new StrategySearchNone());
                break;
            default:
                System.out.println("\nIncorrect strategy! Try again.");
                break;
        }
        sc.close();
        return contextSearch.search(invertedIndex, dataFromFile, query);
    }

    /**
     * Method to display all data from a file
     * */
    private static void printAllPeople(List<String> dataFromFile) {
        for (String person : dataFromFile) {
            System.out.println(person);
        }
    }

    /**
     * Method to display all founded data
     * */
    private static void printResult(Set<Integer> searchResult, List<String> dataFromFile) {
        if (searchResult.size() == 0) {
            System.out.println("No matching people found");
        } else {
            System.out.println(searchResult.size() + " person found");
            for (Integer lineIndex : searchResult) {
                System.out.println(dataFromFile.get(lineIndex));
            }
        }
    }

    /**
     *  Method to transform from data from a file to inverted indexes
     *  */
    public static Map<String, Set<Integer>> loadInvertedIndex(List<String> dataFromFile) {
        Map<String, Set<Integer>> invertedIndex = new HashMap<>();
        Set<Integer> indexes;
        for (int i = 0; i < dataFromFile.size(); i++) {
            for (String word : dataFromFile.get(i).split("\\s+")) {
                indexes = invertedIndex.get(word.toUpperCase());

                if (indexes == null) {
                    indexes = new HashSet<>();
                }
                indexes.add(i);
                invertedIndex.put(word.toUpperCase(),indexes);
            }
        }
        return invertedIndex;
    }
}
