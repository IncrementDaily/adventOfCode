package Day9Puzzle;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;

public class Day9PuzzlePart2 {
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

    public static class Rope{
        private LinkedList<Knot> knots;

        public Rope(int xPosition, int yPosition, int numberOfKnots){
            knots = new LinkedList<>();

            // Initialize all knots except last one
            for (int i = 0; i < numberOfKnots-1; i++) {
                Knot newKnot = new Knot(xPosition,yPosition);
                newKnot.setName(String.valueOf(i));
                knots.add(newKnot);
            }
            // Last knot is the tail
            knots.add(new Knot(xPosition,yPosition,true));

            ListIterator<Rope.Knot> listIter = this.knots().listIterator();

            // Link all the knots to each other through their next pointers
            while (listIter.hasNext()){
                    Knot currentKnot = listIter.next();
                    if (listIter.hasNext()){
                        Knot nextKnot = listIter.next();
                        currentKnot.setNext(nextKnot);
                    }else break;
                    // Move the cursor back one index
                    listIter.previous();
            }
        }

        public LinkedList<Knot> knots() {
            return knots;
        }
        public void headMoveLeft(){
            knots().getFirst().headMoveLeft();
        }
        public void headMoveUp(){
            knots().getFirst().headMoveUp();
        }
        public void headMoveRight(){
            knots().getFirst().headMoveRight();
        }
        public void headMoveDown(){
            knots().getFirst().headMoveDown();
        }
        public int numPositionsVisited(){
            return knots().getLast().visitedPositions.size();
        }
        public void printPositionsVisited(){
            System.out.println(knots().getLast().visitedPositions);
        }
//        public void printPositionChangesVisual(){
//            Knot tail = this.knots().getLast();
//            ArrayList<String> tailVisitedPositions = new ArrayList<>(tail.getVisitedPositions());
//
//            // Sort visistedPositions by PositionX
//            Collections.sort(tailVisitedPositions, new Comparator<String>(){
//                @Override
//                public int compare(String o1, String o2) {
//                    String[] xAndYposO1 = o1.split("\\(|\\)|,");
//                    int xPosO1 = Integer.parseInt(xAndYposO1[2]);
//
//                    String[] xAndYposO2 = o2.split("\\(|\\)|,");
//                    int xPosO2 = Integer.parseInt(xAndYposO2[2]);
//
//                    return Integer.compare(xPosO1,xPosO2);
//                }
//            });
//
//            System.out.println(tailVisitedPositions);
//
//            String[] furthestLeft = tailVisitedPositions.get(0).split("\\(|\\)|,");
//            int leftExtreme = Integer.parseInt(furthestLeft[2]);
////            Day9PuzzlePart1.Tail furthestLeft = tailVisitedPositions.get(0);
////            int leftExtreme = furthestLeft.getPositionX();
//
//
////            Collections.sort(tailVisitedPositions, new Comparator<String>(){
////                @Override
////                public int compare(String o1, String o2) {
////                    String[] xAndYposO1 = o1.split("\\(|\\)|,");
////                    int xPosO1 = Integer.parseInt(xAndYposO1[2]);
////                    int yPosO1 = Integer.parseInt(xAndYposO1[1]);
////
////                    String[] xAndYposO2 = o2.split("\\(|\\)|,");
////                    int xPosO2 = Integer.parseInt(xAndYposO2[2]);
////                    int yPosO2 = Integer.parseInt(xAndYposO2[1]);
////
////                    return Integer.compare(xPosO1,xPosO2);
////                }
////            });
////
////            System.out.println(tailVisitedPositions);
////            Day9PuzzlePart1.Tail furthestUp = tailVisitedPositions.get(tailVisitedPositions.size()-1);
////            int upExtreme = furthestUp.getPositionY();
////            Day9PuzzlePart1.Tail furthestDown = tailVisitedPositions.get(0);
////            int downExtreme = furthestDown.getPositionY();
//
//
//
//
//            int endRow = tail.getPositionY()+175;
//            int endSlot = tail.getPositionX()+175;
//
//            for (int row = 0; row < tail.traversalPathVisual.length; row++) {
//                for (int slot = 0; slot < tail.traversalPathVisual[row].length; slot++) {
//                    if (tail.traversalPathVisual[row][slot] == 1){
//                        System.out.print("X");
//                    }else System.out.print(" ");
//
//                    if (tail.traversalPathVisual[row][slot] == 2) System.out.print("ORIGIN");
//                    if (row == endRow && slot == endSlot) System.out.print("ENDPOINT");
//                }
//                System.out.println();
//            }
//        }

