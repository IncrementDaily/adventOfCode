package Day9Puzzle;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashSet;

public class Day9PuzzlePart1 {
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

    public static class Tail {
        // Relative Position Constants
        private final String START = "Start";
        private final String L = "L";
        private final String UL = "UL";
        private final String U = "U";
        private final String UR = "UR";
        private final String R = "R";
        private final String DR = "DR";
        private final String D = "D";
        private final String DL = "DL";

        public HashSet<Tail> getVisitedPositions() {
            return visitedPositions;
        }

        private int positionX;
        private int positionY;
        private String name;
        private String currentRelPosition;
        private HashSet<Tail> visitedPositions = new HashSet<>();

        public Tail(int x, int y) {
            this.positionX = x;
            this.positionY = y;
            this.name = this.toString();
            this.visitedPositions.add(new Tail(x,y,true));
            this.currentRelPosition = START;
        }

        public Tail(){
            String bitzs = "Yay";
        }

        public Tail(int x, int y, boolean isPositionMarker) {
            this.positionX = x;
            this.positionY = y;
            this.name = this.toString();
        }

        public boolean headMoveLeft(){
            if (currentRelPosition.equals(START)){
                currentRelPosition = R;
                return false;
            }
            if (currentRelPosition.equals(L)){
                currentRelPosition = START;
                return false;
            }
            if (currentRelPosition.equals(UL)){
                currentRelPosition = U;
                return false;
            }
            if (currentRelPosition.equals(U)){
                currentRelPosition = UR;
                return false;
            }
            if (currentRelPosition.equals(UR)){
                // Change position
                positionX--;
                positionY--;
                currentRelPosition = R;
                // Rename to achieve new HashCode
                rename();
                // Add to HashSet visitedPositions (will not duplicate already visitedPositions positions)
                visit();
                return true;
            }
            if (currentRelPosition.equals(R)){
                // Change position
                positionX--;
                // Rename to achieve new HashCode
                rename();
                // Add to HashSet visitedPositions (will not duplicate already visitedPositions positions)
                visit();
                return true;
            }
            if (currentRelPosition.equals(DR)){
                // Change position
                positionX--;
                positionY++;
                currentRelPosition = R;
                // Rename to achieve new HashCode
                rename();
                // Add to HashSet visitedPositions (will not duplicate already visitedPositions positions)
                visit();
                return true;
            }
            if (currentRelPosition.equals(D)){
                currentRelPosition = DR;
                return false;
            }
            if (currentRelPosition.equals(DL)){
                currentRelPosition = D;
                return false;
            }
            return false;
        }
        public boolean headMoveUp(){
            if (currentRelPosition.equals(START)){
                currentRelPosition = D;
                return false;
            }
            if (currentRelPosition.equals(L)){
                currentRelPosition = DL;
                return false;
            }
            if (currentRelPosition.equals(UL)){
                currentRelPosition = L;
                return false;
            }
            if (currentRelPosition.equals(U)){
                currentRelPosition = START;
                return false;
            }
            if (currentRelPosition.equals(UR)){
                currentRelPosition = R;
                return false;
            }
            if (currentRelPosition.equals(R)){
                currentRelPosition = DR;
                return false;
            }
            if (currentRelPosition.equals(DR)){
                // Change position
                positionX--;
                positionY++;
                currentRelPosition = D;
                // Rename to achieve new HashCode
                rename();
                // Add to HashSet visitedPositions (will not duplicate already visitedPositions positions)
                visit();
                return true;
            }
            if (currentRelPosition.equals(D)){
                // Change position
                positionY++;
                // Rename to achieve new HashCode
                rename();
                // Add to HashSet visitedPositions (will not duplicate already visitedPositions positions)
                visit();
                return true;
            }
            if (currentRelPosition.equals(DL)){
                // Change position
                positionX++;
                positionY++;
                currentRelPosition = D;
                // Rename to achieve new HashCode
                rename();
                // Add to HashSet visitedPositions (will not duplicate already visitedPositions positions)
                visit();
                return true;
            }
            return false;
        }
        public boolean headMoveRight(){
            if (currentRelPosition.equals(START)){
                currentRelPosition = L;
                return false;
            }
            if (currentRelPosition.equals(L)){
                // Change position
                positionX++;
                // Rename to achieve new HashCode
                rename();
                // Add to HashSet visitedPositions (will not duplicate already visitedPositions positions)
                visit();
                return true;
            }
            if (currentRelPosition.equals(UL)){
                // Change position
                positionX++;
                positionY--;
                currentRelPosition = L;
                // Rename to achieve new HashCode
                rename();
                // Add to HashSet visitedPositions (will not duplicate already visitedPositions positions)
                visit();
                return true;
            }
            if (currentRelPosition.equals(U)){
                currentRelPosition = UL;
                return false;
            }
            if (currentRelPosition.equals(UR)){
                currentRelPosition = U;
                return false;
            }
            if (currentRelPosition.equals(R)){
                currentRelPosition = START;
                return false;
            }
            if (currentRelPosition.equals(DR)){
                currentRelPosition = D;
                return false;
            }
            if (currentRelPosition.equals(D)){
                currentRelPosition = DL;
                return false;
            }
            if (currentRelPosition.equals(DL)){
                // Change position
                positionX++;
                positionY++;
                currentRelPosition = L;
                // Rename to achieve new HashCode
                rename();
                // Add to HashSet visitedPositions (will not duplicate already visitedPositions positions)
                visit();
                return true;
            }
            return false;
        }
        public boolean headMoveDown(){
            if (currentRelPosition.equals(START)){
                currentRelPosition = U;
                return false;
            }
            if (currentRelPosition.equals(L)){
                currentRelPosition = UL;
                return false;
            }
            if (currentRelPosition.equals(UL)){
                // Change position
                positionX++;
                positionY--;
                currentRelPosition = U;
                // Rename to achieve new HashCode
                rename();
                // Add to HashSet visitedPositions (will not duplicate already visitedPositions positions)
                visit();
                return true;
            }
            if (currentRelPosition.equals(U)){
                // Change position
                positionY--;
                // Rename to achieve new HashCode
                rename();
                // Add to HashSet visitedPositions (will not duplicate already visitedPositions positions)
                visit();
                return true;
            }
            if (currentRelPosition.equals(UR)){
                // Change position
                positionX--;
                positionY--;
                currentRelPosition = U;
                // Rename to achieve new HashCode
                rename();
                // Add to HashSet visitedPositions (will not duplicate already visitedPositions positions)
                visit();
                return true;
            }
            if (currentRelPosition.equals(R)){
                currentRelPosition = UR;
                return false;
            }
            if (currentRelPosition.equals(DR)){
                currentRelPosition = R;
                return false;
            }
            if (currentRelPosition.equals(D)){
                currentRelPosition = START;
                return false;
            }
            if (currentRelPosition.equals(DL)){
                currentRelPosition = L;
                return false;
            }
            return false;
        }
        public void visit(){
            visitedPositions.add(new Tail(this.getPositionX(),this.getPositionY(),true));
        }
        public int numPositionsVisited(){
            return visitedPositions.size();
        }
        public void printPositionsVisited(){
            System.out.println(visitedPositions);
        }
        public int getPositionX() {
            return positionX;
        }
        public void setPositionX(int positionX) {
            this.positionX = positionX;
        }
        public int getPositionY() {
            return positionY;
        }
        public void setPositionY(int positionY) {
            this.positionY = positionY;
        }
        public String getName() {
            return name;
        }
        public void rename() {
            this.name = this.toString();
        }
        @Override
        public int hashCode() {
            return name.hashCode();
        }
        @Override
        public boolean equals(Object obj) {
            return (this.hashCode() == obj.hashCode());
        }
        @Override
        public String toString() {
            StringBuilder name = new StringBuilder();
            name = name.append("(")
                    .append(positionX)
                    .append(",")
                    .append(positionY)
                    .append(")");
            return name.toString();
        }
    }

