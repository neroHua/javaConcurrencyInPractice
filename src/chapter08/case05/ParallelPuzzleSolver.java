package chapter08.case05;

import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicInteger;

public class ParallelPuzzleSolver <P, M> {

	private final Puzzle<P, M> puzzle;
	private final Map<P, Node<P, M>> pathNodeMap = new ConcurrentHashMap<P, Node<P, M>>();
	private final Map<P, Node<P, M>> goalNodeMap = new ConcurrentHashMap<P, Node<P, M>>();

	private final boolean stopWhenFindOneSolution;
	private final Integer totalTaskCount;
	private volatile AtomicInteger currentTotalTaskCount = new AtomicInteger();
	private final ExecutorService executorService = Executors.newCachedThreadPool();
	
	public ParallelPuzzleSolver(Puzzle<P, M> puzzle, boolean stopWhenFindOneSolution, Integer totalTaskCount) {
		this.puzzle = puzzle;
		this.stopWhenFindOneSolution = stopWhenFindOneSolution;
		this.totalTaskCount = totalTaskCount;
	}
	
	public Map<P, Node<P, M>> solve() {
		P initPosition = puzzle.initPosition();
		
		search(new Node<P, M> (initPosition, null, null));
		
		return goalNodeMap;
	}
	
	private void search(Node<P, M> node) {
		if (currentTotalTaskCount.get() >= totalTaskCount) {
			executorService.shutdown();
			return;
		}
		
		if (stopWhenFindOneSolution && 0 < goalNodeMap.size()) {
			executorService.shutdown();
			return;
		}

		currentTotalTaskCount.incrementAndGet();
		executorService.execute(new SearchTask(new ConcurrentHashMap<P, Node<P, M>>(pathNodeMap), node));
	}
	
	public class SearchTask implements Runnable {

		private final Map<P, Node<P, M>> pathNodeMap;
		private final Node<P, M> node;

		public SearchTask(Map<P, Node<P, M>> pathNodeMap, Node<P, M> node) {
			this.pathNodeMap = pathNodeMap;
			this.node = node;
		}
		
		@Override
		public void run() {
			if (pathNodeMap.containsKey(node.position)) {
				pathNodeMap.remove(node);
				return;
			}

			pathNodeMap.put(node.position, node);
			if (puzzle.isGoal(node.position)) {
				goalNodeMap.put(node.position, node);
			}

			Set<M> legalMoveSet = puzzle.legalMove(node.position);
			for (M m : legalMoveSet) {
				P newPosition = puzzle.move(node.position, m);
				Node<P, M> nextNode = new Node<P, M>(newPosition, m, node);
				search(nextNode);
			}

			pathNodeMap.remove(node.position);
			return;
		}	

	}

}
