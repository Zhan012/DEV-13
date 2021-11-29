package assignment1;

import java.util.List;

public class BoardEvaluatorImpl implements BoardEvaluator {

    /**
     * Calculates a score for the given Board
     * A higher score means the Musketeer is winning
     * A lower score means the Guard is winning  
     * Add heuristics to generate a score given a Board
     * @param board Board to calculate the score of
     * @return int Score of the given Board
     */
    @Override
    public int evaluateBoard(Board board) { 
    	// TODO
    	int score = 0;
    	// + 
    	score = score + getGuardNum(board)*2;
    	List<Cell> mCells = board.getMusketeerCells();
    	// - Same line
    	Cell mCell1 = mCells.get(0);
    	Cell mCell2 = mCells.get(1);
    	Cell mCell3 = mCells.get(2);
    	if (isSameLine(mCell1, mCell1)) {
    		score = score - 2;
    	}
    	if (isSameLine(mCell2, mCell3)) {
    		score = score - 2;
    	}
    	if (isSameLine(mCell1, mCell3)) {
    		score = score - 2;
    	}
    	// - possible moves of Musketeer
    	for (Cell musk : mCells) {
    		score = score - 2 * board.getPossibleDestinations(musk).size();
    	}
    	//- duplicated possible destinations of Musketeteer
    	List<Cell> cpMoves1 = board.getPossibleDestinations(mCell1);
    	List<Cell> cpMoves2 = board.getPossibleDestinations(mCell2);
    	List<Cell> cpMoves3 = board.getPossibleDestinations(mCell3);
    	
    	score = score - duplicatedNum(cpMoves1, cpMoves2);
    	score = score - duplicatedNum(cpMoves2, cpMoves3);
    	score = score - duplicatedNum(cpMoves1, cpMoves3);
    	
    	
        return score; 
    }
    private int getGuardNum(Board board) {
    	return board.getGuardCells().size();
    }
	private boolean isSameLine(Cell fCell, Cell sCell){
	    	
	    	boolean isRowSame = false;
	    	if (fCell.getCoordinate().row == sCell.getCoordinate().row) {
	    		isRowSame = true;
	    	}
	    	boolean isColSame = false;
	    	if (fCell.getCoordinate().col == sCell.getCoordinate().col) {
	    		isColSame = true;
	    	}
	    	return isRowSame | isColSame;
	    	
	    }
	private int duplicatedNum(List<Cell> lst1, List<Cell> lst2) {
		int cnt = 0;
		for (Cell c1 : lst1) {
			for (Cell c2 : lst2) {
				if (c1.equals(c2)) {
					cnt += 1;
				}
			}
		}
		return cnt;
	}
}
