package chapter16.case05;

import common.ThreadSafe;

@ThreadSafe
public class ResourceFactory {

    private static class ResourceHolder {
        public static Resource resource = new Resource();
    }

    public static Resource getResource() {
        return ResourceHolder.resource ;
    }

    private static class Resource {

    }

}
