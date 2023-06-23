package Day6Puzzle;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashSet;

public class Day6PuzzlePart2 {
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
        char[] testArray = {'a','b','c','d'};

        // PART 1
        // Loop through input looking for the first
        // set of 4 contiguous distinct characters (Packet Marker)
        HashSet<Character> uniqueSetChecker = new HashSet<>();
        int packetMarker = 0;
        int lastIndexToCheck4 = testArray.length-3;
        try {
            for (int i = 0; i < lastIndexToCheck4; i++) {
                // Starting at [i], add [i] and the next 3 chars
                uniqueSetChecker.add(testArray[i]);
                uniqueSetChecker.add(testArray[i+1]);
                uniqueSetChecker.add(testArray[i+2]);
                uniqueSetChecker.add(testArray[i+3]);
                // If no duplicates were found this iteration, size will be 4
                if (uniqueSetChecker.size() == 4) {
                    // packetMarker is the last character
                    // of the distinct set of 4
                    packetMarker = (i+3)+1;
                    break;
                }
                // Clear set so next iteration will start fresh
                uniqueSetChecker.clear();
            }
        } catch (IndexOutOfBoundsException e) {
            System.out.println("Datastream is limited: No longer checking for Packet Marker or Message Marker");
        }
        System.out.println("packetMarker = " + packetMarker);

        // PART 2
        // Loop through input looking for the first
        // set of 14 contiguous distinct characters (Message Marker)
        // NOTE: The first 4 characters of the Message Marker may be the
        // same 4 characters that constitute the Packet Marker.
        int messageMarker = 0;
        int beginningOfPacketMarker = packetMarker-3;
        int lastIndexToCheck14 = testArray.length-13;
        try {
            for (int i = beginningOfPacketMarker; i < lastIndexToCheck14; i++) {
                // Starting at [i], add [i] and the next 13 chars
                uniqueSetChecker.add(testArray[i]);
                uniqueSetChecker.add(testArray[i+1]);
                uniqueSetChecker.add(testArray[i+2]);
                uniqueSetChecker.add(testArray[i+3]);
                uniqueSetChecker.add(testArray[i+4]);
                uniqueSetChecker.add(testArray[i+5]);
                uniqueSetChecker.add(testArray[i+6]);
                uniqueSetChecker.add(testArray[i+7]);
                uniqueSetChecker.add(testArray[i+8]);
                uniqueSetChecker.add(testArray[i+9]);
                uniqueSetChecker.add(testArray[i+10]);
                uniqueSetChecker.add(testArray[i+11]);
                uniqueSetChecker.add(testArray[i+12]);
                uniqueSetChecker.add(testArray[i+13]);
                // If no duplicates were found this iteration, size will be 14
                if (uniqueSetChecker.size() == 14) {
                    messageMarker = (i+13)+1;
                    break;
                }
                // Clear set so next iteration will start fresh
                uniqueSetChecker.clear();
            }
        } catch (IndexOutOfBoundsException e) {
            System.out.println("Datastream is limited: No longer checking for Packet Marker or Message Marker");
        }
        System.out.println("messageMarker = " + messageMarker);
    }
}
