package Day5Puzzle;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.LinkedList;

public class Day5PuzzlePart2 {
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
        String dataFilePath = "src/Day5Puzzle/Day5PuzzleData.txt";
        BufferedReader dataFileReader = getDataFileReader(dataFilePath);

        // Initialize and populate all the crane slots
        LinkedList<String> craneSlot1 = new LinkedList<>(Arrays.asList("F", "H", "M", "T", "V", "L", "D"));
        LinkedList<String> craneSlot2 = new LinkedList<>(Arrays.asList("P", "N", "T", "C", "J", "G", "Q", "H"));
        LinkedList<String> craneSlot3 = new LinkedList<>(Arrays.asList("H", "P", "M", "D", "S", "R"));
        LinkedList<String> craneSlot4 = new LinkedList<>(Arrays.asList("F", "V", "B", "L"));
        LinkedList<String> craneSlot5 = new LinkedList<>(Arrays.asList("Q", "L", "G", "H", "N"));
        LinkedList<String> craneSlot6 = new LinkedList<>(Arrays.asList("P", "M", "R", "G", "D", "B", "W"));
        LinkedList<String> craneSlot7 = new LinkedList<>(Arrays.asList("Q", "L", "H", "C", "R", "N", "M", "G"));
        LinkedList<String> craneSlot8 = new LinkedList<>(Arrays.asList("W", "L", "C"));
        LinkedList<String> craneSlot9 = new LinkedList<>(Arrays.asList("T", "M", "Z", "J", "Q", "L", "D", "R"));

        // Initialize HashMap for getting craneSlot pointers
        HashMap<String, LinkedList<String>> numberToLLPointer = new HashMap<>();
        numberToLLPointer.put("1", craneSlot1);
        numberToLLPointer.put("2", craneSlot2);
        numberToLLPointer.put("3", craneSlot3);
        numberToLLPointer.put("4", craneSlot4);
        numberToLLPointer.put("5", craneSlot5);
        numberToLLPointer.put("6", craneSlot6);
        numberToLLPointer.put("7", craneSlot7);
        numberToLLPointer.put("8", craneSlot8);
        numberToLLPointer.put("9", craneSlot9);

//      Loop through datafile
        String line;
//        int cycleCounter = 0;
        while ((line = readDataLine(dataFileReader)) != null /*&& cycleCounter < 3*/){
            // Create from each line a String[] of commands that can be interpreted: commandScript
            String[] commandScript = line.split("\\s+");
//            System.out.println(Arrays.toString(commandScript));


            // Interpret commandScript
            String amountToBeMoved = commandScript[1];
            String moveFromCraneSlot = commandScript[3];
            String moveToCraneSlot = commandScript[5];
            LinkedList<String> fromSlot = numberToLLPointer.get(moveFromCraneSlot);
            LinkedList<String> toSlot = numberToLLPointer.get(moveToCraneSlot);
            int executionCycles = Integer.parseInt(amountToBeMoved);

            // Execute commands
            // Pull boxes from fromSlot into temporary (this reverses order)
            LinkedList<String> temporary = new LinkedList<>();
            for (int i = 1; i <= executionCycles; i++) {
                String topBox = fromSlot.pollFirst();
                temporary.addFirst(topBox);
            }
            // Put all boxes from temporary into to toSlot (this unreverses order)
            for (int i = 1; i <= executionCycles; i++) {
                String topBox = temporary.pollFirst();
                toSlot.addFirst(topBox);
            }
//            cycleCounter++;
        }

        String finalOutput = "";
        finalOutput += numberToLLPointer.get("1").getFirst();
        finalOutput += numberToLLPointer.get("2").getFirst();
        finalOutput += numberToLLPointer.get("3").getFirst();
        finalOutput += numberToLLPointer.get("4").getFirst();
        finalOutput += numberToLLPointer.get("5").getFirst();
        finalOutput += numberToLLPointer.get("6").getFirst();
        finalOutput += numberToLLPointer.get("7").getFirst();
        finalOutput += numberToLLPointer.get("8").getFirst();
        finalOutput += numberToLLPointer.get("9").getFirst();
        System.out.println(finalOutput);


    }
}
