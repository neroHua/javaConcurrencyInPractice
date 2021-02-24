package chapter09.case01;

import java.lang.reflect.InvocationTargetException;
import java.util.concurrent.*;

public class SwingUtilities {
	
	private static final ExecutorService executorService = Executors.newSingleThreadExecutor(new SwingThreadFactory());
	
	private static volatile Thread swingThread;
	
	public static boolean isEventDispatchThread() {
		return Thread.currentThread() == swingThread;
	}
	
	public static void invokeLater(Runnable runnable) {
		executorService.execute(runnable);
	}
	
	public static void invokeAndWait(Runnable runnable) throws InvocationTargetException, InterruptedException {
		Future<?> future = executorService.submit(runnable);
		
		try {
			future.get();
		} catch (ExecutionException e) {
			throw new InvocationTargetException(e);
		}
	}

	public static class SwingThreadFactory implements ThreadFactory {
		
		@Override
		public Thread newThread(Runnable runnable) {
			return new Thread(runnable);
		}

	}

}
