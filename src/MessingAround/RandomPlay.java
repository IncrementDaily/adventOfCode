package MessingAround;

import java.util.*;

public class RandomPlay {
    public static void main(String[] args) {

//        List<String> list1 = Arrays.asList("apple", "banana", "cherry", "orange", "kiwi", "DUMB");
//        List<String> list2 = Arrays.asList("apple", "banana", "cherry", "orange", "DUMB", "kiwi");

        // Shuffle these two lists with two sources of randomness at a different seed
//        System.out.println(list1);
//        System.out.println(list2);
//        Collections.shuffle(list1);
//        Collections.shuffle(list2);
//        System.out.println(list1);
//        System.out.println(list2);

        // Shuffle these two lists with two sources of randomness at the same seed
//        System.out.println(list1);
//        System.out.println(list2);
//        Collections.shuffle(list1, new Random(4));
//        Collections.shuffle(list2, new Random(4));
//        System.out.println(list1);
//        System.out.println(list2);


        ///////////////////////////////////////////////////////////////////////


//        // Generate two permutations of 100 random numbers between 0-99.
//        // The lists almost certainly do not share the first 99 numbers in the correct value-position order.
//        // However, the last number in the lists is guaranteed to be the same number.
//
//        // Init lists and sources of randomness
//        List<Integer> list1 = new LinkedList<>();
//        List<Integer> list2 = new LinkedList<>();
//        Random rand1 = new Random();
//        Random rand2 = new Random();
//
//        // Add first 99 numbers pseudorandomly from
//        // sources of randomness with different seeds
//        for (int i = 0; i < 7; i++) {
//            list1.add(rand1.nextInt(100));
//            list2.add(rand2.nextInt(100));
//        }
//
//        // Synchronize the seeds of the two sources of randomness
//        Random rand3 = new Random();
//        int sharedSeed = rand3.nextInt();
//        rand1.setSeed(sharedSeed);
//        rand2.setSeed(sharedSeed);
//
//        // Add last number
//        list1.add(rand1.nextInt(100));
//        list2.add(rand2.nextInt(100));
//
//        // Print lists
//        System.out.println("list1 = " + list1);
//        System.out.println("list2 = " + list2);
//
//        // Check if the lists have any index position OTHER THAN the last number
//        // for which the two numbers in the two lists are the same number
//        System.out.println();
//        System.out.println("Before");
//        int sharedNumber = 0;
//        for (int i = 0; i < list1.size(); i++) {
//            if (list1.get(i).equals(list2.get(i))){
//                sharedNumber = list1.get(i);
//                System.out.println("Shared number is: " + sharedNumber + ". Shared number is at index " + i);
//                System.out.println("List1 at index " + i + " = " + list1.get(i));
//                System.out.println("List2 at index " + i + " = " + list2.get(i));
//            }
//        }
//
//        Collections.rotate(list1, 1);
//        Collections.rotate(list2, -1);
//
//        System.out.println();
//        System.out.println();
//        System.out.println("After");
//
//        for (int i = 0; i < list1.size(); i++) {
//            if (list1.get(i).equals(list2.get(i))){
//                sharedNumber = list1.get(i);
//                System.out.println("Shared number is: " + sharedNumber + ". Shared number is at index " + i);
//                System.out.println("List1 at index " + i + " = " + list1.get(i));
//                System.out.println("List2 at index " + i + " = " + list2.get(i));
//            }
//        }
//


    }
}
