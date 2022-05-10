import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class StreamEx {
    public static void main( String[] args ) {
        List<String> myList = Stream.of("Apple", "Banana")
                .map(String::toUpperCase)
                .collect(Collectors.toList());
        System.out.println(myList);
    }
}
