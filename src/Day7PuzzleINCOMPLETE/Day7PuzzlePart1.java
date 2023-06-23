package Day7PuzzleINCOMPLETE;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.UUID;

public class Day7PuzzlePart1 {
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

    public static class Directory{
        private String name;
        private UUID uuid;
        private Directory parent;
        private HashMap<UUID, Directory> uuidToSubDirectory = new HashMap<>();
        private HashMap<UUID, DataFile> uuidToDataFile = new HashMap<>();

        public static class DataFile{
            private String fileName;
            private int size;
            private UUID uuid;

            public DataFile(String fileName, int size) {
                this.fileName = fileName;
                this.size = size;
                this.uuid = UUID.randomUUID();
            }

            public String getFileName() {
                return fileName;
            }

            public int size() {
                return size;
            }

            public UUID getUuid() {
                return uuid;
            }

            public String toString(){
                return fileName.toString();
            }
        }

        Directory(String name, Directory parent){
            this.name = name;
            this.parent = parent;
            this.uuid = UUID.randomUUID();
        }

        public void addSubDirectory(Directory subdirectory){
            uuidToSubDirectory.put(subdirectory.getUUID(),subdirectory);
        }
        public void addDataFile(String fileName, Integer fileSize){
            DataFile dataFile = new DataFile(fileName,fileSize);
            uuidToDataFile.put(dataFile.getUuid(), dataFile);
        }
        public int size() {
            int totalSize = 0;
            // Find total size of each directory and add to total
            for (Directory subdirectory : uuidToSubDirectory.values()){
                totalSize += subdirectory.size();
            }
            // Find size of each file and add to total
            for (DataFile file : uuidToDataFile.values()){
                totalSize += file.size();
            }
            return totalSize;
        }
        public String getName() {
            return name;
        }
        public Directory getParent() {
            return parent;
        }
        public void setParent(Directory parent) {
            this.parent = parent;
        }
        public UUID getUUID() {
            return uuid;
        }
        @Override
        public String toString(){
            return name.toString();
        }
    }

    public static void main(String[] args) {
        // Initialize input
        String dataFilePath = "src/Day7Puzzle/Day7PuzzleData.txt";
        String testDataFilePath = "src/Day7Puzzle/TestData.txt";
        BufferedReader dataFileReader = getDataFileReader(testDataFilePath);

        // Initialize HashMap of Directories for tracking and returning final
        HashMap<UUID, Directory> uuidToDirectoryTracker = new HashMap<>();

        // Initialize decoded signs
        String command = "$";
        String changeDirectory = "cd";
        String list = "ls";
        String directoryMarker = "dir";
        String goUp = "..";

        // Initialize loop pointers for tracking
        String line = "";
        int lineCount = 0;
        Directory currentDirectory = null;
        while ((line = readDataLine(dataFileReader)) != null) {
            // Separate lines into their parts
            String[] lineArray = line.split("\\s+");
//            System.out.println("lineArray = " + Arrays.toString(lineArray));

            // Decode line
            // (1) If line decodes to enter a named directory
            if (lineArray[0].equals(command)
                    && lineArray[1].equals(changeDirectory)
                    && !lineArray[2].equals(goUp)){
                // If directory does not exist, create it in tracking HashMap
                // and track it as currentDirectory
                String directoryName = lineArray[2];
                Directory newDirectory = new Directory(directoryName, currentDirectory);
                if (uuidToDirectoryTracker.putIfAbsent(newDirectory.getUUID(),newDirectory) != null)
                    System.out.println("yikes");
                currentDirectory = uuidToDirectoryTracker.get(newDirectory.getUUID());
                lineCount++;
                System.out.println("line = " + lineCount);
                continue;
            }

            // (2) If line decodes to go up one directory
            if (lineArray[0].equals(command)
                && lineArray[1].equals(changeDirectory)
                && lineArray[2].equals(goUp)) {
                try {
                    currentDirectory = currentDirectory.getParent();
                } catch (Exception e) {
                    System.out.println("currentDirectory was null");
                }
                lineCount++;
                System.out.println("line = " + lineCount);
                    continue;
            }

            // (3) If line decodes to list directory contents
            if (lineArray[0].equals(command)
                && lineArray[1].equals(list)) {
                lineCount++;
                System.out.println("line = " + lineCount);
                continue;
            }

            // (4) If line decodes to list a directoryMarker + name
            if (lineArray[0].equals(directoryMarker)){
                String directoryName = lineArray[1];
                Directory newDirectory = new Directory (directoryName,currentDirectory);
                currentDirectory.addSubDirectory(newDirectory);
                lineCount++;
                System.out.println("line = " + lineCount);
                continue;
            }

            // (5) At this point in code flow, line should
            // decode to list a fileName + fileSize
            int fileSize = Integer.parseInt(lineArray[0]);
            String fileName = lineArray[1];
            uuidToDirectoryTracker.get(currentDirectory.getUUID()).addDataFile(fileName,fileSize);
            lineCount++;
            System.out.println("line = " + lineCount);
        }

        // Get sum of all directory sizes for directories sized < 100000
        int sumOfDirectorySizes = 0;
        for (Directory directory : uuidToDirectoryTracker.values()){
            int directorySize = directory.size();
            if (directorySize <= 100000) {
                sumOfDirectorySizes += directorySize;
            }
        }
        System.out.println("sumOfDirectorySizes = " + sumOfDirectorySizes);
    }
}


/////////////////////////////////////////////////////////////////////////////////////////////////////////////
////////////// CODE I RAN TO ENSURE THAT THE DATA FILE NEVER RAN $ LS COMMAND TWICE IN A ROW ////////////////
        /*String line = "";
        int listCommandCounter = 0;
        int lineCounter = 1;
        while ((line = readDataLine(dataFileReader)) != null){
            // Separate lines into their parts then decode their parts into their meaning
            String[] lineArray = line.split("\\s+");
            System.out.println("lineArray = " + Arrays.toString(lineArray));

            // If the line is a command
            if (lineArray[0].equals(command) && lineArray[1].equals(changeDirectory)){
                listCommandCounter = 0;
            }
            // Check for previous line being
            if (lineArray[0].equals(command) && lineArray[1].equals(list)){
                listCommandCounter++;
            }
            System.out.print("line = " + lineCounter + "   ");
            System.out.println("listCommandCounter = " + listCommandCounter);

            if (listCommandCounter>1){
                System.out.println("yikes");
                break;
            }
            lineCounter++;
        }*/
//////////////////////////////////////////////////////////////////////////////////////////