package chapter07.case02;

import java.math.BigInteger;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

public class PrimeGenerator {
	
	public static class PrimeProducer extends Thread {

		private final BlockingQueue<BigInteger> queue;
		private volatile boolean cancelled = false;
		
		public PrimeProducer(BlockingQueue<BigInteger> queue) {
			this.queue = queue;
		}
		
		public void cancel() {
			this.cancelled = true;
		}
		
		@Override
		public void run() {
			try {
				BigInteger prime = BigInteger.ONE;
				while (!cancelled) {
					prime = prime.nextProbablePrime();
					queue.put(prime);
					System.out.println("product:\t" + prime);
				}
			} catch (InterruptedException e) {
				Thread.currentThread().interrupt();
			}
		}
	
	}
	
	public static class PrimeConsumer extends Thread {
		
		private final BlockingQueue<BigInteger> queue;
		private volatile boolean needMore = true;

		public PrimeConsumer(BlockingQueue<BigInteger> queue) {
			this.queue = queue;
		}

		@Override
		public void run() {
			try {
				BigInteger p = BigInteger.ONE;
				while (needMore) {
					BigInteger prime = queue.take();
					System.out.println("consumer:\t" + prime);
				}
			} catch (InterruptedException e) {
				Thread.currentThread().interrupt();
			}
		}
		
		public void doNotNeedMore() {
			this.needMore = true;
		}
	
	}
	
	public static void main(String[] args) throws InterruptedException {
		final BlockingQueue<BigInteger> queue = new ArrayBlockingQueue<BigInteger>(1000);
		
		PrimeProducer primeProducer = new PrimeProducer(queue);
		PrimeConsumer primeConsumer = new PrimeConsumer(queue);
		
		primeConsumer.start();
		primeProducer.start();
		
		Thread.currentThread().sleep(200);
		primeConsumer.doNotNeedMore();
		primeProducer.cancel();

	}

}
