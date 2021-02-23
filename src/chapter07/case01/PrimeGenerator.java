package chapter07.case01;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;

import static java.util.concurrent.TimeUnit.SECONDS;

public class PrimeGenerator implements Runnable {

	private final List<BigInteger> primeList = new ArrayList<BigInteger>();
	private volatile boolean cancelled = false;
	
	@Override
	public void run() {
		while (!cancelled) {
			BigInteger prime = generatorPrim();
			synchronized (primeList) {
				primeList.add(prime);
			}
		}
	}

	private BigInteger generatorPrim() {
		return null;
	}
	
	public void cancel() {
		this.cancelled = true;
	}

	List<BigInteger> aSecondOfPrimes() throws InterruptedException {
		PrimeGenerator generator = new PrimeGenerator();
		new Thread(generator).start();

		try {
			SECONDS.sleep(1);
		}
		finally {
			generator.cancel();
		}

		return generator.get();
	}

	private List<BigInteger> get() {
		return null;
	}

}
