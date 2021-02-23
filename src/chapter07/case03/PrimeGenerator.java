package chapter07.case03;

import java.math.BigInteger;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

public class PrimeGenerator {
	
	public static class PrimeProducer extends Thread {

		private final BlockingQueue<BigInteger> queue;
		
		public PrimeProducer(BlockingQueue<BigInteger> queue) {
			this.queue = queue;
		}

		public void cancel() {
		    interrupt();
		}

		@Override
		public void run() {
			try {
				BigInteger prime = BigInteger.ONE;
				while (!Thread.currentThread().isInterrupted()) {
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
				System.out.println("a4");
			} catch (InterruptedException e) {
				System.out.println("a3");
				Thread.currentThread().interrupt();
			}

			System.out.println("a2");
		}
		
		public void doNotNeedMore() {
			this.needMore = false;
		}
	
	}
	
	public static void main(String[] args) throws InterruptedException {
		final BlockingQueue<BigInteger> queue = new ArrayBlockingQueue<BigInteger>(1000);
		
		PrimeProducer primeProducer = new PrimeProducer(queue);
		PrimeConsumer primeConsumer = new PrimeConsumer(queue);
		
		primeProducer.setName("a1");
		primeConsumer.setName("a2");
		
		primeConsumer.start();
		primeProducer.start();
		
		Thread.currentThread().sleep(200);
		primeConsumer.doNotNeedMore();
		primeProducer.cancel();
	}

}
