package Day8Puzzle;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.nio.Buffer;
import java.util.Arrays;
import java.util.HashSet;

public class Day8PuzzlePart2 {

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
        // toString populates the name field of a Tree object based on the
        // coordinates of the Tree
        //
        // EXAMPLE: Tree tree = new Tree(15,143);
        // tree.getName() == "(15,143)";
        // tree.toString() == "(15,143)";
        // String treeDemonstration = tree.toString();
        // tree.hashCode() == treeDemonstration.hashCode();
        //
        // hashCode/equals/toString methods for this class make it so that any two Tree objects
        // which are at the same coordinates in an int[][] will have identical hashCode and
        // be equals() == true to each other. Thus, a HashSet<Tree> will reject duplicates based
        // on Tree objects that share the same coordinates.
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
        private int otherTreesVisLeftward(int[][] forest){
            // Initialize counter
            int visibleTreeCount = 0;

            // testTree is the tree we are looking leftward from
            int testRow = this.row;
            int testSlot = this.slot;
            int testTree = forest[testRow][testSlot];

            // currentTree begins as the tree immediately to the left of testTree
            int currentSlot = testSlot-1;
            if (currentSlot < 0) return 0; // Avoid iOOB exception
            int currentTree = forest[testRow][currentSlot];

            // Look left until a tree blocks view or iOOB exception must be avoided
            while (true){
                // currentTree is visible
                visibleTreeCount++;

                // If currentTree blocks view, stop looking left
                if (currentTree >= testTree) break;

                // Redefine currentTree: look at next tree to the left
                currentSlot--;
                if (currentSlot < 0) return visibleTreeCount; // Avoid iOOB exception
                currentTree = forest[testRow][currentSlot];
            }
            return visibleTreeCount;
        }
        private int otherTreesVisUpward(int[][] forest){
            // Initialize counter
            int visibleTreeCount = 0;

            // testTree is the tree we are looking upward from
            int testRow = this.row;
            int testSlot = this.slot;
            int testTree = forest[testRow][testSlot];

            // currentTree begins as the tree immediately upward of testTree
            int currentRow = testRow-1;
            if (currentRow < 0) return 0; // Avoid iOOB exception
            int currentTree = forest[currentRow][testSlot];

            // Look upward until a tree blocks view or iOOB exception must be avoided
            while (true){
                // currentTree is visible
                visibleTreeCount++;

                // If currentTree blocks view, stop looking upward
                if (currentTree >= testTree) break;

                // Redefine currentTree: look at next tree upward
                currentRow--;
                if (currentRow < 0) return visibleTreeCount; // Avoid iOOB exception
                currentTree = forest[currentRow][testSlot];
            }
            return visibleTreeCount;
        }
        private int otherTreesVisRightward(int[][] forest){
            // Initialize counter
            int visibleTreeCount = 0;

            // testTree is the tree we are looking leftward from
            int testRow = this.row;
            int testSlot = this.slot;
            int testTree = forest[testRow][testSlot];

            // currentTree begins as the tree immediately to the left of testTree
            int currentSlot = testSlot+1;
            if (currentSlot >= forest[testRow].length) return 0; // Avoid iOOB exception
            int currentTree = forest[testRow][currentSlot];

            // Look right until a tree blocks view or iOOB exception must be avoided
            while (true){
                // currentTree is visible
                visibleTreeCount++;

                // If currentTree blocks view, stop looking right
                if (currentTree >= testTree) break;

                // Redefine currentTree: look at next tree to the right
                currentSlot++;
                if (currentSlot >= forest[testRow].length) return visibleTreeCount; // Avoid iOOB exception
                currentTree = forest[testRow][currentSlot];
            }
            return visibleTreeCount;
        }
        private int otherTreesVisDownward(int[][] forest){
            // Initialize counter
            int visibleTreeCount = 0;

            // testTree is the tree we are looking upward from
            int testRow = this.row;
            int testSlot = this.slot;
            int testTree = forest[testRow][testSlot];

            // currentTree begins as the tree immediately downward of testTree
            int currentRow = testRow+1;
            if (currentRow >= forest.length) return 0; // Avoid iOOB exception
            int currentTree = forest[currentRow][testSlot];

            // Look downward until a tree blocks view or iOOB exception must be avoided
            while (true){
                // currentTree is visible
                visibleTreeCount++;

                // If currentTree blocks view, stop looking downward
                if (currentTree >= testTree) break;

                // Redefine currentTree: look at next tree downward
                currentRow++;
                if (currentRow >= forest.length) return visibleTreeCount; // Avoid iOOB exception
                currentTree = forest[currentRow][testSlot];
            }
            return visibleTreeCount;
        }
        private int calculateScenicScore(int[][] forest){
            int scenicScore = 0;
            scenicScore = otherTreesVisLeftward(forest)
                    * otherTreesVisUpward(forest)
                    * otherTreesVisRightward(forest)
                    * otherTreesVisDownward(forest);
            return scenicScore;
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
                // If visible from left, add to visibleTrees then continue
                if (visibleFromLeft(row,slot,forest)){
                    Tree visibleTree = new Tree(row,slot);
                    visibleTrees.add(visibleTree);
                    continue;
                }
                // If visible from right, add to visibleTrees then continue
                if (visibleFromRight(row,slot,forest)){
                    Tree visibleTree = new Tree(row,slot);
                    visibleTrees.add(visibleTree);
                    continue;
                }
                // If visible from top, add to visibleTrees then continue
                if (visibleFromTop(row,slot,forest)){
                    Tree visibleTree = new Tree(row,slot);
                    visibleTrees.add(visibleTree);
                    continue;
                }
                // If visible from bottom, add to visibleTrees then continue
                if (visibleFromBottom(row,slot,forest)){
                    Tree visibleTree = new Tree(row,slot);
                    visibleTrees.add(visibleTree);
                    continue;
                }
            }
        }
//        System.out.println(visibleTrees);
        return visibleTrees.size();

    }
    private static int calcHighestScenicScore(int[][] forest){
        // Initialize Tracker
        int highestScenicScore = 0;
        Tree currentTree = null;
        int currentScore = 0;

        // Loop through and check each Tree's scenicScore
        for (int row = 0; row < forest.length; row++) {
            for (int slot = 0; slot < forest[row].length; slot++) {
                // Reassign currentTree
                currentTree = new Tree(row,slot);

                // Find tree's scenicScore
                currentScore = currentTree.calculateScenicScore(forest);

                // Track highest
                if (currentScore > highestScenicScore) highestScenicScore = currentScore;
            }
        }
        return highestScenicScore;
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

        System.out.println(calcHighestScenicScore(forest));
    }
}

////////////// TESTING//////////////////////
    /*printForest(forest);

    System.out.println();

    Tree testTree = new Tree(2,2);
    System.out.println("Visible Trees left of " + testTree.toString() + " = " + testTree.otherTreesVisLeftward(forest));
    System.out.println("Visible Trees upward of " + testTree.toString() + " = " + testTree.otherTreesVisUpward(forest));
    System.out.println("Visible Trees right of " + testTree.toString() + " = " + testTree.otherTreesVisRightward(forest));
    System.out.println("Visible Trees downward of " + testTree.toString() + " = " + testTree.otherTreesVisDownward(forest));

    System.out.println();

    System.out.println("testTree's scenicScore = " + testTree.calculateScenicScore(forest));
    System.out.println(calcHighestScenicScore(forest));*/
////////////////////////////////////////////

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
