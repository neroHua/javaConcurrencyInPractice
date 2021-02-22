package chapter06.case12;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

public class RenderPageWithAD {

	private static final AD DEFAULT_AD = new AD();
	private static final long TIME_BUDGET = 20000;
	private static final ExecutorService executorService = Executors.newCachedThreadPool();

	public Page renderPageWithAD() {
		long endNanos = System.nanoTime() + TIME_BUDGET;
		Future<AD> f = executorService.submit(new Callable<AD>() {
			
			@Override
			public AD call() {
				return new AD();
			}

		});

		Page page = renderPageBody();
		AD ad = null;

		try {
			long timeLeft = endNanos - System.nanoTime();
				ad = f.get(timeLeft, TimeUnit.NANOSECONDS);
		} catch (InterruptedException e) {
			Thread.currentThread().interrupt();
		} catch (ExecutionException e) {
			ad = DEFAULT_AD;
		} catch (TimeoutException e) {
			ad = DEFAULT_AD;
			f.cancel(true);
		}

		page.setAd(ad);

		return page;
	}
	
	private Page renderPageBody() {
		return null;
	}

	public static class AD {
		
	}
	
	public static class Page {

		private AD ad;

		public AD getAd() {
			return ad;
		}

		public void setAd(AD ad) {
			this.ad = ad;
		}
		
	}
}
