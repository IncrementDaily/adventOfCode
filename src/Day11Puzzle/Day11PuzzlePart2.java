package Day11Puzzle;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.math.BigInteger;
import java.util.*;

public class Day11PuzzlePart2 {
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

    public static class Item {
        private BigInteger worryLevel = BigInteger.valueOf(0);

        Item(BigInteger worryLevel){
            this.worryLevel = worryLevel;
        }
        public void setWorryLevel(BigInteger worryLevel) {
            this.worryLevel = worryLevel;
        }
        public BigInteger worryLevel() {
            return worryLevel;
        }
        public String toString(){
//            StringBuilder item = new StringBuilder();
//            return item.append("(Item: ").append(worryLevel).append(")").toString();
            return String.valueOf(worryLevel);
        }
    }
    public static class Monkey{
        private Integer monkeyNumber;
        private LinkedList<Item> itemList;
        //        private LinkedList<Item> itemListNextRound = new LinkedList<>();
//        private Function<Item, Item> inspectMethod;
        private BigInteger worryIncreaser;
        private BigInteger worryMultiplier;
        private boolean isReckless;
        private boolean isExtremelyReckless;
        //        private Function<Item, Item> watchOnlookerMethod;
        private Integer watchModulus;
        private Monkey trueThrowTo;
        private Monkey falseThrowTo;
        private Integer inspectCounter = 0;

        // Builder-based Constructor
        private Monkey(Builder builder){
            monkeyNumber = builder.monkeyNumber;
            itemList = builder.itemList;
//            inspectMethod = builder.inspectMethod;
            worryIncreaser = builder.worryIncreaser;
            worryMultiplier = builder.worryMultiplier;
            isReckless = builder.isReckless;
            isExtremelyReckless = builder.isExtremelyReckless;
//            watchOnlookerMethod = builder.watchOnlookerMethod;
            watchModulus = builder.watchModulus;
            trueThrowTo = builder.trueThrowTo;
            falseThrowTo = builder.falseThrowTo;
        }


        public static class Builder{
            //Required Parameters
            private final Integer monkeyNumber;

            //Optional Parameters
            private LinkedList<Item> itemList;
            //            private Function<Item, Item> inspectMethod;
            private boolean isReckless;
            private boolean isExtremelyReckless;
            private BigInteger worryIncreaser;
            private BigInteger worryMultiplier;
            //            private Function<Item, Item> watchOnlookerMethod;
            private Integer watchModulus;
            private Monkey trueThrowTo;
            private Monkey falseThrowTo;

            // Builder Constructor
            public Builder(int monkeyNumber){
                this.monkeyNumber = monkeyNumber;
            }

            // Builder-Setters
//          public Builder inspectMethod(Function<Item, Item> inspectMethod)
//              {this.inspectMethod = inspectMethod;               return this;}
            public Builder worryIncreaser(BigInteger worryIncreaser)
            {this.worryIncreaser = worryIncreaser;             return this;}
            public Builder worryMultiplier(BigInteger worryMultiplier)
            {this.worryMultiplier = worryMultiplier;           return this;}
            public Builder isReckless(boolean isReckless)
            {this.isReckless = isReckless;                     return this;}
            public Builder isExtremelyReckless(boolean isExtremelyReckless)
            {this.isExtremelyReckless = isExtremelyReckless;   return this;}
            //          public Builder watchOnlookerMethod(Function<Item, Item> watchOnlookerMethod)
//              {this.watchOnlookerMethod = watchOnlookerMethod;   return this;}
            public Builder watchModulus(int watchModulus)
            {this.watchModulus = watchModulus;                 return this;}
            public Builder trueThrowTo(Monkey trueThrowTo)
            {this.trueThrowTo = trueThrowTo;                   return this;}
            public Builder falseThrowTo(Monkey falseThrowTo)
            {this.falseThrowTo = falseThrowTo;                 return this;}
            public Builder itemList(LinkedList<Item> itemList)
            {this.itemList = new LinkedList<Item>(itemList);       return this;}

            // Build()
            public Monkey build(){
                return new Monkey(this);
            }

            public boolean isReady(){
                return (monkeyNumber != null
                        && itemList != null
                        && (worryIncreaser != null || worryMultiplier != null || isExtremelyReckless)
                        && watchModulus != null);
            }
        }

        // MONKEY ITEM METHODS
        // (0) Monkey will inspect all items in its itemList once, then throw them to a different Monkey
        // (1) Monkey inspects item causing item's worryLevel to increase
        // (2) Monkey gets bored by item and hasn't damaged it, causing item's worryLevel to decrease
        // (3) Monkey watches onlooker and, based on Monkey's observation/item's worryLevel,
        //     decides who to throw item to next
        // (4) Throw the item

