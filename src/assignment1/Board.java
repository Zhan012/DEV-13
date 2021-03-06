package assignment1;

import java.io.BufferedWriter;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Scanner;

import assignment1.Piece.Type;

import observable.Observable;
import observable.Observer;

public class Board extends Observable{
    public int size = 5;

    // 2D Array of Cells for representation of the game board
    public final Cell[][] board = new Cell[size][size];

    private Piece.Type turn;
    private Piece.Type winner;
    private Designer designer = null;

    /**
     * Create a Board with the current player turn set.
     */
    public Board() {
        this.loadBoard("Boards/Starter.txt");
    }

    /**
     * Create a Board with the current player turn set and a specified board.
     * @param boardFilePath The path to the board file to import (e.g. "Boards/Starter.txt")
     */
    public Board(String boardFilePath) {
        this.loadBoard(boardFilePath);
    }

    /**
     * Creates a Board copy of the given board.
     * @param board Board to copy
     */
    public Board(Board board) {
        this.size = board.size;
        for (int row = 0; row < size; row++) {
            for (int col = 0; col < size; col++) {
                this.board[row][col] = new Cell(board.board[row][col]);
            }
        }
        this.turn = board.turn;
        this.winner = board.winner;
    }

    /**
     * @return the Piece.Type (Muskeeteer or Guard) of the current turn
     */
    public Piece.Type getTurn() {
        return turn;
    }

    /**
     * Get the cell located on the board at the given coordinate.
     * @param coordinate Coordinate to find the cell
     * @return Cell that is located at the given coordinate
     */
    public Cell getCell(Coordinate coordinate) { 
    	// TODO
    	int row = coordinate.row;
    	int col = coordinate.col;
    	return board[row][col];

    }

    /**
     * @return the game winner Piece.Type (Muskeeteer or Guard) 
     * if there is one otherwise null
     */
    public Piece.Type getWinner() {
        return winner;
    }

    /**
     * Gets all the Musketeer cells on the board.
     * @return List of cells
     */ 
    public List<Cell> getMusketeerCells() { 
    	// TODO
    	List<Cell> cellList = new ArrayList<Cell>();
    	for (int row = 0; row < size; row++) {
            for (int col = 0; col < size; col++) {
            	Cell aCell = this.board[row][col];
            	if (aCell.getPiece() != null) {
            		Piece piece = aCell.getPiece();
            		if (piece.getType().getType() == "MUSKETEER") {
                		cellList.add(aCell);
                	}	
            	}
            }
        }  
    	return cellList;
        //return List.of();
    }

    /**
     * Gets all the Guard cells on the board.
     * @return List of cells
     */
    public List<Cell> getGuardCells() { 
    	// TODO 
    	List<Cell> cellList = new ArrayList<Cell>();
    	for (int row = 0; row < size; row++) {
            for (int col = 0; col < size; col++) {
            	Cell aCell = this.board[row][col];
            	if (aCell.getPiece() != null) {
            		Piece piece = aCell.getPiece();
            		if (piece.getType().getType() == "GUARD") {
                		cellList.add(aCell);
                	}
            		
            	}
            }
        }  
    	return cellList;
    }

    /**
     * Executes the given move on the board and 
     * changes turns at the end of the method.
     * @param move a valid move
     */
    public void move(Move move) { 
    	// TODO
    	// move
    	Piece fPiece = move.fromCell.getPiece();
    	move.toCell.setPiece(fPiece);
    	move.fromCell.removePiece();
    	
    	//turn
    	
    	helpSwichTurn();
    }
    private void helpSwichTurn() {
    	if(this.turn.getType().equals("GUARD")) {
    		this.turn = Type.MUSKETEER;
    	} 
    	else if (this.turn.getType().equals("MUSKETEER")) {
    		this.turn = Type.GUARD;
    	}
    }

    /**
     * Undo the move given.
     * @param move Copy of a move that was done and needs to be undone. 
     * The move copy has the correct piece info in the from and to cell fields. 
     * Changes turns at the end of the method.
     */
    public void undoMove(Move move) { 
    	// TODO
    	//copy move
    	Cell cfCell = move.fromCell;
    	Cell ctCell = move.toCell;
    	Coordinate fCoor = cfCell.getCoordinate();
    	Coordinate tCoor = ctCell.getCoordinate();
    	Cell fCell = getCell(fCoor);
    	Cell tCell = getCell(tCoor);
    	
    	fCell.setPiece(cfCell.getPiece());
    	tCell.setPiece(ctCell.getPiece());
    	//swichTurn
    	
    	helpSwichTurn();
    }