    public static void main(String[] args) {
        // Initialize input
        String dataFilePath = "src/Day9Puzzle/Day9PuzzleData.txt";
        String testDataFilePath = "src/Day9Puzzle/TestData.txt";
        BufferedReader dataFileReader = getDataFileReader(dataFilePath);

        // Initialize tail at origin and movement tracker (movement tracker just for fun)
        Tail tail = new Tail(0,0);
        int movementCounter = 0;

        // Execute movement commands on tail
        String line;
        while ((line = readDataLine(dataFileReader)) != null){
            // Generate commandScript
            String[] commandScript = line.split("\\s+");

            // Interpret commandScript
            String direction = commandScript[0];
            int executionCycles = Integer.parseInt(commandScript[1]);

            // Execute commandScript
            for (int i = 0; i < executionCycles; i++) {
                if (direction.equals("L")) {
                    // If tail actually moved, movementCounter++
                    if (tail.headMoveLeft()) movementCounter++;
                }
                if (direction.equals("U")) {
                    // If tail actually moved, movementCounter++
                    if (tail.headMoveUp()) movementCounter++;
                }
                if (direction.equals("R")) {
                    // If tail actually moved, movementCounter++
                    if (tail.headMoveRight()) movementCounter++;
                }
                if (direction.equals("D")) {
                    // If tail actually moved, movementCounter++
                    if (tail.headMoveDown()) movementCounter++;
                }
            }
        }

//        System.out.println("numberOfPositionsVisited = " + tail.numPositionsVisited());
//        ////////// Movement tracker is just for fun ////////////////
//        System.out.println("Tail moved " + movementCounter + " times.");
//        tail.printPositionsVisited();

        ArrayList<Tail> tailVisitedPositions = new ArrayList<>(tail.getVisitedPositions());

        Collections.sort(tailVisitedPositions, new Comparator<Tail>(){
            @Override
            public int compare(Tail o1, Tail o2) {
                return Integer.compare(o1.getPositionX(), o2.getPositionX());
            }
        });

        System.out.println(tailVisitedPositions);
        Tail furthestRight = tailVisitedPositions.get(tailVisitedPositions.size()-1);
        int rightExtreme = furthestRight.getPositionX();
        Tail furthestLeft = tailVisitedPositions.get(0);
        int leftExtreme = furthestLeft.getPositionX();


        Collections.sort(tailVisitedPositions, new Comparator<Tail>(){
            @Override
            public int compare(Tail o1, Tail o2) {
                return Integer.compare(o1.getPositionY(), o2.getPositionY());
            }
        });

        System.out.println(tailVisitedPositions);
        Tail furthestUp = tailVisitedPositions.get(tailVisitedPositions.size()-1);
        int upExtreme = furthestUp.getPositionY();
        Tail furthestDown = tailVisitedPositions.get(0);
        int downExtreme = furthestDown.getPositionY();

        System.out.println("leftExtreme = " + leftExtreme);
        System.out.println("upExtreme = " + upExtreme);
        System.out.println("rightExtreme = " + rightExtreme);
        System.out.println("downExtreme = " + downExtreme);
    }
}