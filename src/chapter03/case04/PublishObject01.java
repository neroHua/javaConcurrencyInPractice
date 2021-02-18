package chapter03.case04;

import java.util.HashSet;
import java.util.Set;

public class PublishObject01 {

    public static Set<Secret> secretSet;

    public void initialize() {
        secretSet = new HashSet<Secret>();
    }

    public class Secret {

    }

}
