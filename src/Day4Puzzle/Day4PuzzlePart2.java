package Day4Puzzle;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashSet;

public class Day4PuzzlePart2 {
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
        String dataFilePath = "src/Day4Puzzle/Day4PuzzleData.txt";
        BufferedReader dataFileReader = getDataFileReader(dataFilePath);

        //Get the total line count of the datafile which == total Elf-pairs
        int totalElfPairs = getDataFileLineCount(dataFilePath);

        // Initialize variables for loop and tracking of count
        String line = null;
        int noOverlapCounter = 0;
//      int cycleCounter = 0; // <--- this was used for debugging

        while ((line = readDataLine(dataFileReader)) != null /*&& cycleCounter < lineCount*/){
            // Separate each line in the datafile into two elfAssignmentCode strings
            String[] elfPairAssignmentCodes = line.split(",");
            String elf1Unprocessed = elfPairAssignmentCodes[0];
            String elf2Unprocessed = elfPairAssignmentCodes[1];

            // Turn each unprocessed elf String into a range
            String[] elf1BeginEnd = elf1Unprocessed.split("-");
            Integer elf1Begin = Integer.parseInt(elf1BeginEnd[0]);
            Integer elf1End = Integer.parseInt(elf1BeginEnd[1]);

            String[] elf2BeginEnd = elf2Unprocessed.split("-");
            Integer elf2Begin = Integer.parseInt(elf2BeginEnd[0]);
            Integer elf2End = Integer.parseInt(elf2BeginEnd[1]);

            // Populate a HashSet of assignments for each elf (begin to end)
            HashSet<Integer> elf1Assignments = new HashSet<>();
            for (int assignment = elf1Begin; assignment <= elf1End; assignment++) {
                elf1Assignments.add(assignment);
            }
            HashSet<Integer> elf2Assignments = new HashSet<>();
            for (int assignment = elf2Begin; assignment <= elf2End; assignment++) {
                elf2Assignments.add(assignment);
            }

            // Simply check if assignmentRange1 has anything in common with assignmentRange2
            elf1Assignments.retainAll(elf2Assignments);
            if (elf1Assignments.size() == 0) noOverlapCounter++;
        }
        //Compute total overlaps
        int totalOverlaps = totalElfPairs - noOverlapCounter;
        System.out.println(totalOverlaps);
    }
}
