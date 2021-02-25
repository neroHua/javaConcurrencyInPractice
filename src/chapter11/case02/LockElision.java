package chapter11.case02;

import java.util.List;
import java.util.Vector;

public class LockElision {

    public String getStoogeNames() {
        List<String> stooges = new Vector<String>();
        stooges.add("Moe");
        stooges.add("Larry");
        stooges.add("Curly");
        return stooges.toString();
    }

}
