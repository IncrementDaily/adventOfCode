package MessingAround;

import java.io.BufferedReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;
import java.util.stream.Stream;

public class StreamPlay {
    public static void main(String[] args) {

    List<Integer> list = Arrays.asList(1,2,3,1,2);
    System.out.println(list.stream().distinct().count());

//    BufferedReader reader = new BufferedReader();


//        List<ArrayList<UUID>> listOfListsOfIDs = Arrays.asList(
//                new ArrayList(Arrays.asList(UUID.randomUUID(), UUID.randomUUID(), UUID.randomUUID())),
//                new ArrayList(Arrays.asList(UUID.randomUUID(), UUID.randomUUID(), UUID.randomUUID())),
//                new ArrayList(Arrays.asList(UUID.randomUUID(), UUID.randomUUID(), UUID.randomUUID())
//                ));
//
//        Stream<ArrayList<UUID>> streamOfArrayLists = listOfListsOfIDs.stream();
//        Stream<UUID> streamOfUUID = streamOfArrayLists.flatMap(list -> list.stream());


    }
}
