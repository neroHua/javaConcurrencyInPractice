package chapter08.case05;

import java.util.LinkedList;
import java.util.List;

public class Node<P, M> {

	final P position;
	final M move;
	final Node<P, M> previousNode;
	
	public Node (P postion, M move, Node<P, M> previousNode) {
		this.position = postion;
		this.move = move;
		this.previousNode = previousNode;
	}

	public List<M> asMoveList() {
		List<M> moveList = new LinkedList<M>();
		
		Node<P, M> iteratorNode = this;
		while (iteratorNode != null) {
			moveList.add(0, iteratorNode.move);
			iteratorNode = iteratorNode.previousNode;
		}

		return moveList;
	}

	public List<P> asPositionList() {
		List<P> positionList = new LinkedList<P>();
		
		Node<P, M> iteratorNode = this;
		while (iteratorNode != null) {
			positionList.add(0, iteratorNode.position);
			iteratorNode = iteratorNode.previousNode;
		}

		return positionList;
	}
	
	public List<Node<P, M>> asNodeList() {
		List<Node<P, M>> nodeList = new LinkedList<Node<P, M>>();
		
		Node<P, M> iteratorNode = this;
		while (iteratorNode != null) {
			nodeList.add(0, iteratorNode);
			iteratorNode = iteratorNode.previousNode;
		}

		return nodeList;
	}

}