        // RULES:
        // (1) When a monkey throws an item to another monkey, the item goes on the end of the recipient monkey's list
        // (2) If a monkey is holding no items at the start of its turn, its turn ends.
        // (3) Monkey 0 goes first, then monkey 1, and so on until each monkey has had one turn.
        //     The process of each monkey taking a single turn is called a round.
        // (4) When populating monkeys itemList initially, use addLast()
        // (5) When monkey is inspecting items, use a while (!itemList.isEmpty()) --> poll()
        // (6) Chasing all of the monkeys at once is impossible; you're going to have to focus on the two most active monkeys
        //     if you want any hope of getting your stuff back. Count the total number of times each monkey inspects items
        //     over 20 rounds:

        public void takeATurn(int productOfAllWatchModuli){
            while (!itemList.isEmpty()) {
                Item currentItem = itemList.peekFirst();
                inspect(currentItem);
//                getBoredBy(currentItem);
                watchOnlookerWorry(currentItem, productOfAllWatchModuli);
            }
        }

        // (1)
        public void inspect(Item item){
            if (isExtremelyReckless) {
                extremelyRecklesslyInspect(item);
            }else if(isReckless) {
                recklesslyInspect(item);
            }else{
                gentlyInspect(item);
            }
        }
        private void extremelyRecklesslyInspect(Item item){
            BigInteger currentWorryLevel = item.worryLevel();
            BigInteger finalWorryLevel = currentWorryLevel.multiply(currentWorryLevel);
            item.setWorryLevel(finalWorryLevel);
            // Increment total number of times Monkey has inspected an Item
            inspectCounter++;
        }
        private void recklesslyInspect(Item item){
            BigInteger currentWorryLevel = item.worryLevel();
            BigInteger finalWorryLevel = currentWorryLevel.multiply(worryMultiplier);
            item.setWorryLevel(finalWorryLevel);
            // Increment total number of times Monkey has inspected an Item
            inspectCounter++;
        }
        private void gentlyInspect(Item item){
            BigInteger currentWorryLevel = item.worryLevel();
            BigInteger finalWorryLevel = currentWorryLevel.add(worryIncreaser);
            item.setWorryLevel(finalWorryLevel);
            // Increment total number of times Monkey has inspected an Item
            inspectCounter++;
        }
        // (2)
        public void getBoredBy(Item item){
            BigInteger currentWorryLevel = item.worryLevel();
            BigInteger finalWorryLevel = currentWorryLevel.divide(BigInteger.valueOf(3));
            item.setWorryLevel(finalWorryLevel);
        }
        // (3)
        public void watchOnlookerWorry(Item item, int productOfAllWatchModuli){
            // Watch onlooker... Monkey sees something! --> Throw to trueThrowTo
            // NOTE: To manage worryLevel using modular arithmetic it is necessary to understand that:
            //
            // For any set of integers n, p and d: if p mod d = 0, then
            //  (n mod p) mod d = n mod d
            //
            // In this case: d is the divisor (different for each monkey), n is the input number (worry level), and n mod p
            // is the reduced number. What the above observation shows is that if you take p to be a common multiple for
            // every possible d, then you may safely replace n by n mod p without messing up any future divisibility test.
            //
            // In this case, since all the monkey divisors are different primes, the minimum number p that satisfies these
            // conditions is simply the product of all those primes. If the monkey divisors weren't primes or if they had some
            // divisors in common, then the "least common multiple" would be a lot smaller.

            if (item.worryLevel().mod(BigInteger.valueOf(watchModulus)).equals(BigInteger.ZERO)){
                throwItem(item, trueThrowTo);
                item.setWorryLevel(item.worryLevel().mod(BigInteger.valueOf(productOfAllWatchModuli)));
            }
            // Watch onlooker... nothing catches Monkey's eye --> Throw to falseThrowTo
            else{
                throwItem(item, falseThrowTo);
            }

        }
        // (4)
        private void throwItem(Item item, Monkey catcherMonkey){
            // Add item to the back of the catcher Monkey's itemList
            catcherMonkey.itemList.addLast(item);
            // Remove item from thrower Monkey's itemList
            itemList.remove(item);
        }
        public Monkey getTrueThrowTo() {
            return trueThrowTo;
        }
        public void setTrueThrowTo(Monkey trueThrowTo) {
            this.trueThrowTo = trueThrowTo;
        }
        public Monkey getFalseThrowTo() {
            return falseThrowTo;
        }
        public void setFalseThrowTo(Monkey falseThrowTo) {
            this.falseThrowTo = falseThrowTo;
        }
        public BigInteger worryIncreaser() {
            return worryIncreaser;
        }
        public void setWorryIncreaser(BigInteger worryIncreaser) {
            this.worryIncreaser = worryIncreaser;
        }
        public BigInteger worryMultiplier() {
            return worryMultiplier;
        }
        public void setWorryMultiplier(BigInteger worryMultiplier) {
            this.worryMultiplier = worryMultiplier;
        }
        public int monkeyNumber() {
            return monkeyNumber;
        }
        public int getInspectCounter() {
            return inspectCounter;
        }
        public int getWatchModulus() {
            return watchModulus;
        }
        public String toString(){
            String name =  "Monkey " + monkeyNumber;
            String itemList = this.itemList.toString();
            return name + itemList;
        }
    }

