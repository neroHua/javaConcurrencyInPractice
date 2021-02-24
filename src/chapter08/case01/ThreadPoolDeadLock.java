package chapter08.case01;

import java.io.File;
import java.io.FileReader;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public class ThreadPoolDeadLock {

	private static final ExecutorService executorService = Executors.newSingleThreadExecutor();

	public static class RenderPageTask implements Callable<String> {

		@Override
		public String call() throws Exception {
			Future<String> header;
			Future<String> footer;
			
			header = executorService.submit(new LoadFileTask(new File("header.html")));
			footer = executorService.submit(new LoadFileTask(new File("footer.html")));
			String body = renderBody();
			String headerString = header.get();
			String footerString = footer.get();
			String s = headerString + body + footerString;
			
			return s;
		}
		
		public String renderBody() {
			return "body";
		}

	}
	
	public static class LoadFileTask implements Callable<String> {

		private final File file;
		
		public LoadFileTask(File file) {
			this.file = file;
		}
		
		@Override
		public String call() throws Exception {
			FileReader fileReader = new FileReader(file);

			char[] buffer = new char[1024];
			fileReader.read(buffer);

			return new String(buffer);
		}	
	}

	public static void main(String[] args) throws InterruptedException, ExecutionException {
		Future<String> future = executorService.submit(new RenderPageTask());
		System.out.println(future.get());
	}

}
