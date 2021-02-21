package chapter05.case07;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.FutureTask;

public class Preloader {

    private final FutureTask<ProductInfo> future = new FutureTask<ProductInfo>(new Callable<ProductInfo>() {

        @Override
        public ProductInfo call() {
            return loadProductInfo();
        }

        private ProductInfo loadProductInfo() {
            // do something need a lot time
            return null;
        }

    });

    private final Thread thread = new Thread(future);

    public void start() {
        thread.start();
    }

    public ProductInfo get() throws InterruptedException, ExecutionException {
        try {
            return future.get();
        } catch (ExecutionException e) {
            throw e;
        }
    }

    private class ProductInfo {
    }

}
