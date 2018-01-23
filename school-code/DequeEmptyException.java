/**
 * DequeEmptyException.java
 * Created for CSC115 Assignment #4.
 */

/**
 * An exception meant to indicate that a Deque object is empty,
 * and an operation was requested cannot be completed.
 */
public class DequeEmptyException extends RuntimeException {

	/**
	 * Creates an exception.
	 * @param msg The message to the calling program.
	 */
	public DequeEmptyException(String msg) {
		super(msg);
	}

	/**
	 * Creates an exception without a message.
	 */
	public DequeEmptyException() {
		super();
	}
}
