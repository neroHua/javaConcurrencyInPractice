package chapter16.case03;

import common.ThreadSafe;

@ThreadSafe
public class SafeLazyInitialization {

    private static Resource resource;

    public synchronized static Resource getInstance() {
        if (resource == null) {
            resource = new Resource();
        }

        return resource;
    }

    private static class Resource {

    }

}
