package chapter08.case05;

import java.util.Set;

public interface Puzzle<P, M> {

	P initPosition();
	
	boolean isGoal(P posintion);
	
	Set<M> legalMove(P position);
	
	P move(P position, M move);
}
