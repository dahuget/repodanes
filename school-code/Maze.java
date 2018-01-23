/**
 * Name: DANA HUGET
 * ID: V00860786
 * Date: June 26, 2016
 * CSC115 Assignment 4 : Backtracking through a maze.
 * File Name: Maze.java.
 */

/*
 * A representation of the maze object
 */
 
 public class Maze{
 	//array to keep track of the algorithm's path/solution
 	char[][] mazeData;
 	Cell start;
 	Cell finish;
 	CellDeque path; //keeps track of the path
 	
 	/*
 	 * Sets up a simple unsolved maze. The incoming parameter that symbolizes the 
 	 * maze is an array of strings such that if the strings are printed, one to a line, 
 	 * the maze will be visible as single characters, either an asterisk or a space. 
 	 * The asterisk represents a wall in the maze, while the space character represents a free cell.
 	 * Parameters:
	 * arr - A representation of the maze as described above.
	 * start - The cell that enters the maze.
	 * finish - The cell that exits the maze.
 	*/
 	public Maze(String[] arr, Cell start, Cell finish){
 		//copy the data from String[] arr into the 2D array of chars called mazeData
 		mazeData = new char[arr.length][arr[0].length()];
 		for(int i = 0; i < arr.length; i++){ //rows
 			for(int j = 0; j < arr[0].length(); j++){ //columns
 				mazeData[i][j] = arr[i].charAt(j);
 			}
 		}
 		this.start = start;
 		this.finish = finish;
 		//creates a deque to keep track of cells visited in the maze
 		path = new CellDeque();
 	
 	}
 	
 	/*
 	This method answers the question: "Is there a path from a given source cell 
 	to the destination cell?" using recursion
 	Parameters: curr - the current cell and  finish - the destination cell
 	Returns true if there is a path from Cell curr to Cell dest
 	*/
 	private boolean findPath(Cell curr, Cell finish){
 		//System.out.println("Current location: " + curr);
 		//add current cell to deque, keeps track of path
 		path.insertLast(curr);
  		mazeData[curr.row][curr.col] = 'A';		
 		//**BASE CASE**
 		//if curr cell equals finish cell
 			//return true, we're DONE!
 		if(curr.equals(finish)){
 			return true;
 		}
 		//**RECURSIVE**
 		//ADD that cell to the DEQUE solution path list
 			//path.insertFirst(NEWCELL); or path.insertLast(NEWCELL);
 			//make a recursive call to see if there is a path
 			//from THAT cell to the finish
 		//check for cell below is that is BLANK (==' ')
 		if(mazeData[curr.row+1][curr.col] == ' '){
 			//add current cell to deque
 			//System.out.println("Found a path DOWN");
 			Cell temp = new Cell(curr.row + 1, curr.col);
 			if(findPath(temp, finish) == true){
 				return true;
 			}
 		}
 		//check for cell to the right is that is BLANK (==' ')
 		if(mazeData[curr.row][curr.col+1] == ' '){
 			//add current cell to deque
 			//System.out.println("Found a path to the RIGHT");
 			Cell temp = new Cell(curr.row, curr.col + 1);
 			if(findPath(temp, finish) == true){
 				return true;
 			}
 		}
 		//check for cell to the left is that is BLANK (==' ')
 		if(mazeData[curr.row][curr.col-1] == ' '){
 			//add current cell to deque
 			//System.out.println("Found a path to the LEFT");
 			Cell temp = new Cell(curr.row, curr.col - 1);
 			if(findPath(temp, finish) == true){
 				return true;
 			}
 		}
 		//after backtracking, if we are back at start then there was no solution
 		if(curr.equals(start)){
 			path.makeEmpty();
 			return false;
 		}	
 		//check for cell up is that is BLANK (==' ')
 		if(mazeData[curr.row-1][curr.col] == ' '){
 			//add current cell to deque
 			//System.out.println("Found a path UP");
 			Cell temp = new Cell(curr.row-1, curr.col);
 			if(findPath(temp, finish) == true){
 				return true;
 			}
 		}
 		//**DEAD END**
 		//remove last cell from deque so it does not show in path solution (BACKTRACK)
 		mazeData[curr.row][curr.col] = 'B';		
 		path.removeLast();
 		return false;
 	
  	}
 	/*
 	 * Finds the solution path, if there is one, for the maze. 
 	 * This method simply calls the recursive method with its initial values.
	 * Returns: The list of Cell objects, ordered from the start Cell to the 
	 * finish Cell if there is a solution. 
	 * If there is no solution, then the list is empty.
 	*/
 	public CellDeque solve(){
 		boolean solved = findPath(this.start, this.finish);
 		if (solved) {
 			//System.out.println("Found a path!!!\n Solution:\n" + path);
 		} else {
 			System.out.println("Couldn't find a path :(");
 		}
 		System.out.println(toString());
 		return path;
 	}
 	/*
 	 * This very helpful method produces a current snapshot of what the maze looks like. 
 	 * Produces a 2D dimensional representation of the current maze.
 	 * Overrides: toString in class java.lang.Object
	 * Returns: A meaningful string representation of the maze.
 	*/
 	public String toString(){
 		String details = "";
 		//set to details to be all data from char array
 		for(int i = 0; i < mazeData.length; i++){ //rows
 			for(int j = 0; j <  mazeData[i].length; j++){ //columns
 				details = details + mazeData[i][j];
 			}
 			details += "\n";
 		}
 		return details;
 	}
 
 	public static void main(String args[]) {
 		
 	}
 }
