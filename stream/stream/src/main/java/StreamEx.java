import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class StreamEx {

    public static void main( String[] args ) {
        // map
        List<String> myList = Stream.of("Apple", "Banana")
                .map(String::toUpperCase)
                .collect(Collectors.toList());
        System.out.println(myList);

        // flatMap
        List<List<String>> myList1 = new ArrayList<>();
        myList1.add(Arrays.asList("Apple", "Banana"));
        myList1.add(Arrays.asList("Mango"));
        System.out.println(myList1);
        List<String> result = myList1.stream()
                .flatMap(Collection::stream)
                .collect(Collectors.toList());
        System.out.println(result);
    }
}
