package chapter16.case02;

import common.NotThreadSafe;

@NotThreadSafe
public class UnsafeLazyInitialization {

    private static Resource resource;

    public static Resource getInstance() {
        if (resource == null) {
            // unsafe publication
            resource = new Resource();
        }

        return resource;
    }

    private static class Resource {

    }

}
