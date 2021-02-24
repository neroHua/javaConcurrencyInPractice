package chapter08.case05;

import java.util.HashSet;
import java.util.Set;

public class SequentialPuzzleSolver<P, M> {

	private final Puzzle<P, M> puzzle;
	private final Set<Node<P, M>> pathNodeSet = new HashSet<Node<P, M>>();
	private final Set<Node<P, M>> goalNodeSet = new HashSet<Node<P, M>>();
	
	public SequentialPuzzleSolver(Puzzle<P, M> puzzle) {
		this.puzzle = puzzle;
	}
	
	public Set<Node<P, M>> solve() {
		P initPosition = puzzle.initPosition();
		
		search(new Node<P, M> (initPosition, null, null));
		
		return goalNodeSet;
	}
	
	private void search(Node<P, M> node) {
		if (pathNodeSet.contains(node)) {
			pathNodeSet.remove(node);
			return;
		}
		
		pathNodeSet.add(node);
		if (puzzle.isGoal(node.position)) {
			goalNodeSet.add(node);
		}
		
		Set<M> legalMoveSet = puzzle.legalMove(node.position);
		for (M m : legalMoveSet) {
			P newPosition = puzzle.move(node.position, m);
			Node<P, M> nextNode = new Node<P, M>(newPosition, m, node);
			search(nextNode);
		}
		
		pathNodeSet.remove(node);
		return;
	}

}
