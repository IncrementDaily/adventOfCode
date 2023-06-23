package Day3Puzzle;

import java.io.*;
import java.util.HashMap;
import java.util.HashSet;

public class Day3PuzzlePart1 {
    private static HashMap<String, Integer> itemToPriority = new HashMap<>();

    // Populate HashMap for matching item to priority value
    // Unicode Guide: A = 65 ; Z = 90 ; a = 97 ; z = 122
    private static void initItemToPriority(){
        for (int i = 27; i <= 52; i++) {
            char item = (char) (i+38);
            itemToPriority.put(Character.toString(item),i);
        }
        for (int i = 1; i <= 26; i++) {
            char item = (char) (i+96);
            itemToPriority.put(Character.toString(item),i);
        }
    }

    public static String findSharedItem(String rucksack) {
        int midpoint = rucksack.length() / 2;
        String firstCompartment = rucksack.substring(0, midpoint);
        String secondCompartment = rucksack.substring(midpoint, rucksack.length());
        char[] firstCompItemSet = firstCompartment.toCharArray();
        char[] secondCompItemSet = secondCompartment.toCharArray();

        Character sharedItem = null;
        HashSet<Character> itemInventory = new HashSet<>();
        for (Character item : firstCompItemSet) {
            itemInventory.add(item);
        }
        for (Character item : secondCompItemSet){
            if (itemInventory.contains(item)){
                sharedItem = item;
            }
        }
        return sharedItem.toString();
    }

    public static void main(String[] args) {
        // Get data input
        BufferedReader reader = null;
        try {
            File day3PuzzleData = new File("src/Day3Puzzle/Day3PuzzleData.txt");
            reader = new BufferedReader(new FileReader(day3PuzzleData));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        // Initialize HashMap for item to priority scoring
        initItemToPriority();

        String line = null;
        int totalPriority = 0;
        try {
            while ((line = reader.readLine()) != null) {
                totalPriority += itemToPriority.get(findSharedItem(line));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        System.out.println(totalPriority);
        try {
            reader.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
