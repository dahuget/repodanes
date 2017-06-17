import java.util.LinkedList;
import java.util.Iterator;

/**
 * Name: DANA HUGET
 * ID: V00860786
 * Date: July 10, 2016
 * CSC115 Assignment 5 : Fun With Binary Trees.
 * File Name: BinaryTreeIterator.java.
 */

/**
 * BinaryTreeIterator is an iterator specific to a BinaryTree.
 * One of three orders are available:
 * <ol>
 * <li>preorder</li>
 * <li>inorder</li>
 * <li>postorder</li>
 * </ol>
 */
public class BinaryTreeIterator<E> implements Iterator<E> {

	private TreeNode<E> root;
	private E curr;
	private LinkedList<E> list;

	/**
	 * Non-public constructor : only a BinaryTree is allowed access.
	 * Creates a Binary tree iterator.
	 * @param order One of the three orders: inorder, preorder, or postorder.
	 * @param root The root of the BinaryTree.
	 * @throws TreeException if any of the actual parameters are not valid.
	 */
	BinaryTreeIterator(String order, TreeNode<E> root) {
		this.root = root;
		curr = null;
		list = new LinkedList<E>();
		switch(order) {
			case "preorder":
				preorder(root);
				break;
			case "inorder":
				inorder(root);
				break;
			case "postorder":
				postorder(root);
				break;
			default:
				throw new TreeException("unable to assess the interator order:"+
					" choose {inorder, inorder, postorder");
		}
	}

	/* Required methods from Iterator.  Comments inherited from that class.*/

	// One of the Iterator required methods.
	public boolean hasNext() {
		return list.size() != 0;
	}

	// One of the Iterator required methods.
	public E next() {
		return list.remove();
	}

	/**
	 * Even though this method is required by the Iterator, 
	 * it should never be allowed.
	 * No one should remove an item during a traversal.
	 */
	public void remove() {
		throw new UnsupportedOperationException();
	}

	/**
	 * Recursive helper method.
	 * Fills the list with tree items in 'pre' order.
	 * @param node The root node of a subtree.
	 */
	private void preorder(TreeNode<E> node) {	
		// base case - add nodes to list if they are not null
		if (node != null) {
			//add node first
			list.add(node.item);
			// recursive calls
			preorder(node.left);
			preorder(node.right);
		}
	// printing out the list will print out the nodes in preorder 
	}

	/**
	 * Recursive helper method.
	 * Fills the list with tree items in 'in' order.
	 * @param node The root node of a subtree.
	 */
	private void inorder(TreeNode<E> node) {
		// base case - add nodes to list if they are not null
		if (node != null) {
			// recursive calls add left, node then right
			inorder(node.left);
			list.add(node.item);	
			inorder(node.right);
		}
	// printing out the list will print out the nodes in inorder
	}

	/**
	 * Recursive helper method.
	 * Fills the list with tree items in 'post' order.
	 * @param node The root node of a subtree.
	 */
	private void postorder(TreeNode<E> node) {
		// base case - add nodes to list if they are not null
		if (node != null) {
			// recursive calls add left then right
			postorder(node.left);
			postorder(node.right);
			// adds node last
			list.add(node.item);
		}	
	// printing out the list will print out the nodes in postorder
	}

}
