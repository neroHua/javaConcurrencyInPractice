package blog.case04;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.*;

public class ExecutorServiceUse {

    public final ExecutorService executorService = Executors.newFixedThreadPool(10);

    public void testRunnable01() {
        Runnable runnable = new Runnable() {

            @Override
            public void run() {
                System.out.println("execute runnable01");
            }

        };

        executorService.execute(runnable);
    }

    public void testRunnable02() throws ExecutionException, InterruptedException {
        Runnable runnable = new Runnable() {

            @Override
            public void run() {
                System.out.println("submit runnable02");
            }

        };

        Future<?> future = executorService.submit(runnable);

        System.out.println(future.isDone());
        System.out.println(future.get());
        System.out.println(future.isDone());
    }

    public void testRunnable03() throws ExecutionException, InterruptedException {
        Runnable runnable = new Runnable() {

            @Override
            public void run() {
                System.out.println("submit runnable03");
            }

        };

        Future<Integer> future = executorService.submit(runnable, new Integer(1));

        System.out.println(future.isDone());
        System.out.println(future.get());
        System.out.println(future.isDone());
    }

    public void testRunnableList01() {
        List<Runnable> runnableList = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            Runnable runnable = new Runnable() {

                @Override
                public void run() {
                    System.out.println("execute runnable list 01");
                }

            };

            runnableList.add(runnable);
        }

        for (int i = 0; i < 10; i++) {
            executorService.execute(runnableList.get(i));
        }
    }

    public void testRunnableList02() {
        List<Runnable> runnableList = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            Runnable runnable = new Runnable() {

                @Override
                public void run() {
                    System.out.println("execute runnable list 02");
                }

            };

            runnableList.add(runnable);
        }

        for (int i = 0; i < 10; i++) {
            executorService.submit(runnableList.get(i));
        }
    }

    public void testRunnableList03() throws ExecutionException, InterruptedException {
        List<Runnable> runnableList = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            Runnable runnable = new Runnable() {

                @Override
                public void run() {
                    System.out.println("execute runnable list 03");
                }

            };

            runnableList.add(runnable);
        }

        List<Future<Object>> futureList = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            Future<Object> future = executorService.submit(runnableList.get(i), new Object());
            futureList.add(future);
        }

        for (int i = 0; i < 10; i++) {
            System.out.println(futureList.get(i).get());
        }
    }

    public void testCallable() throws ExecutionException, InterruptedException {
        Callable<Integer> callable = new Callable<Integer>() {

            @Override
            public Integer call() {
                System.out.println("execute callable");
                return 1;
            }

        };

        Future<Integer> future = executorService.submit(callable);

        System.out.println(future.isDone());
        System.out.println(future.get());
        System.out.println(future.isDone());
    }

    public void testCallableList01() throws ExecutionException, InterruptedException {
        List<Callable> callableList = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            Callable<Integer> callable = new Callable<Integer>() {

                @Override
                public Integer call() {
                    return 1;
                }

            };

            callableList.add(callable);
        }

        List<Future<Integer>> futureList = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            Future<Integer> future = executorService.submit(callableList.get(i));
            futureList.add(future);
        }

        for (int i = 0; i < 10; i++) {
            Future<Integer> future = futureList.get(i);

            System.out.println(future.isDone());
            System.out.println(future.get());
            System.out.println(future.isDone());
        }
    }

    public void testCallableList02() throws ExecutionException, InterruptedException {
        List<Callable> callableList = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            Callable<Integer> callable = new Callable<Integer>() {

                @Override
                public Integer call() {
                    return 1;
                }

            };

            callableList.add(callable);
        }

        List<Future<Integer>> futureList = executorService.invokeAll(Collections.unmodifiableList(callableList));

        for (int i = 0; i < 10; i++) {
            Future<Integer> future = futureList.get(i);

            System.out.println(future.isDone());
            System.out.println(future.get());
            System.out.println(future.isDone());
        }
    }

    public void testCallableList03() throws ExecutionException, InterruptedException {
        List<Callable> callableList = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            Callable<Integer> callable = new Callable<Integer>() {

                @Override
                public Integer call() {
                    return 1;
                }

            };

            callableList.add(callable);
        }

        List<Future<Integer>> futureList = executorService.invokeAll(Collections.unmodifiableList(callableList), 1000, TimeUnit.MILLISECONDS);

        for (int i = 0; i < 10; i++) {
            Future<Integer> future = futureList.get(i);

            System.out.println(future.isDone());
            System.out.println(future.get());
            System.out.println(future.isDone());
        }
    }

    public static void main(String[] args) throws ExecutionException, InterruptedException {
        ExecutorServiceUse executorServiceUse = new ExecutorServiceUse();
        executorServiceUse.testRunnable01();
        executorServiceUse.testRunnable02();
        executorServiceUse.testRunnable03();
        executorServiceUse.testRunnableList01();
        executorServiceUse.testRunnableList02();
        executorServiceUse.testRunnableList03();
        executorServiceUse.testCallable();
        executorServiceUse.testCallableList01();
        executorServiceUse.testCallableList02();
        executorServiceUse.testCallableList03();
    }

}