    /**
     * Checks if the given move is valid. Things to check: 
     * (1) the toCell is next to the fromCell
     * (2) the fromCell piece can move onto the toCell piece.
     * @param move a move
     * @return     True, if the move is valid, false otherwise
     */
    public Boolean isValidMove(Move move) { 
    	// TODO
    	//toCell is next to the fromCell
    	if (!move.fromCell.hasPiece()) {
    		return false;
    	}
    	int fRow = move.fromCell.getCoordinate().row;
    	int fCol = move.fromCell.getCoordinate().col;
    	int tRow = move.toCell.getCoordinate().row;
    	int tCol = move.toCell.getCoordinate().col;
    	
    	if (fRow == tRow & Math.abs(fCol-tCol) == 1) {
    		if (helpValidMove(move)) {
    			return true;
    		}
    	}
    	else if (fCol == tCol & Math.abs(fRow-tRow) == 1) {
    		if (helpValidMove(move)) {
    			return true;
    		}
    	}
        return false;
    }
    
    private Boolean helpValidMove(Move move) {
    	if (move.fromCell.getPiece().getType().getType() == "MUSKETEER") {
    		return move.fromCell.getPiece().canMoveOnto(move.toCell);
    		//return true;
    	}
    	else if (move.fromCell.getPiece().getType().getType() == "GUARD") {
    		//System.out.println("kunnan");
    		return move.fromCell.getPiece().canMoveOnto(move.toCell);
    		//return true;
    	}
    	return false;
    }

    /**
     * Get all the possible cells that have pieces that can be moved this turn.
     * @return      Cells that can be moved from the given cells
     */
    public List<Cell> getPossibleCells() { 
    	// TODO
    	List<Cell> cells = new ArrayList<>();
    	if (this.turn.getType() == "MUSKETEER") {
    		for(int i = 0; i < getMusketeerCells().size(); i++) {
    			Cell mCell = getMusketeerCells().get(i);
    			if (getPossibleDestinations(mCell).size() != 0) {
    				cells.add(mCell);
    				}
    			}
    		}
    	else if (this.turn.getType() == "GUARD") {
    		for(int i = 0; i < getGuardCells().size(); i++) {
    			Cell gCell = getGuardCells().get(i);
    			if (getPossibleDestinations(gCell).size() != 0) {
    				cells.add(gCell);
    				}
    			}
    		}
        return cells;
    } 

    /**
     * Get all the possible cell destinations that is possible to move to from the fromCell.
     * @param fromCell The cell that has the piece that is going to be moved
     * @return List of cells that are possible to get to
     */
    public List<Cell> getPossibleDestinations(Cell fromCell) {
    	// TODO
    	List<Cell> cells = new ArrayList<>();
    	if (!fromCell.hasPiece()) {
    		return cells;
    	}

    	Coordinate coorFromCell = fromCell.getCoordinate();
    	
    	Coordinate coorUp = new Coordinate(coorFromCell.row-1, coorFromCell.col);
    	Coordinate coorDown = new Coordinate(coorFromCell.row+1, coorFromCell.col);
    	Coordinate coorLeft = new Coordinate(coorFromCell.row, coorFromCell.col-1);
    	Coordinate coorRight = new Coordinate(coorFromCell.row, coorFromCell.col+1);
    	
    	ArrayList<Coordinate> coors = new ArrayList<>(Arrays.asList(coorUp,coorDown,coorLeft,coorRight));
    	
    	
    	if (fromCell.getPiece().getType().getType() == "MUSKETEER") {
    		for (int i = 0; i < 4; i++) {
    			if (heplValidCoor(coors.get(i))) {
    				Cell cell = getCell(coors.get(i));
        			if (cell != null && cell.getPiece() != null){
        				if (cell.getPiece().getType().getType() == "GUARD") {
        					cells.add(cell);
        				}
        				}
        			}
    			}
    		
    		}
    	else if (fromCell.getPiece().getType().getType() == "GUARD") {
    		for (int i = 0; i < 4; i++) {
    			if (heplValidCoor(coors.get(i))){
    				Cell cell = getCell(coors.get(i));
        			if (cell != null && !cell.hasPiece()) {
        				cells.add(cell);
        				}
        			}
    		}
    	}
        return cells; 
    }
    
    private boolean heplValidCoor(Coordinate coordinate) {

    	if (coordinate.row < 0 | 4 < coordinate.row) {
    		return false;
    	}
    	if (coordinate.col < 0 | 4 < coordinate.col) {
    		return false;
    	}
		return true;
    	
    }
    

