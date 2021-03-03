package chapter16.case04;

import common.ThreadSafe;

@ThreadSafe
public class EagerInitialization {

    private static Resource resource = new Resource();

    public static Resource getResource() {
        return resource;
    }

    private static class Resource {

    }

}
