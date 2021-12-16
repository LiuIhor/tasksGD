package task2;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;

import static task2.Controller.*;

public class Main {

    public static void main(String[] args) {
        List<String> dataFromFile = new ArrayList<>();
        Map<String, Set<Integer>> invertedIndex;
        if(args[0].equals("--data")) {
            File file = new File(args[1]);

            try (Scanner scanner = new Scanner(file)) {
                while (scanner.hasNext()) {
                    dataFromFile.add(scanner.nextLine()); // fill the list with data lines from file
                }
                invertedIndex = loadInvertedIndex(dataFromFile); // get the Map<String, Set<Integer>>
                System.out.println(invertedIndex);
                menu(invertedIndex, dataFromFile); //main logic program with user`s menu
            } catch (FileNotFoundException e) {
                System.out.println("Fail");
            }
        }
    }
}
