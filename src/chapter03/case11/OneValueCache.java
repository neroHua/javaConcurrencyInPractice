package chapter03.case11;

import common.Immutable;

import java.math.BigInteger;
import java.util.Arrays;

@Immutable
public class OneValueCache {

    private final BigInteger lastNumber;
    private final BigInteger[] lastFactors;

    public OneValueCache(BigInteger lastNumber, BigInteger[] lastFactors) {
        this.lastNumber = lastNumber;
        this.lastFactors = Arrays.copyOf(lastFactors, lastFactors.length);
    }

    public BigInteger[] getFactors(BigInteger i) {
        if (null == lastFactors || !lastNumber.equals(i)) {
            return null;
        }
        else {
            return Arrays.copyOf(lastFactors, lastFactors.length);
        }
    }

}
