package Day1Puzzle;

import java.io.*;
import java.util.*;

public class Day1PuzzlePart1 {
    public static void main(String[] args) {
        File day1PuzzleData = new File("src/Day1Puzzle/Day1PuzzleData.txt");

        try {
            // One way to get all the data processed
//            List<String> data = Files.readAllLines(day1PuzzleData.toPath());
//
//
//            int elfSum = 0;
//            ArrayList<Integer> listOfSums = new ArrayList<>();
//            for (String line : data) {
//                if (line.length() != 0) {
//                    int item = Integer.parseInt(line);
//                    elfSum += item;
//                }
//                if (line.length() == 0) {
//                    listOfSums.add(elfSum);
//                    elfSum = 0;
//                }
//            }
//            Collections.sort(listOfSums);
//            System.out.println(listOfSums.get(listOfSums.size()-1));


            //////////////////////////////////////////////////////////////////
            /////////////////////////////////////////////////////////////////
            // A second way to get all the data processed

            BufferedReader reader = new BufferedReader(new FileReader(day1PuzzleData));

            int elfSum = 0;
            HashSet<Integer> sortedElfSums = new HashSet<>();
            String line;
            while ((line = reader.readLine()) != null) {
                if (line.length() != 0) {
                    int item = Integer.parseInt(line);
                    elfSum += item;
                }
                if (line.length() == 0) {
                    sortedElfSums.add(elfSum);
                    elfSum = 0;
                }
            }

            System.out.println(Collections.max(sortedElfSums));

            // PART 2 //
            // PART 2 //
            int topThreeElfSum = 0;
            topThreeElfSum += Collections.max(sortedElfSums);
            sortedElfSums.remove(Collections.max(sortedElfSums));

            topThreeElfSum += Collections.max(sortedElfSums);
            sortedElfSums.remove(Collections.max(sortedElfSums));

            topThreeElfSum += Collections.max(sortedElfSums);
            sortedElfSums.remove(Collections.max(sortedElfSums));

            System.out.println(topThreeElfSum);
            // PART 2 //
            // PART 2 //

            /////////////////////////////////////
            ///////////////////////////////////

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}