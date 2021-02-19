package chapter03.case10;

import java.util.HashSet;
import java.util.Set;

public class ThreeStooges {

    private final Set<String> stooges = new HashSet<String>();

    public ThreeStooges() {
        stooges.add("a");
        stooges.add("b");
        stooges.add("c");
    }

    public boolean isStooge(String name) {
        return stooges.contains(name);
    }

}
