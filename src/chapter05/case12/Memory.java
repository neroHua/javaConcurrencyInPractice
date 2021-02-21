package chapter05.case12;

import java.math.BigInteger;
import java.util.Map;
import java.util.concurrent.*;

public class Memory {

    public interface Computable<A, V> {

        V compute(A arg) throws InterruptedException;

    }

    public class ExpensiveFunction implements Computable<String, BigInteger> {

        public BigInteger compute(String arg) {
            return new BigInteger(arg);
        }

    }

    public class Memorizer<A, V> implements Computable<A, V> {

        private final Map<A, Future<V>> cache = new ConcurrentHashMap<A, Future<V>>();
        private final Computable<A, V> c;

        public Memorizer(Computable<A, V> c) {
            this.c = c;
        }

        public V compute(final A arg) throws InterruptedException {
            Future<V> f = cache.get(arg);
            if (f == null) {
                Callable<V> eval = new Callable<V>() {

                    public V call() throws InterruptedException {
                        return c.compute(arg);
                    }

                };
                FutureTask<V> ft = new FutureTask<V>(eval);
                f = ft;
                cache.put(arg, ft);
                ft.run();
            }
            try {
                return f.get();
            } catch (ExecutionException e) {
                throw new RuntimeException(e);
            }
        }

    }

}
