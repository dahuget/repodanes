/**
 * CellNode.java
 * Created for CSC115 Assignment #4.
 */

/**
 * CellNode is a protected class to be used by a Reference-Based
 * Deque.
 */
class CellNode {
	
	Cell item;
	CellNode next;
	CellNode prev;

	public CellNode(Cell item, CellNode prev, CellNode next) {
		this.item = item;
		this.prev = prev;
		this.next = next;
	}

	public CellNode(Cell item) {
		this(item,null,null);
	}
}
