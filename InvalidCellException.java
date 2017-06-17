/**
 * Name: DANA HUGET
 * ID: V00860786
 * Date: June 26, 2016
 * 
 * InvalidCellException.java
 * Created for CSC115 Assignment #4.
 */

/**
 * An exception meant to indicate that a Cell being added to the deque is invalid,
 * and an operation was requested cannot be completed.
 */
public class InvalidCellException extends RuntimeException {

	/**
	 * Creates an exception.
	 * @param msg The message to the calling program.
	 */
	public InvalidCellException(String msg) {
		super(msg);
	}

	/**
	 * Creates an exception without a message.
	 */
	public InvalidCellException() {
		super();
	}
}
