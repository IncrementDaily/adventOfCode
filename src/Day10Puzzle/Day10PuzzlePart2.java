package Day10Puzzle;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;

public class Day10PuzzlePart2 {
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
            reader.close();
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

    public static class Communicator {
        LinkedList<Cycle> cpuInstructionChain = new LinkedList<>();
        int xRegisterValue;
        int cycleNumber;
        int signalStrengthSum;

        Communicator(int inputDataLineCount){
            this.xRegisterValue = 1;
            this.cycleNumber = 0;
            this.signalStrengthSum = 0;
            for (int i = 0; i < (inputDataLineCount*2)+1; i++) {
                cpuInstructionChain.add(new Cycle());
            }
        }

        public LinkedList<Cycle> cycleChain() {
            return cpuInstructionChain;
        }
        public void trimCIChain(){
            Cycle lastCycle = cpuInstructionChain.getLast();
            while (lastCycle.isEmpty()){
                cpuInstructionChain.pollLast();
                lastCycle = cpuInstructionChain.getLast();
            }
            Cycle endCycle = new Cycle();
            cpuInstructionChain.addLast(endCycle);
        }
        public void executeCycleChain(){
            for (Cycle cycle : cpuInstructionChain){
                cycle.execute(this);
            }
            cpuInstructionChain.clear();
        }
        public void drawCycleChain(){
            String litPixel = "@ ";
            String darkPixel = "  ";
            int positionOfPixelDrawer = 0;
            int addxValue = 0;
            Sprite sprite = new Sprite();
            Cycle.Instruction currentInstruction = null;

            for (Cycle cycle : cpuInstructionChain){
                // Check if current cycle contains an instruction to move the sprite
                currentInstruction = cycle.instructionBatch.peek();
                if (currentInstruction != null && currentInstruction.isAddXStage2()){
                    addxValue = currentInstruction.getAddxValue();
                }

                // sprite.draw returns null or cycleNumber
                // Null --> currentPixel dark ; cycleNumber --> currentPixel lit
                Integer currentPixel = sprite.draw(positionOfPixelDrawer);
                if (currentPixel != null) {
                    System.out.print(litPixel);
                }else{
                    System.out.print(darkPixel);
                }

                // If currentInstruction should move the sprite, move it
                if (currentInstruction != null && currentInstruction.isAddXStage2()){
                    sprite.move(addxValue);
                }

                // Adjust positionOfPixelDrawer
                if (positionOfPixelDrawer == 39){
                    System.out.println();
                    positionOfPixelDrawer = 0;
                    continue;
                }
                positionOfPixelDrawer++;

            }
        }

        public boolean isSigStrengthCycle(){
            return (cycleNumber == 20 || cycleNumber%40 == 20);
        }
        public void increaseSignalStrengthSum(){
            signalStrengthSum += cycleNumber * xRegisterValue;
        }
        public int getSignalStrengthSum() {
            return signalStrengthSum;
        }
    }
    public static class Cycle {
        PriorityQueue<Instruction> instructionBatch = new PriorityQueue<>();
        public static class Instruction implements Comparable { // Internal Instruction Class
            private boolean isNoop = false;
            private boolean isAddXStage1 = false;
            private boolean isAddXStage2 = false;
            private int addxValue = 0;

            Instruction(String name) {
                if (name.equals("noop")) this.isNoop = true;
            }
            Instruction(String name, int addxValue) {
                if (name.equals("addx")) this.isAddXStage1 = true;
                if (name.equals("addxStage2")) this.isAddXStage2 = true;
                this.addxValue = addxValue;
            }

            public boolean isNoop() {
                return isNoop;
            }
            public boolean isAddXStage1() {
                return isAddXStage1;
            }
            public boolean isAddXStage2() {
                return isAddXStage2;
            }
            public int getAddxValue(){
                return addxValue;
            }
            public String toString(){
                StringBuilder sb = new StringBuilder();
                if (isNoop) sb.append("noop");
                if (isAddXStage1) {sb.append("addx-st1").append("(").append(addxValue).append(")");}
                if (isAddXStage2) {sb.append("addx-st2").append("(").append(addxValue).append(")");}
                return sb.toString();
            }
            @Override
            public int compareTo(Object o) {
                return 0;
            }
        } // Internal Class Instruction