    /**
     * Get all the possible moves that can be made this turn.
     * @return List of moves that can be made this turn
     */
    public List<Move> getPossibleMoves() { 
    	// TODO
    	List<Move> moves = new ArrayList<>();
    	List<Cell> pCells = getPossibleCells();
    	for (int i = 0; i < pCells.size();i++) {
    		Cell fcell = pCells.get(i);
    		for (int c = 0; c < getPossibleDestinations(fcell).size(); c++) {
				Cell tcell = getPossibleDestinations(fcell).get(c);
				Move move = new Move(fcell, tcell);
				if (isValidMove(move)) {
					moves.add(move);
				}
			}	
    	}
    	return moves;
    }

    /**
     * Checks if the game is over and sets the winner if there is one.
     * @return True, if the game is over, false otherwise.
     */
    public boolean isGameOver() { 
    	// TODO
    	List<Cell> mCells = getMusketeerCells();
    	if (isSameLine(mCells)) {
    		this.winner = Type.GUARD;
        	return true;
    	}
    	else if (this.turn.getType() == "MUSKETEER") {
	    	boolean noDestination = true;
	    	for (int i = 0; i < mCells.size(); i++) {
	    		if (getPossibleDestinations(mCells.get(i)).size() != 0) {
	    			noDestination = false;
	    		}
	    	}
	    	if (noDestination) {
	    		this.winner = Type.MUSKETEER;
	    	}
	    		return noDestination;		
	    	}
    	return false;
    }
    private boolean isSameLine(List<Cell> cells){
    	
    	boolean isRowSame = true;
    	int fRow = cells.get(0).getCoordinate().row;
    	for (int i = 0; i < cells.size(); i++) {
    		if (fRow != cells.get(i).getCoordinate().row) {
    			isRowSame = false;
    		}
    	}
    	
    	boolean isColSame = true;
    	int fCol = cells.get(0).getCoordinate().col;
    	for (int c = 0; c < cells.size(); c++) {
    		if (fCol != cells.get(c).getCoordinate().col) {
    			isColSame = false;
    		}
    	}
    	
    	return isRowSame | isColSame;
    	
    }

    /**
     * Saves the current board state to the boards directory 
     */
    public void saveBoard() {
        String filePath = String.format("boards/%s.txt",
                new SimpleDateFormat("yyyy.MM.dd.HH.mm.ss").format(new Date()));
        File file = new File(filePath);

        try {
            file.createNewFile();
            Writer writer = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(file)));
            writer.write(turn.getType() + "\n");
            for (Cell[] row: board) {
                StringBuilder line = new StringBuilder();
                for (Cell cell: row) {
                    if (cell.getPiece() != null) {
                        line.append(cell.getPiece().getSymbol());
                    } else {
                        line.append("_");
                    }
                    line.append(" ");
                }
                writer.write(line.toString().strip() + "\n");
            }
            writer.close();
            System.out.printf("Saved board to %s.\n", filePath);
        } catch (IOException e) {
            e.printStackTrace();
            System.out.printf("Failed to save board to %s.\n", filePath);
        }
    }

    @Override
    public String toString() {
        StringBuilder boardStr = new StringBuilder("  | A B C D E\n");
        boardStr.append("--+----------\n");
        for (int i = 0; i < size; i++) {
            boardStr.append(i + 1).append(" | ");
            for (int j = 0; j < size; j++) {
                Cell cell = board[i][j];
                boardStr.append(cell).append(" ");
            }
            boardStr.append("\n");
        }
        return boardStr.toString();
    }

    /**
     * Loads a board file from a file path. 
     * @param filePath The path to the board file to load (e.g. "Boards/Starter.txt")
     */
    private void loadBoard(String filePath) {
        File file = new File(filePath);
        Scanner scanner = null;
        try {
            scanner = new Scanner(file);
        } catch (FileNotFoundException e) {
            System.err.printf("File at %s not found.", filePath);
            System.exit(1);
        }

        turn = Piece.Type.valueOf(scanner.nextLine().toUpperCase());

        int row = 0, col = 0;
        while (scanner.hasNextLine()) {
            String line = scanner.nextLine();
            String[] pieces = line.trim().split(" ");
            for (String piece: pieces) {
                Cell cell = new Cell(new Coordinate(row, col));
                switch (piece) {
                    case "O" -> cell.setPiece(new Guard());
                    case "X" -> cell.setPiece(new Musketeer());
                    default -> cell.setPiece(null);
                }
                this.board[row][col] = cell;
                col += 1;
            }
            col = 0;
            row += 1;
        }
        scanner.close();
        System.out.printf("Loaded board from %s.\n", filePath);
    }

	@Override
	public void register(Observer o) {
		// TODO Auto-generated method stub
		this.designer = new Designer(this);
		
	}

	@Override
	public void unregister(Observer o) {
		// TODO Auto-generated method stub
		this.designer = null;
		
	}

	@Override
	public void notifyObserver() {
		// TODO Auto-generated method stub
		this.designer.update();
	}
}
