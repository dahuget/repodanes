import java.util.NoSuchElementException;
import java.util.Vector;

/**
 * Name: DANA HUGET
 * ID: V00860786
 * Date: July 25, 2016
 * CSC115 Assignment 6 : The Emergency Room.
 * File Name: Heap.java.
 */

/*
 * The shell of the class, to be completed as part
 * of CSC115 Assignment 5: Emergency Room.
 */

public class Heap<E extends Comparable<E>> {

	private Vector<E> heapArray;
	//index of parent
	private int parent;
	//index of current item
	private int curr;
	
	/*
 	 * Constructs an empty vector with the specified initial capacity (10) 
 	 * and capacity increment (5). As the Vector reaches its size(10), it’s 
 	 * capacity gets incremented by 5.
 	*/
	public Heap() {
 	/* initialize the heapArray vector here */
 		heapArray = new Vector<E>(10, 5);	
 		heapArray.add(null); //first index is null
 		parent = 0;
 		curr = 0;
	}

	/*
	 * Determines if the heap is empty
	 * Returns: true if the heap is empty, false if it is not.
	*/
	public boolean isEmpty() {
		return size() == 0;
	}

	/*
	 * Determines the size of the heap
	 * Returns: the number of items in the heap.
	*/
	public int size() {
		return heapArray.size() - 1;
	}
	
	/*
	 * Removes the item at the root node of the heap.
	 * Returns: the item at the root of the heap.
	 * Throws: java.util.NoSuchElementException - if the heap is empty.
	 * Calls: swap and determineChild
	*/
	public E removeRootItem() {
		if(!isEmpty()){ //if heap is not empty
			E temp = heapArray.get(1);
			//if there is only one item in the heap
			if(size()==1){
				heapArray.remove(1);
			} else {
				heapArray.remove(1);
				//inserts last element into root position
				heapArray.add(1, heapArray.lastElement());
				//remove last item
				heapArray.remove(size());
				//moves item of lesser priority down the heap
				bubbleDown();
			}
			return temp;
		}else{
 			throw new NoSuchElementException("Heap exception on removeRootItem: " 
 										+ "Heap Empty");
 		}
	}
	
	/*
	 * Helper method used when removing the root item
	 * to ensure parent has higher precedence over child
	 * Calls: swap and determineChild
	*/	
	private void bubbleDown(){
		parent = 1; //the root
		//determines which child has precedence 
		curr = determineChild(parent);
		while(curr <= size() && 
			heapArray.get(curr).compareTo(heapArray.get(parent)) < 0){
			//swap parent item with current item
			swap(parent, curr);
			//set new parent and curr indexes
			parent = curr;
			curr = determineChild(parent);		
		}
	}
	
	/*
	 * Helper method to determine which child of a parent has 
	 * greater precedence
	 * Parameter: int a representing the index of parent item
	 * Returns: the index of child with greater precedence
	 * Called by bubbleDown
	*/	
	private int determineChild(int a){
		//children of current item
		int leftChild = a*2;
		int rightChild = a*2+1;
		//case when there are two children
		if(leftChild <= size() && rightChild <= size()){
			//if left child has greater precedence 
			if(heapArray.get(leftChild).compareTo(heapArray.get(rightChild)) < 0){
				return leftChild;
			} else {
				return rightChild;
			}
		}
		//case when there is no right child
		return leftChild;
	}
	/*
	 * Retrieves, without removing the item in the root.
	 * Returns: the top item in the tree.
	 * Throws: java.util.NoSuchElementException - if the heap is empty.
	*/
	public E getRootItem() {
		if(!isEmpty()){ //if heap is not empty
			return heapArray.get(1);
		}else{
 			throw new NoSuchElementException("Heap exception on getRootItem: " 
 										+ "Heap Empty");
 		}		
	}
	
	/*
	 * Inserts an item into the heap.
	 * Parameters: item - the newly added item.
	 * Calls: bubbleUp
	*/
	public void insert(E item) {
		//if heapArray is empty
		if(isEmpty()) {
			//add item to heapArray at last index
			heapArray.add(item);
		} else { 
			//add item to heapArray at last index
			heapArray.add(item);
			//moves item of higher priority up the heap
			bubbleUp();
		}	
	}
	
	/*
	 * Helper method used when inserting a new item
	 * to ensure parent has higher precedence over child
	 * Calls: swap
	*/	
	private void bubbleUp(){
		curr = size();
		parent = curr/2;
		//while parentIndex > 0,
		//compare value of item(last element) with its parent
		//if value of item < parent
		while(parent > 0 &&
			heapArray.get(curr).compareTo(heapArray.get(parent)) < 0){
			//swap current item and parent item
			swap(curr, parent);
			//set new curr and parent indexes
			curr = parent; 
			parent = curr/2;
		}
	}

	/*
	 * Helper method that swaps the position of two specified elements
	 * Parameters: two ints that represent the index of the items to be swapped
	 * Called by bubbleUp and bubbleDown
	*/	
	private void swap(int a, int b) {
		//store value at a in temp
		E temp = heapArray.get(a);
		//replace value at a with value at b
		heapArray.set(a, heapArray.get(b));
		//replace value at b with temp
		heapArray.set(b, temp);
	}
	
	/*
	 * String representation of the heapArray
	*/
 	public String toString() {
	 	return heapArray.toString();	
 	}
 	
 	/*
 	 * Used for internal testing purposes.
	 * Parameters: args - Not used.
 	*/
	public static void main(String[] args) {
		/*Heap<Integer> test = new Heap<Integer>();
		System.out.println(test.size());
		test.insert(11);
		test.insert(5);
		test.insert(8);
		test.insert(3);
		test.insert(4);
		//test.insert(15);
		System.out.println(test);*/
		
		//test 1
		Heap<Integer> testHeap = new Heap<Integer>();
		System.out.println("Empty heap: " + testHeap.isEmpty());
		System.out.println("Size of heap: "+ testHeap.size());
		System.out.println("Trying to access item at root in empty heap...");
		try{
			testHeap.getRootItem(); 
		} catch (NoSuchElementException e) {
			System.out.println("Correctly caught the exception");
		}
		System.out.println("Trying to remove item at root in empty heap...");
		try{
			testHeap.removeRootItem(); 
		} catch (NoSuchElementException e) {
			System.out.println("Correctly caught the exception");
		}
		testHeap.insert(4);
		System.out.println("After first insert, heap isEmpty: " + testHeap.isEmpty());
		testHeap.insert(6);
		testHeap.insert(2);
		testHeap.insert(8);
		testHeap.insert(5);
		testHeap.insert(9);
		testHeap.insert(1);
		System.out.println("After inserting 4, 6, 2, 8, 5, 9, 1...");
		System.out.println(testHeap);
		System.out.println("Size of heap: "+ testHeap.size());
		System.out.println("Get item at root: " + testHeap.getRootItem());
		while(!testHeap.isEmpty()){
			System.out.println("Removing root: " + testHeap.removeRootItem());
			System.out.println(testHeap);
		}
		System.out.println("Size of heap after removing all: "+ testHeap.size());
		System.out.println("Heap is now empty: "+testHeap.isEmpty());
		
	}
}
