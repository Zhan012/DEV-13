package assignment1;

import observable.Observer;

public class Designer implements Observer{

private Board board;

public Designer(Board board) {
	this.board = board;
}
	
	@Override
	public void update() {
		// TODO Auto-generated method stub
		GreedyAgent a = new GreedyAgent(this.board);
		System.out.print(board.getWinner());
		System.out.print("The musketeer number is");
		System.out.print(this.board.getMusketeerCells().size());
		System.out.print("The Guard number is");
		System.out.print(this.board.getGuardCells().size());
		System.out.print("The recommend Move is");
		System.out.print(a.getMove().toCell.getCoordinate().row);
		System.out.print(a.getMove().toCell.getCoordinate().col);
	}

}
