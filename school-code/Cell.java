/**
 * Cell.java
 * Created for CSC115 Assignment#4
 */

/**
 * Cell is a cell in a 2-dimensional grid or matrix,
 * defined by its row number and column number.
 * The numbers are not confined to non-negative numbers;
 * this requirement has to be imposed by the class that 
 * uses a cell object
 */
public class Cell {

	/**
	 * The row number.
	 */
	public int row;
	/**
	 * The column number.
	 */
	public int col;

	/**
	 * Constructs a cell.
	 * @param row The row number of the cell.
	 * @parqam col The column number of the cell.
	 */
	Cell(int row,int column) {
		this.row = row;
		this.col = column;
	}

	/**
	 * @return A string represention of the cell as (r,c) where r is the row number
	 * and c is the column number.
	 */
	public String toString() {
		return "("+row+","+col+")";
	}

	/**
	 * Determines equality with another Cell object.
	 * @param other The other Cell object.
	 * @return true if both the row and column are identical.
	 */
	public boolean equals(Cell other) {
		return (this.row==other.row && this.col==other.col);
	}
}
