package Day7PuzzleINCOMPLETE;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

public class Day7PuzzlePart2 {
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
        String dataFilePath = "src/Day6Puzzle/Day7PuzzleData.txt";
        BufferedReader dataFileReader = getDataFileReader(dataFilePath);


    }
}
