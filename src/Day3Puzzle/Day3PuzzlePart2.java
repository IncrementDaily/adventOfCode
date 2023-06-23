package Day3Puzzle;

import java.io.*;
import java.nio.file.Path;
import java.util.*;

public class Day3PuzzlePart2 {
    private static HashMap<String, Integer> itemToPriority = new HashMap<>();

    // Populate itemToPriority HashMap for matching item to priority value
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

    public static String findBadge(String rucksack1, String rucksack2, String rucksack3) {
        ArrayList<Character> itemSet1 = new ArrayList<>();
        for (char item : rucksack1.toCharArray()){
            itemSet1.add(item);
        }
        ArrayList<Character> itemSet2 = new ArrayList<>();
        for (char item : rucksack2.toCharArray()){
            itemSet2.add(item);
        }
        ArrayList<Character> itemSet3 = new ArrayList<>();
        for (char item : rucksack3.toCharArray()){
            itemSet3.add(item);
        }

        itemSet1.retainAll(itemSet2);
        itemSet1.retainAll(itemSet3);

        if (!itemSet1.isEmpty()) return itemSet1.get(0).toString();
        return null;
    }



    public static List<String> getElfGroup(BufferedReader reader){
        List<String> elfGroup = new ArrayList<>();
        for (int line=0;line<3;line++){
            try {
                elfGroup.add(reader.readLine());
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return elfGroup;
    }

    public static int getDataFileLineCount(String filePath){
        BufferedReader reader = null;
        int lineCount = 0;
        try {
            File file = new File(filePath);
            reader = new BufferedReader(new FileReader(file));
            lineCount = (int) reader.lines().count();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return lineCount;
    }

    public static void main(String[] args) {
        // Prepare data file for reading
        BufferedReader reader = null;
        try {
            File day3PuzzleData = new File("src/Day3Puzzle/Day3PuzzleData.txt");
            reader = new BufferedReader(new FileReader(day3PuzzleData));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        // initialize HashMap for item to priority scoring
        initItemToPriority();

        // Read through all the data to get all the elfGroups
        // and find their badges. Then tally up the total priority values.
        int totalPriority = 0;
        int lineCount = getDataFileLineCount("src/Day3Puzzle/Day3PuzzleData.txt");
        for (int i = 0; i < lineCount/3; i++) {
            List<String> elfGroup = getElfGroup(reader);
            String badge = findBadge(elfGroup.get(0), elfGroup.get(1), elfGroup.get(2));
            totalPriority += itemToPriority.get(badge);
        }

        System.out.println(totalPriority);

        try {
            reader.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
