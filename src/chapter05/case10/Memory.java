package chapter05.case10;

import common.GuardedBy;

import java.math.BigInteger;
import java.util.HashMap;
import java.util.Map;

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

        @GuardedBy("this")
        private final Map<A, V> cache = new HashMap<A, V>();
        private final Computable<A, V> c;

        public Memorizer(Computable<A, V> c) {
            this.c = c;
        }

        public synchronized V compute(A arg) throws InterruptedException {
            V result = cache.get(arg);
            if (result == null) {
                result = c.compute(arg);
                cache.put(arg, result);
            }
            return result;
        }

    }

}
