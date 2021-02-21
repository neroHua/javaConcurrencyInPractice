package chapter05.case14;

import common.ThreadSafe;

import javax.servlet.Servlet;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import java.math.BigInteger;

@ThreadSafe
public class Factory implements Servlet {

    private final Computable<BigInteger, BigInteger[]> c = new Computable<BigInteger, BigInteger[]>() {

        public BigInteger[] compute(BigInteger arg) {
            return factor(arg);
        }

        private BigInteger[] factor(BigInteger arg) {
            return new BigInteger[0];
        }

    };

    private final Computable<BigInteger, BigInteger[]> cache = new Memorizer<BigInteger, BigInteger[]>(c);

    public void service(ServletRequest req, ServletResponse resp) {
        BigInteger i = extractFromRequest(req);
        encodeIntoResponse(resp, cache.compute(i));
    }

    private void encodeIntoResponse(ServletResponse resp, BigInteger[] compute) {
    }

    private BigInteger extractFromRequest(ServletRequest req) {
        return null;
    }

    private class Computable<T, T1> {
        public T1 compute(T i) {
            return null;
        }
    }

    private class Memorizer<T, T1> extends Computable<BigInteger, BigInteger[]> {

        public Memorizer(Computable<T, T1> c) {
            super();
        }

    }

}
