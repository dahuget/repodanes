// LAB 5

public class CircularArrayQueue {
	private static final int MAX_SIZE=5;
	int[] storage;
	int front;
	int back;

	public CircularArrayQueue() {
		storage = new int[MAX_SIZE];
		front = -1;
		back = -1;
	}
		
	public void enqueue(int item) {
		if((back + 1)%MAX_SIZE == front){ //if stack is full
			System.out.println("Sorry, cannot add to full queue");
		} else if(isEmpty()){ //insert into first spot if empty, set front & back
			front = 0;
			back= 0;
			storage[0] = item;
		//make sure we don't insert outside the bounds of the array
		} else { // increment back, put item into array
			back = (back + 1) % MAX_SIZE;
			storage[back] = item;
		}
	}
	
	public int dequeue() {
		if(isEmpty()){
			System.out.println("Cannot dequeue, queue is empty");
			return -1;
		} else if(front == back){
			int temp = storage[front];
			front = -1;
			back = -1;
			return temp;
		//increment front, make sure doesn't exceed array size
		} else {
			int temp = storage[front];
			front = (front + 1) % MAX_SIZE;
			return temp;
		}
	}
	
	public boolean isEmpty() {
		return (front == -1);
	}
	
	public void dequeueAll() {
		front = -1;
		back = -1;
	}
	
	public int peek() {
		return -1;
	}
	
	public String toString() {
		if (isEmpty()) {
			return "The queue is empty";
		}
		String details = "{";
		if (!isEmpty()) {
			for (int i = front; i != back; i = (i+1)%MAX_SIZE) {
				details = details + storage[i]+", ";
			}
			details = details + storage[back];
		}
		details = details + "}\n";
		return details;
	}
	
	public static void main(String[] args) {
		CircularArrayQueue myQ = new CircularArrayQueue();
		
		//STEP 1: Check enqueue method		
		System.out.println("Testing enqueue: adding 7, 4, 8");
		myQ.enqueue(7);
		myQ.enqueue(4);
		myQ.enqueue(8);
		System.out.println(myQ);
		
		//STEP 2: Check dequeue method
		System.out.println("Testing dequeue");
		myQ.dequeue();
		System.out.println(myQ);
		
		//STEP 3: Can 3 more items be added?
		System.out.println("Testing wrap-around enqueue: 2, 7, 6");
		myQ.enqueue(2);
		myQ.enqueue(7);
		myQ.enqueue(6);
		System.out.println(myQ);
		
		//STEP 4: Can items be added to a full queue?
		System.out.println("Testing full enqueue: adding 1");
		myQ.enqueue(1);
		System.out.println(myQ);

		//Step 5: More checks for enqueue and dequeue wrap-around.
		System.out.println("More enqueue and dequeue: dq, dq, eq(3), dq, eq(4)");
		myQ.dequeue();
		myQ.dequeue();
		myQ.enqueue(3);
		myQ.dequeue();
		myQ.enqueue(4);
		System.out.println(myQ);
		System.out.println("dq, dq, dq");
		myQ.dequeue();
		myQ.dequeue();
		myQ.dequeue();
		System.out.println(myQ);
		
		//Step 6: Emptying queue
		System.out.println("Testing isEmpty: 2 dequeues");
		myQ.dequeue();
		myQ.dequeue();
		System.out.println(myQ);	
	}	
}
