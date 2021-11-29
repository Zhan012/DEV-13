package assignment1;

import java.util.List;
import java.util.Random;

public class RandomAgent extends Agent {

    public RandomAgent(Board board) {
        super(board);
    }

    /**
     * Gets a valid random move the RandomAgent can do.
     * @return a valid Move that the RandomAgent can perform on the Board
     */
    @Override
    public Move getMove() { 
    	// TODO
    	List<Move> lstOfMove = board.getPossibleMoves();
    	if (lstOfMove.size() != 0) {
    		Random rnum = new Random();
    		int index = rnum.nextInt(lstOfMove.size());
    		//System.out.println("number"+ index);
    		
    		Move move = lstOfMove.get(index);
    		//System.out.println("move"+ move);
    		if (board.isValidMove(move)) {
    			return move;	 
    		}
    		 
    	}
        return null;
    }
}
 