    public static void round(List<Monkey> troupe, int productOfAllWatchModuli){
        for (Monkey monkey : troupe){
            monkey.takeATurn(productOfAllWatchModuli);
        }
    }

    public static void main(String[] args) {
        // Initialize input
        String dataFilePath = "src/Day11Puzzle/Day11PuzzleData.txt";
        String testDataFilePath = "src/Day11Puzzle/TestData.txt";
        BufferedReader dataFileReaderFIRST_LOOP = getDataFileReader(dataFilePath);

        // Populate List of Monkeys (troupe) made of Monkeys with their starting items
        // Initialize Necessary Pointers for Loop
        String line;
        int lineNumber = 1;
        Monkey.Builder currentMonkey = null;
        int monkeyNumber;
        BigInteger itemWorryLevel;
        LinkedList<Item> monkeyItemList = new LinkedList<>();
        boolean isReckless;
        BigInteger worryIncreaser;
        BigInteger worryMultiplier;
        int watchModulus;
        HashMap<Integer, Monkey> intToMonkey = new HashMap<>();
        final String MONKEY_SIGNIFIER = "Monkey";
        final String ITEM_LIST_SIGNIFIER = "items";
        final String INSPECT_SIGNIFIER = "Operation";
        final String GENTLE_INSPECT = "+";
        final String RECKLESS_INSPECT = "*";
        final String EXTREMELY_RECKLESS_INSPECT = "old";
        final String WATCH_ONLOOKER_SIGNIFIER = "Test";
        final String TRUE_THROW_TO = "true";
        final String FALSE_THROW_TO = "false";
        // FIRST LOOP:
        // Build Monkeys and encode their inspect() and watchOnlookerWorry() behavior
        while ((line = readDataLine(dataFileReaderFIRST_LOOP)) != null) {
            assert lineNumber == lineNumber;
            // .trim() call makes regex split cleaner
            line = line.trim();

            // Initialize command script
            String[] commandScript = line.split("\\s*,\\s*|\\s+|\\s*:\\s*"); // regex pattern removes/separates commas, whitespace, and colons
            List<String> commandScriptList = List.of(commandScript);
            ListIterator<String> commandScriptListIter = commandScriptList.listIterator();

//          // Execute commandScript
            // (0) Exclude all blank lines that don't need interpreting
            if (commandScript.length == 1) {
                lineNumber++;
                continue;
            }

            // (1) If commandScript found new Monkey -- init Monkey Builder
            if (commandScript[0].equals(MONKEY_SIGNIFIER)){
                monkeyNumber = Integer.parseInt(commandScript[1]);
                currentMonkey = new Monkey.Builder(monkeyNumber);
                lineNumber++;
                continue;
            }

            // (2) If commandScript describes itemList -- continue build currentMonkey w/ items
            // (a) loop through items to populate monkeyItemList
            // (b) continue building Monkey with monkeyItemList
            if (commandScript[1].equals(ITEM_LIST_SIGNIFIER)) {
                // (a)
                while (commandScriptListIter.hasNext()) {
                    String element = commandScriptListIter.next();
                    try {
                        itemWorryLevel = BigInteger.valueOf(Integer.parseInt((element)));
                    } catch (Exception ignored) {
                        continue;
                    }
                    Item item = new Item(itemWorryLevel);
                    // Make sure items preserve correct order
                    monkeyItemList.addLast(item);
                }
                // (b)
                currentMonkey.itemList(monkeyItemList);
                lineNumber++;
                continue;
            }

            // (3) If commandScript describes Inspect() behavior -- continue build currentMonkey w/ inspect()
            // (a) Determine if monkey's inspect() behavior is gentle (+), reckless (*), or extremelyReckless (* old)
            // (b) finish inspect() build
            if (commandScript[0].equals(INSPECT_SIGNIFIER)){
                BigInteger worryFactor;
                // Gentle Monkey
                if (commandScript[4].equals(GENTLE_INSPECT)){
                    currentMonkey.isReckless(false);
                    worryFactor = BigInteger.valueOf(Integer.parseInt(commandScript[5]));
                    currentMonkey.worryIncreaser(worryFactor);
                }
                // Reckless Monkey
                if (commandScript[4].equals(RECKLESS_INSPECT)
                        && !commandScript[5].equals(EXTREMELY_RECKLESS_INSPECT)){
                    currentMonkey.isReckless(true);
                    worryFactor = BigInteger.valueOf(Integer.parseInt((commandScript[5])));
                    currentMonkey.worryMultiplier(worryFactor);
                }
                // Extremely Reckless Monkey
                if (commandScript[5].equals(EXTREMELY_RECKLESS_INSPECT)){
                    currentMonkey.isExtremelyReckless(true);
                }
                lineNumber++;
                continue;
            }

            // (4) If commandScript describes watchOnlooker() behavior -- continue build currentMonkey w/ watchOnlooker()
            if (commandScript[0].equals(WATCH_ONLOOKER_SIGNIFIER)){
                watchModulus = Integer.parseInt(commandScript[3]);
                currentMonkey.watchModulus(watchModulus);
                lineNumber++;
                continue;
            }

            // (5) Check if currentMonkey is ready to be built and then move on to next Monkey
            // (a) build currentMonkey then add to HashMap for referencing later
            // (b) refresh all pointers as necessary
            if (currentMonkey != null && currentMonkey.isReady()){
                //(a)
                Monkey newMonkey = currentMonkey.build();
                intToMonkey.put(newMonkey.monkeyNumber(), newMonkey);
                // (b)
                currentMonkey = null;
                monkeyItemList.clear();
                lineNumber++;
                continue;
            }
            lineNumber++;
        }

        Monkey currentThrower = null;
        Monkey targetMonkey = null;
        BufferedReader dataFileReaderSECOND_LOOP = getDataFileReader(dataFilePath);
        // SECOND LOOP:
        // Set Monkeys' trueThrowTo targets and falseThrowTo targets
        while ((line = readDataLine(dataFileReaderSECOND_LOOP)) != null) {
            assert lineNumber == lineNumber;
            // .trim() call makes regex split cleaner
            line = line.trim();

            // Initialize command script
            String[] commandScript = line.split("\\s*,\\s*|\\s+|\\s*:\\s*"); // regex pattern removes/separates commas, whitespace, and colons
            List<String> commandScriptList = List.of(commandScript);
            ListIterator<String> commandScriptListIter = commandScriptList.listIterator();

            // Execute commandScript
            // (0) Exclude all blank lines that don't need interpreting
            if (commandScript.length == 1) {
                lineNumber++;
                continue;
            }

            // (1) Find the currentThrower
            if (commandScript[0].equals(MONKEY_SIGNIFIER)){
                monkeyNumber = Integer.parseInt(commandScript[1]);
                currentThrower = intToMonkey.get(monkeyNumber);
                lineNumber++;
                continue;
            }

            // (2) If commandScript describes trueThrowTo() behavior -- assign currentThrower's trueThrowTo()
            if (commandScript[1].equals(TRUE_THROW_TO)){
                monkeyNumber = Integer.parseInt(commandScript[5]);
                targetMonkey = intToMonkey.get(monkeyNumber);
                currentThrower.setTrueThrowTo(targetMonkey);
                lineNumber++;
                continue;
            }

            // (3) If commandScript describes trueThrowTo() behavior -- assign currentThrower's trueThrowTo()
            if (commandScript[1].equals(FALSE_THROW_TO)){
                monkeyNumber = Integer.parseInt(commandScript[5]);
                targetMonkey = intToMonkey.get(monkeyNumber);
                currentThrower.setFalseThrowTo(targetMonkey);
                lineNumber++;
                continue;
            }
        }

        // Execute 10000 rounds of monkey business
        List<Monkey> troupe = new ArrayList<Monkey>(intToMonkey.values());
        int productOfAllWatchModuli = 1;
        for (Monkey monkey : troupe){
            productOfAllWatchModuli *= monkey.getWatchModulus();
        }
        for (int i=0; i<10000; i++){
            round(troupe, productOfAllWatchModuli);
        }

        for (Monkey monkey : troupe){
            System.out.println("monkey.getInspectCounter() = " + monkey.getInspectCounter());
        }

//      Find the two busiest monkeys
        troupe.sort(new Comparator<Monkey>() {
            @Override
            public int compare(Monkey o1, Monkey o2) {
                return o2.inspectCounter.compareTo(o1.inspectCounter);
            }
        });
        Monkey busiestMonkey = troupe.get(0);
        Monkey secondBusiestMonkey = troupe.get(1);

        System.out.println(troupe);

        // Calculate the amount of monkeyBusiness
        BigInteger monkeyBusiness = BigInteger.valueOf(busiestMonkey.getInspectCounter()).multiply(BigInteger.valueOf(secondBusiestMonkey.getInspectCounter()));
        System.out.println(monkeyBusiness);


    }
}

