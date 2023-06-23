package MessingAround;

import java.io.*;
import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

public class MessAround{
    public static void writePseudoData(String filepath, int numberOfLines){
        BufferedWriter writer = null;
        try {
            writer = new BufferedWriter(new FileWriter(filepath));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        Random rand = new Random();
        Set<Integer> intSet1 = new HashSet<>();
        Set<Integer> intSet2 = new HashSet<>();
        Set<Integer> intSet3 = new HashSet<>();

        for (int line=0;line<numberOfLines;line++) {
            for (int i = 0; i < 40; i++) {
                intSet1.add(rand.nextInt(100));
                intSet2.add(rand.nextInt(100));
                intSet3.add(rand.nextInt(100));
            }
            intSet1.retainAll(intSet2);
            intSet1.retainAll(intSet3);
            try {
                writer.write(intSet1.toString());
                // If statement prevents an empty line at the tail-end of the data file
                if (line < numberOfLines-1) writer.newLine();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            intSet1.clear();
            intSet2.clear();
            intSet3.clear();
        }
        try {
            writer.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
    private static class TestObject{
        private final UUID id;
        private String name;
        private Integer insanityFactor;

        @Override
        public boolean equals(Object obj) {
            if (!(obj instanceof TestObject)) return false;
            TestObject comparison = (TestObject) obj;
            return (this.getId().equals(comparison.getId()));
        }

        public TestObject() {
            this("DEFAULT");
        }

        public TestObject(String name) {
            Random rand = new Random();
            this.id = UUID.randomUUID();
            this.name = name;
            this.insanityFactor = rand.nextInt(1001);

        }

        public UUID getId() {
            return id;
        }

        public String getName() {
            return name;
        }

        public Integer getInsanityFactor() {
            return insanityFactor;
        }

        private void setInsanityFactor(Integer insanityFactor) {
            this.insanityFactor = insanityFactor;
        }

        private void setName(String name) {
            this.name = name;
        }
    }


    public static void main(String[] args) {




        //////////////////////////////////////////////////////
      /*  List<String> list = Arrays.asList("T","A","N","K","S");

        System.out.println(list);
        Collections.rotate(list,list.size());
        System.out.println(list);*/
        ///////////////////////////////////////


        ///////////////////////////////////////////
//        List<TestObject> list = Arrays.asList(
//                new TestObject("Karim"),
//                new TestObject("Bitch"),
//                new TestObject("Jane Doe")
//        );
//
//        Map<UUID, TestObject> nameToIF = list
//                .stream()
//                .collect
//                (Collectors.toMap(TestObject::getId, Function.identity(), (a, b) -> a));
//
//        System.out.println(nameToIF);
        ////////////////////////////////////////////////


        //////////////////////////
//        writePseudoData("src/MessingAround/Test.txt", 100);
        ////////////////////////

        ////////////////////////////////////////////////////////
        ////////////////////////////////////////////////////
        /*String testHarder = "Test and more tests";
        Stream<String> wordStream = Stream.of(testHarder.split("\\s+"));
        wordStream.forEach(System.out::println);

        String[] test = {"Test", "and", "more", "tests"};

        for (String word : test
             ) {
            System.out.print(word + " ");
        }*/
        ///////////////////////////////////////////////////
        /////////////////////////////////////////////////


        ///////////////////////////////////////////
        /////////////////////////////////////////
        /*File messAround = new File("src\\messAround.txt");

        try {
            BufferedReader reader = new BufferedReader(new FileReader(messAround));
            Stream<String> wordStream = reader.lines().flatMap(line -> Stream.of(line.split("\\s+")));

            Stream<String> lineStream = reader.lines();

            Map<Integer, String> lengthToString = lineStream
                    .flatMap(line -> Stream.of(line.split("\\s+")))
                    .filter(line -> line.startsWith("Print")
                    || line.startsWith("the")
                    || line.startsWith("hidden")
                    || line.startsWith("message!"))
                    .collect(Collectors.toMap(line -> line.toString().length(), line -> line, (a,b) -> a));

            StringBuilder result = new StringBuilder();
            result.append(lengthToString.get(5) + " ")
                    .append(lengthToString.get(3) + " ")
                    .append(lengthToString.get(6) + " ")
                    .append(lengthToString.get(8) + " ");

            System.out.println(result);
        } catch (Exception e) {
            e.printStackTrace();
        }*/
        ///////////////////////////////////
        //////////////////////////////////


//        try {
//            BufferedReader reader = new BufferedReader(new FileReader(elvesDataFile));
//            BufferedReader reader2 = new BufferedReader(new FileReader(messAround));
//
//            HashMap<String, Integer> nameToAge = new HashMap<>();
//            nameToAge.put("Bob", 1);
//            nameToAge.put("Ralph", 2);
//            nameToAge.put("David", 3);
//
//            while (true) {
//                Stream<Map.Entry<String, Integer>> entryStream = nameToAge.entrySet().stream();
//                entryStream.map(entry -> entry.getValue()).forEach(System.out::println);
//                if (Math.random() < 0.01) break;
//            }
//
//
//            List<Double> results = new ArrayList<>();
//            for (int i=0;i<100000;i++) {
//                double roll = Math.random();
//                if (roll > 0 && roll <= 0.00001) results.add(0.001);
//                if (roll > 0.00001 && roll <= 0.00011) results.add(0.01);
//                if (roll > 0.00011 && roll <= 0.00111)  results.add(0.1);
//                if (roll > 0.00111 && roll <= 0.01111) results.add(1.0);
//                if (roll > 0.01111 && roll <= 0.11111) results.add(10.0);
//                if (roll > 0.11111 && roll <= 0.61111) results.add(50.0);
//            }
//
//            System.out.println(results.stream().filter(result -> result == 0.001).count());
//            System.out.println(results.stream().anyMatch(result -> result == 0.001));
//            results.stream().distinct().forEach(System.out::println);
//
//
//
//            reader2.lines()
//            .filter(line -> line.length() != 0)
//            .map(line -> line + " yay!")
//            .forEach(System.out::println);
//
//            int elfSum = 0;
//            ArrayList<Integer> listOfSums = new ArrayList<>();
//
//            String line;
//            while ((line = reader.readLine()) != null) {
//                if (line.length() != 0) {
//                    int item = Integer.parseInt(line);
//                    elfSum += item;
//                }
//                if (line.length() == 0) {
//                    listOfSums.add(elfSum);
//                    elfSum = 0;
//                }
//            }
//            Collections.sort(listOfSums);
//            System.out.println(listOfSums.get(listOfSums.size()-1));
//            reader.close();
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
    }
}
