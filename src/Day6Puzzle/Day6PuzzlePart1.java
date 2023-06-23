package Day6Puzzle;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashSet;

public class Day6PuzzlePart1 {
    public static BufferedReader getDataFileReader(String filepath){
        BufferedReader reader = null;
        try {
            reader = new BufferedReader(new FileReader(filepath));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        return reader;
    }
    public static int getDataFileLineCount(String filePath){
        int lineCount = 0;
        try {
            BufferedReader reader = new BufferedReader(new FileReader(filePath));
            while (reader.readLine() != null){
                lineCount++;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return lineCount;
    }
    public static String readDataLine(BufferedReader reader){
        String dataLine = null;
        try {
            dataLine = reader.readLine();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return dataLine;
    }

    public static void main(String[] args) {
        // Initialize input
        String dataFilePath = "src/Day6Puzzle/Day6PuzzleData.txt";
        BufferedReader dataFileReader = getDataFileReader(dataFilePath);
        String input = readDataLine(dataFileReader);
        char[] inputCharArray = input.toCharArray();
//        char[] testArray = {'a','b','c','a','b','a','c','d','e','a'};

        // PART 1
        // Loop through input looking for the first
        // set of 4 contiguous distinct characters
        HashSet<Character> uniqueSetChecker = new HashSet<>();
        int startOfPacketMarker = 0;

        for (int i = 0; i < inputCharArray.length-3; i++) {
            uniqueSetChecker.add(inputCharArray[i]);
            uniqueSetChecker.add(inputCharArray[i+1]);
            uniqueSetChecker.add(inputCharArray[i+2]);
            uniqueSetChecker.add(inputCharArray[i+3]);
            if (uniqueSetChecker.size() == 4) {
                startOfPacketMarker = (i+3)+1;
                break;
            }
            uniqueSetChecker.clear();
        }
        System.out.println(startOfPacketMarker);
    }
}
