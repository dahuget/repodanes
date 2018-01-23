/**
 * Name: DANA HUGET
 * ID: V00860786
 * Date: June 26, 2016
 * CSC115 Assignment 4 : Backtracking through a maze.
 * File Name: Cell.Deque.java.
 * 
 * The CellDeque class is a Double ended queue of Cell objects.
 */
 
 public class CellDeque{
 	CellNode head;
 	CellNode tail;
 	private int count;
 	
 	//constructor creates an empty deque
 	public CellDeque(){
 		this.head = null;
 		this.tail = null;
 		count = 0;
 	}
 	
 	/*
 	 * Accesses the first cell in the deque without removing it.
	 * Returns: the cell at the front.
	 * Throws: DequeEmptyException - if the deque is empty.
 	*/
 	public Cell first(){
 		if(!isEmpty()){ //deque is not empty
 			return head.item;
 		}else{
 			throw new DequeEmptyException("Deque exception on first: " 
 										+ "Deque empty");
 		}
 	}
 	
 	/*
 	 * Accesses the last cell in the deque without removing it.
	 * Returns: the cell at the end.
	 * Throws: DequeEmptyException - if the deque is empty.
 	*/
 	public Cell last(){
 		if(!isEmpty()){ //deque is not empty
 			return tail.item;
 		}else{
 			throw new DequeEmptyException("Deque exception on first: " 
 										+ "Deque empty");
 		}
 	}
 	
 	/*
 	 * Inserts cell into the front of the deque.
	 * Parameters: p - The cell to be inserted.
 	*/
 	public void insertFirst(Cell p) {
 		//create a CellNode with Cell p to insert into deque
 		CellNode a = new CellNode(p);
 		if (count == 0){ //if the list is empty
			head = a;
			tail = a;
		} else { //insert into front of deque
			CellNode after = head;
			after.prev = a;
			head = a;
			head.next = after;
 		}
 		count++;
 	}
	/*
	 * Inserts cell into the end of the deque.
	 * Parameters: p - The cell to be inserted.
	*/
	public void insertLast(Cell p) {
		
		//create a CellNode with Cell p to insert into deque
 		CellNode a = new CellNode(p);
 		if (count == 0){ //if the list is empty
			head = a;
			tail = a;
 		}else { //insert into back of deque
			//change old tail its next points to the new node
			tail.next = a;
			//change new node's prev so it points to old tail
			a.prev = tail;
			//update the deque's tail
			tail = a;
		}
		count++;
	}
	/*
	 * Removes the cell item at the front of the deque.
	 * Returns: the cell at the front.
	 * Throws: DequeEmptyException - if the deque is empty.
	*/
	public Cell removeFirst(){
 		if(!isEmpty()){ //deque is not empty
 			CellNode temp = new CellNode(head.item);
			//handles removal of only cell in deque
 			if(count == 1){
 				head = head.next;
 			} else { 			
 				head = head.next;
				head.prev = null;
			}
			count--;
 			return temp.item;
 		}else{
 			throw new DequeEmptyException("Deque exception on removeFirst: " 
 										+ "Deque empty");
 		}		
	}
	
	/*
	 * Removes the cell item at the end of the deque.
	 * Returns: the cell at the end.
	 * Throws: DequeEmptyException - if the deque is empty.
	*/
	public Cell removeLast(){
 		if(!isEmpty()){ //deque is not empty
 			CellNode temp = new CellNode(tail.item);
 			//handles removal of only cell in deque
 			if(count == 1){
 				tail = tail.prev;
 			} else {
 				tail = tail.prev;
				tail.next = null;
			}
			count--;
 			return temp.item;
 		}else{
 			throw new DequeEmptyException("Deque exception on removeLast: " 
 										+ "Deque empty");
 		}		
	}
	/*
	 * Returns: True if the deque is empty, false if it is not.
	*/
	public boolean isEmpty(){
		return count == 0;
	}
	/*
	 * Empties all the items from the deque.
	*/
	public void makeEmpty(){
		head = null;
		tail = null;
		count = 0;
	}

	/*
	 * A method that returns a string representation of the deque
	*/
	public String toString() {
		String details = "";
		if(head == null){
			details += "(empty)";
		}else{
			CellNode curr = head;	
			details += "[ " + curr.item;
			curr = curr.next;
			while(curr != null){
				details += " " + curr.item;
				curr = curr.next;
			}
			details += " ]\n";
		}
		return details;
	}	
 	/*
 	 * Internal test harness for this class.
 	*/
 	public static void main(String args[]) {
 		CellDeque test = new CellDeque();
 		Cell a = new Cell(0,0);
 		Cell b = new Cell(0,1);
 		Cell c = new Cell(0,2);
 		Cell d = new Cell(0,3);
 		Cell f = new Cell(0,4);
 		System.out.println("Trying to access first cell in empty deque...");
		try{
			test.first(); 
		} catch (DequeEmptyException e) {
			System.out.println("Correctly caught the exception");
		}
 		System.out.println("Trying to access last cell in empty deque...");
		try{
			test.last(); 
		} catch (DequeEmptyException e) {
			System.out.println("Correctly caught the exception");
		}
		System.out.println("Trying to remove first cell in empty deque...");
		try{
			test.removeFirst(); 
		} catch (DequeEmptyException e) {
			System.out.println("Correctly caught the exception");
		}
		System.out.println("Trying to remove last cell in empty deque...");
		try{
			test.removeLast(); 
		} catch (DequeEmptyException e) {
			System.out.println("Correctly caught the exception");
		}
		System.out.println("Testing isEmpty() on empty deque: " + test.isEmpty());
 		test.insertFirst(a);
 		test.insertFirst(b);
 		System.out.println("Adding (0,0) then (0,1) to front of deque" + "\n" + test);
 		test.insertLast(c);
 		test.insertLast(d);
 		test.insertLast(f);
 		System.out.println("Adding (0,2), (0,3) then (0,4) to back of deque" + "\n" + test);
 		Cell removed = test.removeFirst();
 		System.out.println("Removing first cell, returning: " + removed +"\n" + test);
 		Cell removed2 = test.removeLast();
 		System.out.println("Removing last cell, returning: " + removed2 +"\n" + test);
 		System.out.println("Testing isEmpty() on deque: " + test.isEmpty());
 		System.out.println("Testing makeEmpty() on deque...");
 		test.makeEmpty();
 		System.out.println(test);
 		System.out.println("Testing isEmpty() on deque: " + test.isEmpty());
 															
 		
 	}
 }
