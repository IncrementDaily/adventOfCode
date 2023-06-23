package Day8Puzzle;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.nio.Buffer;
import java.util.Arrays;
import java.util.HashSet;

public class Day8PuzzlePart1 {

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
            e.printStackTrace();
        }
        return dataLine;
    }
    public static void closeDataFile(BufferedReader reader){
        try {
            reader.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static class Tree{
        private int row;
        private int slot;
        private String name;

        public Tree(int row, int slot) {
            this.row = row;
            this.slot = slot;
            this.name = this.toString();
        }

        @Override
        public int hashCode() {
            return name.hashCode();
        }
        @Override
        public boolean equals(Object obj){
            return (this.hashCode() == obj.hashCode());
        }
        @Override
        public String toString(){
            StringBuilder sb = new StringBuilder();
            StringBuilder build = sb
                    .append("(")
                    .append(row)
                    .append(",")
                    .append(slot)
                    .append(")");
            return build.toString();
        }
    }

    public static void printForest(int[][] forest){
        for (int row = 0; row < forest.length; row++) {
            for (int slot = 0; slot < forest[row].length; slot++) {
                System.out.print(forest[row][slot] + " ");
            }
            System.out.println();
        }
    }
    private static boolean visibleFromLeft(int testRow, int testSlot, int[][] forest){
        int testedTree = 0;
        try {
            testedTree = forest[testRow][testSlot];
        } catch (IndexOutOfBoundsException e) {
            System.out.println("IndexOutOfBounds: no forest at those coordinates");
            return false;
        }
        for (int slot = 0; slot < testSlot; slot++) {
            if (forest[testRow][slot] >= testedTree) return false;
        }
        return true;
    }
    private static boolean visibleFromRight(int testRow, int testSlot, int[][] forest){
        int testedTree = 0;
        try {
            testedTree = forest[testRow][testSlot];
        } catch (IndexOutOfBoundsException e) {
            System.out.println("IndexOutOfBounds: no forest at those coordinates");
            return false;
        }
        for (int slot = forest[testRow].length-1; slot > testSlot; slot--) {
            if (forest[testRow][slot] >= testedTree) return false;
        }
        return true;
    }
    private static boolean visibleFromTop(int testRow, int testSlot, int[][] forest){
        int testedTree = 0;
        try {
            testedTree = forest[testRow][testSlot];
        } catch (IndexOutOfBoundsException e) {
            System.out.println("IndexOutOfBounds: no forest at those coordinates");
            return false;
        }
        for (int row = 0; row < testRow; row++) {
            if (forest[row][testSlot] >= testedTree) return false;
        }
        return true;
    }
    private static boolean visibleFromBottom(int testRow, int testSlot, int[][] forest){
        int testedTree = 0;
        try {
            testedTree = forest[testRow][testSlot];
        } catch (IndexOutOfBoundsException e) {
            System.out.println("IndexOutOfBounds: no forest at those coordinates");
            return false;
        }
        for (int row = forest.length-1; row > testRow; row--) {
            if (forest[row][testSlot] >= testedTree) return false;
        }
        return true;
    }
    private static int countVisibleTrees(int[][] forest){
        // Initialize HashSet of Tree objects (avoids duplicates)
        HashSet<Tree> visibleTrees = new HashSet<>();

        // Loop through and check if each tree (forest[row][slot]) is visible
        // from left, right, top, or bottom
        for (int row = 0; row < forest.length; row++) {
            for (int slot = 0; slot < forest[row].length; slot++) {
                // If visible from left, add to visibleTrees set then continue
                if (visibleFromLeft(row,slot,forest)){
                    Tree visibleTree = new Tree(row,slot);
                    visibleTrees.add(visibleTree);
                    continue;
                }
                // If visible from right, add to visibleTrees set then continue
                if (visibleFromRight(row,slot,forest)){
                    Tree visibleTree = new Tree(row,slot);
                    visibleTrees.add(visibleTree);
                    continue;
                }
                // If visible from top, add to visibleTrees set then continue
                if (visibleFromTop(row,slot,forest)){
                    Tree visibleTree = new Tree(row,slot);
                    visibleTrees.add(visibleTree);
                    continue;
                }
                // If visible from bottom, add to visibleTrees set then continue
                if (visibleFromBottom(row,slot,forest)){
                    Tree visibleTree = new Tree(row,slot);
                    visibleTrees.add(visibleTree);
                    continue;
                }
            }
        }
        return visibleTrees.size();
    }

    public static void main(String[] args) {
        // Initialize input
        String dataFilePath = "src/Day8Puzzle/Day8PuzzleData.txt";
        String testDataFilePath = "src/Day8Puzzle/TestData.txt";
        BufferedReader dataFileReader = getDataFileReader(dataFilePath);

        // Populate a giant 2D array to iterate over (forest)
        int numberOfRows = getDataFileLineCount(dataFilePath);
        int numberOfSlots = readDataLine(dataFileReader).length();
        closeDataFile(dataFileReader);
        dataFileReader = getDataFileReader(dataFilePath);
        int[][] forest = new int[numberOfSlots][numberOfRows];

        for (int row = 0; row < forest.length; row++) {
            char[] charsInLine = readDataLine(dataFileReader).toCharArray();
            for (int slot = 0; slot < forest[row].length; slot++) {
                forest[row][slot] = Integer.parseInt(String.valueOf(charsInLine[slot]));
            }
        }

//      Step 2: Consider each tree from the left, right, top, and bottom (don't duplicate)
//        printForest(forest);
        System.out.println("totalVisibleTrees = " + countVisibleTrees(forest));
    }
}

//////////////////TESTING/////////////////
/*Tree test1 = new Tree(0,0);
    Tree test2 = new Tree(1,1);
    Tree test3 = new Tree(3,0);
    Tree test4 = new Tree(3,0);
    Tree test5 = new Tree(4,1);
    Tree test6 = new Tree(0,0);

    HashSet<Tree> treeHashSet = new HashSet<>();
        treeHashSet.add(test1);
                treeHashSet.add(test2);
                treeHashSet.add(test3);
                treeHashSet.add(test4);
                treeHashSet.add(test5);
                treeHashSet.add(test6);

                System.out.println(treeHashSet);
                System.out.println(treeHashSet.size());*/
//////////////////////////////////////////