        public void addInstruction(Instruction newInstruction){
            instructionBatch.add(newInstruction);
        }
        public void execute(Communicator comm){
            // Enter new cycle and manage signal strength
            comm.cycleNumber++;
            if (comm.isSigStrengthCycle()) comm.increaseSignalStrengthSum();

            // Execute all instructions in this cycle's instructionBatch
            Instruction currentInstruction = null;
            while (!instructionBatch.isEmpty()) {
                currentInstruction = instructionBatch.poll();

                // Execute instruction
                if (currentInstruction.isNoop()){
                    continue;
                }
                if (currentInstruction.isAddXStage1()){
                    continue;
                }
                if (currentInstruction.isAddXStage2()){
                    comm.xRegisterValue += currentInstruction.getAddxValue();
                }
            }
        }
        public boolean isEmpty(){
            return instructionBatch.isEmpty();
        }
        public String toString(){
            return instructionBatch.toString();
        }
    }
    public static class Sprite{
        private TreeSet<Integer> position;

        Sprite(){
            position = new TreeSet<>();
            position.add(0);
            position.add(1);
            position.add(2);
        }

        public void move(int addxValue){
            int newLeftPixel = position.pollFirst()+addxValue;
            int newMiddlePixel = position.pollFirst()+addxValue;
            int newRightPixel = position.pollFirst()+addxValue;
            position.add(newLeftPixel);
            position.add(newMiddlePixel);
            position.add((newRightPixel));
        }

        // Returns cycleNumber (will be drawn) or null (will not be drawn)
        public Integer draw(int cycleNumber){
            Integer litPixel = null;
            if (position.contains(cycleNumber)){
                litPixel = cycleNumber;
            }
            return litPixel;
        }

        public String toString(){
            return position.toString();
        }

    }

    public static void main(String[] args) {
        // Initialize input
        String dataFilePath = "src/Day10Puzzle/Day10PuzzleData.txt";
        String testDataFilePath = "src/Day10Puzzle/TestData.txt";
        BufferedReader dataFileReader = getDataFileReader(dataFilePath);
        int dataFileLineCount = getDataFileLineCount(dataFilePath);

        // Initialize Communicator
        Communicator communicator = new Communicator(dataFileLineCount);
        ListIterator<Cycle> cycleChainTracker = communicator.cycleChain().listIterator();

        // Populate communicator cycleChain with Instructions
        String line;
        final String noopCommand = "noop";
        final String addxSt1Command = "addx";
        final String addxSt2Command = "addxStage2";
        int addxValue = 0;
        Cycle currentCycle = cycleChainTracker.next();
        Cycle nextCycle = null;
        while ((line = readDataLine(dataFileReader)) != null) {
            // Initialize command script
            String[] commandScript = line.split("\\s+");

            // Interpret command script
            String command = commandScript[0]; // Either addx or noop
            if (commandScript.length == 2) addxValue = Integer.parseInt(commandScript[1]); // Avoid iOOB exception

            // Execute command script
            // Noop
            if (command.equals(noopCommand)) {
                currentCycle.addInstruction(new Cycle.Instruction(noopCommand));
            }
            // Addx
            if (command.equals(addxSt1Command)) {
                // Add addxStage1 to current cycle & addxStage2 to next cycle
                currentCycle.addInstruction(new Cycle.Instruction(addxSt1Command,addxValue));
                nextCycle = cycleChainTracker.next();
                nextCycle.addInstruction(new Cycle.Instruction(addxSt2Command, addxValue));
            }
            if (cycleChainTracker.hasNext()) currentCycle = cycleChainTracker.next();
        }
        // Remove all extraneous cycles (one empty cycle remains at tail of LinkedList<Cycle>)
        communicator.trimCIChain();

        communicator.drawCycleChain();


    }
}


//    Sprite sprite = new Sprite();
//        sprite.move(10);
//        System.out.println(sprite);
//        sprite.move(-5);
//        System.out.println(sprite);
//        sprite.move(-3);
//        System.out.println(sprite);
//        sprite.move(13);
//        System.out.println(sprite);