        public static class Knot {
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

        private String name;
        private Knot next;
        private String currentRelPosition;
        private boolean isRopeTail;

        private int positionX;
        private int positionY;
        private HashSet<String> visitedPositions = new HashSet<>();
        private int[][] traversalPathVisual;

        public Knot(int x, int y) {
            this.positionX = x;
            this.positionY = y;
            this.name = this.toString();
            this.currentRelPosition = START;
            this.isRopeTail = false;
        }
        public Knot(int x, int y, boolean isRopeTail) {
            this.positionX = x;
            this.positionY = y;
            this.name = this.toString();
            this.visitedPositions.add(this.getName());
            this.currentRelPosition = START;
            this.isRopeTail = isRopeTail;
            traversalPathVisual = new int[350][350];
            traversalPathVisual[175][175] = 2;
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
                if (next != null) next.headMoveDownLeft();
                // Rename to achieve new HashCode
                if (isRopeTail) rename();
                // Add to HashSet visitedPositions (will not duplicate already visitedPositions positions)
                if (isRopeTail) visit();
                return true;
            }
            if (currentRelPosition.equals(R)){
                // Change position
                positionX--;
                if (next != null) next.headMoveLeft();
                // Rename to achieve new HashCode
                if (isRopeTail) rename();
                // Add to HashSet visitedPositions (will not duplicate already visitedPositions positions)
                if (isRopeTail) visit();
                return true;
            }
            if (currentRelPosition.equals(DR)){
                // Change position
                positionX--;
                positionY++;
                currentRelPosition = R;
                if (next != null) next.headMoveUpLeft();
                // Rename to achieve new HashCode
                if (isRopeTail) rename();
                // Add to HashSet visitedPositions (will not duplicate already visitedPositions positions)
                if (isRopeTail) visit();
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
        public boolean headMoveUpLeft(){
                if (currentRelPosition.equals(START)){
                    currentRelPosition = DR;
                    return false;
                }
                if (currentRelPosition.equals(L)){
                    currentRelPosition = D;
                    return false;
                }
                if (currentRelPosition.equals(UL)){
                    currentRelPosition = START;
                    return false;
                }
                if (currentRelPosition.equals(U)){
                    currentRelPosition = R;
                    return false;
                }
                if (currentRelPosition.equals(UR)){
                    // Change position
                    positionX--;
                    currentRelPosition = R;
                    if (next != null) next.headMoveLeft();

                    // Rename to achieve new HashCode
                    if (isRopeTail) rename();
                    // Add to HashSet visitedPositions (will not duplicate already visitedPositions positions)
                    if (isRopeTail) visit();
                    return true;
                }
                if (currentRelPosition.equals(R)){
                    // Change position
                    positionX--;
                    positionY++;
                    currentRelPosition = R;
                    if (next != null) next.headMoveUpLeft();
                    // Rename to achieve new HashCode
                    if (isRopeTail) rename();
                    // Add to HashSet visitedPositions (will not duplicate already visitedPositions positions)
                    if (isRopeTail) visit();
                    return true;
                }
                if (currentRelPosition.equals(DR)){
                    // Change position
                    positionX--;
                    positionY++;
                    currentRelPosition = DR;
                    if (next != null) next.headMoveUpLeft();
                    // Rename to achieve new HashCode
                    if (isRopeTail) rename();
                    // Add to HashSet visitedPositions (will not duplicate already visitedPositions positions)
                    if (isRopeTail) visit();
                    return true;
                }
                if (currentRelPosition.equals(D)){
                    // Change position
                    positionX--;
                    positionY++;
                    currentRelPosition = D;
                    if (next != null) next.headMoveUpLeft();
                    // Rename to achieve new HashCode
                    if (isRopeTail) rename();
                    // Add to HashSet visitedPositions (will not duplicate already visitedPositions positions)
                    if (isRopeTail) visit();
                    return true;
                }
                if (currentRelPosition.equals(DL)){
                    // Change position
                    positionY++;
                    currentRelPosition = D;
                    if (next != null) next.headMoveUp();
                    // Rename to achieve new HashCode
                    if (isRopeTail) rename();
                    // Add to HashSet visitedPositions (will not duplicate already visitedPositions positions)
                    if (isRopeTail) visit();
                    return true;
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
                if (next != null) next.headMoveUpLeft();
                // Rename to achieve new HashCode
                if (isRopeTail) rename();
                // Add to HashSet visitedPositions (will not duplicate already visitedPositions positions)
                if (isRopeTail) visit();
                return true;
            }
            if (currentRelPosition.equals(D)){
                // Change position
                positionY++;
                if (next != null) next.headMoveUp();
                // Rename to achieve new HashCode
                if (isRopeTail) rename();
                // Add to HashSet visitedPositions (will not duplicate already visitedPositions positions)
                if (isRopeTail) visit();
                return true;
            }
            if (currentRelPosition.equals(DL)){
                // Change position
                positionX++;
                positionY++;
                currentRelPosition = D;
                if (next != null) next.headMoveUpRight();
                // Rename to achieve new HashCode
                if (isRopeTail) rename();
                // Add to HashSet visitedPositions (will not duplicate already visitedPositions positions)
                if (isRopeTail) visit();
                return true;
            }
            return false;
        }
        public boolean headMoveUpRight(){
                if (currentRelPosition.equals(START)){
                    currentRelPosition = DL;
                    return false;
                }
                if (currentRelPosition.equals(L)){
                    // Change position
                    positionX++;
                    positionY++;
                    currentRelPosition = L;
                    if (next != null) next.headMoveUpRight();
                    // Rename to achieve new HashCode
                    if (isRopeTail) rename();
                    // Add to HashSet visitedPositions (will not duplicate already visitedPositions positions)
                    if (isRopeTail) visit();
                    return true;
                }
                if (currentRelPosition.equals(UL)){
                    // Change position
                    positionX++;
                    currentRelPosition = L;
                    if (next != null) next.headMoveRight();
                    // Rename to achieve new HashCode
                    if (isRopeTail) rename();
                    // Add to HashSet visitedPositions (will not duplicate already visitedPositions positions)
                    if (isRopeTail) visit();
                    return true;
                }
                if (currentRelPosition.equals(U)){
                    currentRelPosition = L;
                    return false;
                }
                if (currentRelPosition.equals(UR)){
                    currentRelPosition = START;
                    return false;
                }
                if (currentRelPosition.equals(R)){
                    currentRelPosition = D;
                    return false;
                }
                if (currentRelPosition.equals(DR)){
                    // Change position
                    positionY++;
                    currentRelPosition = D;
                    if (next != null) next.headMoveUp();
                    // Rename to achieve new HashCode
                    if (isRopeTail) rename();
                    // Add to HashSet visitedPositions (will not duplicate already visitedPositions positions)
                    if (isRopeTail) visit();
                    return true;
                }
                if (currentRelPosition.equals(D)){
                    // Change position
                    positionX++;
                    positionY++;
                    if (next != null) next.headMoveUpRight();
                    // Rename to achieve new HashCode
                    // Rename to achieve new HashCode
                    if (isRopeTail) rename();
                    // Add to HashSet visitedPositions (will not duplicate already visitedPositions positions)
                    if (isRopeTail) visit();
                    return true;
                }
                if (currentRelPosition.equals(DL)){
                    // Change position
                    positionX++;
                    positionY++;
                    if (next != null) next.headMoveUpRight();
                    // Rename to achieve new HashCode
                    // Rename to achieve new HashCode
                    if (isRopeTail) rename();
                    // Add to HashSet visitedPositions (will not duplicate already visitedPositions positions)
                    if (isRopeTail) visit();
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
                if (next != null) next.headMoveRight();
                // Rename to achieve new HashCode
                if (isRopeTail) rename();
                // Add to HashSet visitedPositions (will not duplicate already visitedPositions positions)
                if (isRopeTail) visit();
                return true;
            }
            if (currentRelPosition.equals(UL)){
                // Change position
                positionX++;
                positionY--;
                currentRelPosition = L;
                if (next != null) next.headMoveDownRight();
                // Rename to achieve new HashCode
                if (isRopeTail) rename();
                // Add to HashSet visitedPositions (will not duplicate already visitedPositions positions)
                if (isRopeTail) visit();
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
                if (next != null) next.headMoveUpRight();
                // Rename to achieve new HashCode
                if (isRopeTail) rename();
                // Add to HashSet visitedPositions (will not duplicate already visitedPositions positions)
                if (isRopeTail) visit();
                return true;
            }
            return false;
        }
        public boolean headMoveDownRight(){
                if (currentRelPosition.equals(START)){
                    currentRelPosition = UL;
                    return false;
                }
                if (currentRelPosition.equals(L)){
                    // Change position
                    positionX++;
                    positionY--;
                    if (next != null) next.headMoveDownRight();
                    // Rename to achieve new HashCode
                    if (isRopeTail) rename();
                    // Add to HashSet visitedPositions (will not duplicate already visitedPositions positions)
                    if (isRopeTail) visit();
                    return true;
                }
                if (currentRelPosition.equals(UL)){
                    // Change position
                    positionX++;
                    positionY--;
                    if (next != null) next.headMoveDownRight();
                    // Rename to achieve new HashCode
                    if (isRopeTail) rename();
                    // Add to HashSet visitedPositions (will not duplicate already visitedPositions positions)
                    if (isRopeTail) visit();
                    return true;
                }
                if (currentRelPosition.equals(U)){
                    // Change position
                    positionX++;
                    positionY--;
                    if (next != null) next.headMoveDownRight();
                    // Rename to achieve new HashCode
                    if (isRopeTail) rename();
                    // Add to HashSet visitedPositions (will not duplicate already visitedPositions positions)
                    if (isRopeTail) visit();
                    return true;
                }
                if (currentRelPosition.equals(UR)){
                    // Change position
                    positionY--;
                    currentRelPosition = U;
                    if (next != null) next.headMoveDown();
                    // Rename to achieve new HashCode
                    if (isRopeTail) rename();
                    // Add to HashSet visitedPositions (will not duplicate already visitedPositions positions)
                    if (isRopeTail) visit();
                    return true;
                }
                if (currentRelPosition.equals(R)){
                    currentRelPosition = U;
                    return false;
                }
                if (currentRelPosition.equals(DR)){
                    currentRelPosition = START;
                    return false;
                }
                if (currentRelPosition.equals(D)){
                    currentRelPosition = L;
                    return false;
                }
                if (currentRelPosition.equals(DL)){
                    // Change position
                    positionX++;
                    currentRelPosition = L;
                    if (next != null) next.headMoveRight();
                    // Rename to achieve new HashCode
                    if (isRopeTail) rename();
                    // Add to HashSet visitedPositions (will not duplicate already visitedPositions positions)
                    if (isRopeTail) visit();
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
                if (next != null) next.headMoveDownRight();
                // Rename to achieve new HashCode
                if (isRopeTail) rename();
                // Add to HashSet visitedPositions (will not duplicate already visitedPositions positions)
                if (isRopeTail) visit();
                return true;
            }
            if (currentRelPosition.equals(U)){
                // Change position
                positionY--;
                if (next != null) next.headMoveDown();
                // Rename to achieve new HashCode
                if (isRopeTail) rename();
                // Add to HashSet visitedPositions (will not duplicate already visitedPositions positions)
                if (isRopeTail) visit();
                return true;
            }
            if (currentRelPosition.equals(UR)){
                // Change position
                positionX--;
                positionY--;
                currentRelPosition = U;
                if (next != null) next.headMoveDownLeft();
                // Rename to achieve new HashCode
                if (isRopeTail) rename();
                // Add to HashSet visitedPositions (will not duplicate already visitedPositions positions)
                if (isRopeTail) visit();
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
        public boolean headMoveDownLeft(){
                if (currentRelPosition.equals(START)){
                    currentRelPosition = UR;
                    return false;
                }
                if (currentRelPosition.equals(L)){
                    currentRelPosition = U;
                    return false;
                }
                if (currentRelPosition.equals(UL)){
                    // Change position
                    positionY--;
                    currentRelPosition = U;
                    if (next != null) next.headMoveDown();
                    // Rename to achieve new HashCode
                    if (isRopeTail) rename();
                    // Add to HashSet visitedPositions (will not duplicate already visitedPositions positions)
                    if (isRopeTail) visit();
                    return true;
                }
                if (currentRelPosition.equals(U)){
                    // Change position
                    positionY--;
                    positionX--;
                    if (next != null) next.headMoveDownLeft();
                    // Rename to achieve new HashCode
                    if (isRopeTail) rename();
                    // Add to HashSet visitedPositions (will not duplicate already visitedPositions positions)
                    if (isRopeTail) visit();
                    return true;
                }
                if (currentRelPosition.equals(UR)){
                    // Change position
                    positionX--;
                    positionY--;
                    if (next != null) next.headMoveDownLeft();
                    // Rename to achieve new HashCode
                    if (isRopeTail) rename();
                    // Add to HashSet visitedPositions (will not duplicate already visitedPositions positions)
                    if (isRopeTail) visit();
                    return true;
                }
                if (currentRelPosition.equals(R)){
                    // Change position
                    positionX--;
                    positionY--;
                    if (next != null) next.headMoveDownLeft();
                    // Rename to achieve new HashCode
                    if (isRopeTail) rename();
                    // Add to HashSet visitedPositions (will not duplicate already visitedPositions positions)
                    if (isRopeTail) visit();
                    return true;
                }
                if (currentRelPosition.equals(DR)){
                    // Change position
                    positionX--;
                    currentRelPosition = R;
                    if (next != null) next.headMoveLeft();
                    // Rename to achieve new HashCode
                    if (isRopeTail) rename();
                    // Add to HashSet visitedPositions (will not duplicate already visitedPositions positions)
                    if (isRopeTail) visit();
                    return true;
                }
                if (currentRelPosition.equals(D)){
                    currentRelPosition = R;
                    return false;
                }
                if (currentRelPosition.equals(DL)){
                    currentRelPosition = START;
                    return false;
                }
                return false;
            }
        public void visit(){
            visitedPositions.add(this.getName());
            int x = this.positionX;
            int y = this.positionY;

            // 1 = visited, 0 = not visited
            traversalPathVisual[y+175][x+175] = 1;

            // traversalPathVisual[row][slot]
            //  OAO  <--- A is row == 0, slot == 1
            //  OOB  <--  B is row == 1, slot == 2
            //  OOC  <--  C is row == 2, slot == 2
        }
        public int numPositionsVisited(){
            return visitedPositions.size();
        }
        public void printPositionsVisited(){
            System.out.println(visitedPositions);
        }
        public void printPositionChangesVisual(){
            for (int row = 0; row < traversalPathVisual.length; row++) {
                for (int slot = 0; slot < traversalPathVisual[row].length; slot++) {
                    if (traversalPathVisual[row][slot] == 1){
                        System.out.print("X");
                    }else System.out.print(" ");
                }
                System.out.println();
            }

        }
        public HashSet<String> getVisitedPositions() {
            return visitedPositions;
        }
        public int getPositionX() {
            return positionX;
        }
        public int getPositionY() {
            return positionY;
        }
        public String getName() {
            return name;
        }
        public void printName(){
            System.out.println(this.getName());
        }
        public void rename() {
            this.name = this.toString();
        }
        public int hashCode() {
            return name.hashCode();
        }
        public boolean equals(Object obj) {
            return (this.hashCode() == obj.hashCode());
        }
        public String toString() {
            StringBuilder name = new StringBuilder();
            name = name.append("(")
                    .append(positionX)
                    .append(",")
                    .append(positionY)
                    .append(")");
            return name.toString();
        }
        public void setNext(Knot next) {
                this.next = next;
            }
        public void setName(String name) {
            this.name = name;
        }
        public boolean isRopeTail() {
            return isRopeTail;
        }
        }
    }

    public static void main(String[] args) {
        // Initialize input
        String dataFilePath = "src/Day9Puzzle/Day9PuzzleData.txt";
        String testDataFilePath = "src/Day9Puzzle/TestDataPart2.txt";
        BufferedReader dataFileReader = getDataFileReader(dataFilePath);

        // Initialize rope at origin
        Rope rope = new Rope(0,0,9);

        // Execute movement commands on rope
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
                    rope.headMoveLeft();
                }
                if (direction.equals("U")) {
                    rope.headMoveUp();
                }
                if (direction.equals("R")) {
                    rope.headMoveRight();
                }
                if (direction.equals("D")) {
                    rope.headMoveDown();
                }
            }
        }


//        rope.printPositionsVisited();
//        System.out.println("numberOfPositionsVisited = " + rope.numPositionsVisited());
        ///////////////////////// JUST FOR FUN ////////////////////////////
        //////////////////////// SEE TRAVERSALPATH.TXT ///////////////////
//        rope.printPositionChangesVisual();
        //////////////////////// SEE TRAVERSALPATH.TXT ///////////////////
        /////////////////////////// JUST FOR FUN //////////////////////////

    }
}