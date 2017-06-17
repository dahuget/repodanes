import java.util.NoSuchElementException;

/**
 * Name: DANA HUGET
 * ID: V00860786
 * Date: July 25, 2016
 * CSC115 Assignment 6 : The Emergency Room.
 * File Name: PriorityQueue.java.
 */

public class PriorityQueue<E extends Comparable<E>> {
	
	private Heap<E>  heap;
	
	/*
	 * Constructor: Creates an empty priority queue.
	*/
	public PriorityQueue() {
 		heap = new Heap<E>();
	}
	
	/*
	 * Inserts an item into the queue. 
	 * Parameters: item - The item to insert.
	*/
	public void enqueue(E item) {
		heap.insert(item);
	}
	
	/*
	 * Removes the highest priority item from the queue.
	 * Returns: The item.
	 * Throws: java.util.NoSuchElementException - if the queue is empty.
	*/
	public E dequeue() {
		if(!isEmpty()){ //if queue is not empty
			E temp = heap.getRootItem();
			heap.removeRootItem();
			return temp;
		}else{
 			throw new NoSuchElementException("Queue exception on dequeue: " 
 										+ "Queue Empty");
 		}
	}
	
	/* 
	 * Retrieves, but does not remove the next item out of the queue. 
	 * Returns: the item with the highest priority in the queue.
	 * Throws: java.util.NoSuchElementException - if the queue is empty.
	*/
	public E peek() {
		if(!isEmpty()){ //if queue is not empty
			return heap.getRootItem();
		}else{
 			throw new NoSuchElementException("Queue exception on peek: " 
 										+ "Queue Empty");
 		}	
	}
	
	/*
	 * Returns: True if the queue is empty, false if it is not.
	*/
	public boolean isEmpty() {
		return heap.isEmpty();
	}

	/*
	 * String representation of the heapArray
	*/
 	public String toString() {
	 	return heap.toString();	
 	}
 	
 	/*
 	 * Used for internal testing purposes.
	 * Parameters: args - Not used.
 	*/
	public static void main(String[] args) {
		//test priority queue, first index is null
		PriorityQueue<ER_Patient> test = new PriorityQueue<ER_Patient>();
		System.out.println("Current Queue: " + test);
		System.out.println("Testing isEmpty: " + test.isEmpty());
		ER_Patient[] patients = new ER_Patient[5];
		String[] complaints = {"Walk-in", "Life-threatening","Chronic","Major fracture", "Chronic"};
		for (int i=0; i<5; i++) {
			patients[i] = new ER_Patient(complaints[i]);
			// spread out the admission times by 1 second
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				System.out.println("sleep interrupted");
				return;
			}
		}
		System.out.println("Patients waiting:");
		for (int i=0; i<5; i++) {
			System.out.println(patients[i]);
		}
		for (int i=0; i<5; i++) {
			test.enqueue(patients[i]);
		}
		System.out.println("Priority Queue: \n" + test);
		System.out.println("Priority Queue is empty: " + test.isEmpty());
		System.out.println("Peek at next item out of queue: " + test.peek());
		//dequeue patients in order of priority
		while(!test.isEmpty()) { //while queue is not empty
			System.out.println("Dequeue priority patient: " + test.dequeue());
		}
		System.out.println("Queue is now empty: " + test.isEmpty());
	}		
}
	
