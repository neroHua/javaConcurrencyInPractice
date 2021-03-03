package chapter16.case07;

import common.ThreadSafe;

import java.util.HashMap;
import java.util.Map;

@ThreadSafe
public class SafeStates {

    private final Map<String, String> states;

    public SafeStates() {
        states = new HashMap<String, String>();
        states.put("alaska", "AK");
        states.put("alabama", "AL");
        states.put("wyoming", "WY");
    }

    public String getAbbreviation(String s) {
        return states.get(s);
    }

}
