package assignment1;

import java.util.Scanner;

public class HumanAgent extends Agent {

    public HumanAgent(Board board) {
        super(board);
    }

    /**
     * Asks the human for a move with from and to coordinates and makes sure its valid.
     * Create a Move object with the chosen fromCell and toCell
     * @return the valid human inputted Move  
     */
    @Override
    public Move getMove() { 
    	// TODO
    	System.out.println("Let's pick a fromCell."); 
    	Cell fromCell = board.getCell(getCoor());
    	
    	System.out.println("Let's pick a toCell."); 
    	Cell toCell = board.getCell(getCoor());
    	
    	
    	Move aMove = new Move(fromCell, toCell); 
    	if (board.isValidMove(aMove)) { 
    		return aMove;
    	}
        return null;
    }
    
    // break up getCoor() 
 

	private Coordinate getCoor() {
		Scanner scanner = new Scanner(System.in);
    	System.out.print("Choose a row to play i.e. enter a number from 1 to 5: ");
    	while (!scanner.hasNextInt()) {
            System.out.print("Invalid option. Enter 1, 2, 3, 4 or 5: ");
            scanner.next();
        }
    	int x = scanner.nextInt()-1;
    	if (x < 0 || x > 4) {
            System.out.print("Invalid Input.");
            return getCoor();
        }
    	
    	System.out.print("Choose a column to play i.e. enter a number from A to E(or a to e): ");
    	while (!scanner.hasNext("[ABCDEabcde]")) {
            System.out.print("Invalid option. Enter A,B,C,D,E or a,b,c,d,e: ");
            scanner.next();
        }
    	
    	String yst = scanner.next().toLowerCase();
    	String colname = "abcde";
    	int y = colname.indexOf(yst);
    	
    	return new Coordinate(x, y);
	}
} 
	
	