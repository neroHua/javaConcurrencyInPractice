package chapter06.case13;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.concurrent.Callable;
import java.util.concurrent.CancellationException;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;

public class TravelQuotesUnderTimeBudget {

	private final ExecutorService executorService = Executors.newCachedThreadPool();

	private class QuoteTask implements Callable<TravelQuote> {

		private final TravelCompany company = null;
		private final TravelInfo travelInfo = null;

		public QuoteTask(TravelCompany company2, TravelInfo travelInfo2) {
		}

		public TravelQuote call() throws Exception {
			return company.solicitQuote(travelInfo);
		}

		public TravelQuote getFailureQuote(Throwable cause) {
			return null;
		}

		public TravelQuote getTimeoutQuote(CancellationException e) {
			return null;
		}

	}

	public List<TravelQuote> getRankedTravelQuotes(TravelInfo travelInfo, Set<TravelCompany> companies, Comparator<TravelQuote> ranking, long time, TimeUnit unit) throws InterruptedException {
		List<QuoteTask> tasks = new ArrayList<QuoteTask>();
		for (TravelCompany company : companies) {
			tasks.add(new QuoteTask(company, travelInfo));
		}

		List<Future<TravelQuote>> futures = executorService.invokeAll(tasks, time, unit);

		List<TravelQuote> quotes = new ArrayList<TravelQuote>(tasks.size());

		Iterator<QuoteTask> taskIter = tasks.iterator();
		for (Future<TravelQuote> f : futures) {
			QuoteTask task = taskIter.next();
			try {
				quotes.add(f.get());
			} catch (ExecutionException e) {
				quotes.add(task.getFailureQuote(e.getCause()));
			} catch (CancellationException e) {
				quotes.add(task.getTimeoutQuote(e));
			}
		}

		Collections.sort(quotes, ranking);

		return quotes;
	}
	
	public static class TravelCompany {
		public TravelQuote solicitQuote(TravelInfo travelInfo) {
			return null;
		}
	}

	public static class TravelInfo {
	}

	public static class TravelQuote {
	}

}
