package chapter09.case01;

import java.util.List;
import java.util.concurrent.AbstractExecutorService;
import java.util.concurrent.TimeUnit;

public class GUIExecutor extends AbstractExecutorService {
	
	private static final GUIExecutor instance = new GUIExecutor();
	
	private GUIExecutor() {
	}

	public static GUIExecutor instance() {
		return instance;
	}
	
	public void execute1(Runnable runnable) {
		if (SwingUtilities.isEventDispatchThread()) {
			runnable.run();
		}
		
		SwingUtilities.invokeLater(runnable);
	}

	@Override
	public void shutdown() {
	}

	@Override
	public List<Runnable> shutdownNow() {
		return null;
	}

	@Override
	public boolean isShutdown() {
		return false;
	}

	@Override
	public boolean isTerminated() {
		return false;
	}

	@Override
	public boolean awaitTermination(long timeout, TimeUnit unit) throws InterruptedException {
		return false;
	}

	@Override
	public void execute(Runnable command) {
	}

}
