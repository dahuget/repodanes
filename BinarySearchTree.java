import java.util.Iterator;

/**
 * Name: DANA HUGET
 * ID: V00860786
 * Date: July 10, 2016
 * CSC115 Assignment 5 : Fun With Binary Trees.
 * File Name: BinarySearchTree.java.
 */

/**
 * BinarySearchTree is an ordered binary tree, where the element in each node
 * comes after all the elements in the left subtree rooted at that node
 * and before all the elements in the right subtree rooted at that node.
 * For this assignment, we can assume that there are no elements that are
 * identical in this tree. 
 * A small modification will allow duplicates.
 */
public class BinarySearchTree<E extends Comparable<E>> extends BinaryTree<E> {

	// the root is inherited from BinaryTree.

	/**
	 * Create an empty BinarySearchTree.
	 */
	public BinarySearchTree() {
		super();
	}

	/**
	 * Creates a BinarySearchTree with a single item.
	 * @param item The single item in the tree.
	 */
	public BinarySearchTree(E item) {
		super(item);
	}

	/**
 	 * <b>This method is not allowed in a BinarySearchTree.</b>
	 * It's description from the subclass:<br>
	 * <br>
	 * {@inheritDoc}
	 * @throws UnsupportedOperationException if this method is invoked.
	 */
	public void attachLeftSubtree(BinaryTree<E> left) {
		throw new UnsupportedOperationException();
	}

	public void attachRightSubtree(BinaryTree<E> right) {
		throw new UnsupportedOperationException();
	}
	
	/*
	 * Inserts a new item into the tree, maintaining its order. 
	 * If the item already exists in the tree, nothing happens.
	 * Parameters: item - the newest item.
	*/
	public void insert(E item) {
		// calls recursive method
		root = insert(item, root);
	}
	   	
	// private recursive helper method
	// assume duplicate item will not be added
	private TreeNode<E> insert(E item, TreeNode<E> cur) {
		// base case
		if(cur == null){
			TreeNode<E> toAdd = new TreeNode<E>(item);
			return toAdd;
		}
		// compare item and cur
		if(item.compareTo(cur.item) < 0){
			// insert to the left
			cur.left = insert(item, cur.left);
			cur.left.parent = cur;
		} else if (item.compareTo(cur.item) > 0){
			// insert to the right
			cur.right = insert(item, cur.right);
			cur.right.parent = cur;
		}
		// will not add duplicate item
		return cur;
	}

	/*
	 * Looks for the item in the tree that is equivalent to the item.
	 * Parameters: item - the item that is equivalent to the item we are looking for. 
	 * Equality is determined by the equals method of the item.
	 * Returns: the item if it's in the tree, null if it is not.
	*/
	public E retrieve(E item) {
		//recursive call
		TreeNode<E> temp = retrieve(item, root);
		if(temp == null){
			return null;
		}
		return temp.item;
	}
	
	// private recursive helper method
	private TreeNode<E> retrieve(E item, TreeNode<E> cur){
		TreeNode<E> temp = null;
		// returns null if item is not in the tree
		if(cur == null){
			return null;
		} else {
			// items are equal, base case
			if(item.compareTo(cur.item) == 0){
				//System.out.println("base case");
				//System.out.println("returning cur " + cur.item);
				return cur;
			}
			// item is less than cur when -1
			if(item.compareTo(cur.item) < 0){
				// search the left subtree
				//System.out.println("left recursion");
				temp = retrieve(item, cur.left);
			// item is greater than cur when +1 aka (item.compareTo(cur.item) > 0)
			} else {
				// search the right subtree
				//System.out.println("right recursion");
				temp = retrieve(item, cur.right);
			}
		}
		return temp;
	}
	
	/*
	 * Finds the item that is equivalent to the item and removes it, if it's in the tree.
	 * Parameters: item - the item that is equivalent to the item we are looking for. 
	 * Equality is determined by the equals method of the item.
	 * Returns: the actual item that was removed, or null if it is not in the tree.
	*/
	public E delete(E item) {
		//finds node to delete
		TreeNode<E> toDelete = retrieve(item, root);
		E temp = null;
		if(toDelete == null){
			return temp;
		} else {
			System.out.println("found node to delete: " + toDelete.item);
			temp = toDelete.item;
			// calls: deleteNode method
			deleteNode(toDelete);
		}
		return temp;	
	}
	
	// private helper method that deletes a node
	// three different types of node exist and this method handles them
	// when dealing with 2 children this method calls two other methods to find 
	// and and replace the leftmost inorder successor
	private TreeNode<E> deleteNode(TreeNode<E> cur) {
		//case 1: node is a leaf
		//case 2: node has one child
		//case 3: node has two children
		E replacementItem;
		
		//test for case 1
		if(cur.left == null && cur.right == null){
			System.out.println(cur.item + " node has no children");
			//if cur is the right node of its parent, set to null
			if(cur.parent.right == cur){
				cur.parent.right = null;
			} //else cur is the left node, set to null
			else {
				cur.parent.left = null;
			}
			return cur;
		} //test for case 2
		else if(cur.left == null){ // has a right child
			System.out.println(cur.item + " node has a right child");
			cur.parent.right = cur.right;
			return cur;
		} else if(cur.right == null){ // has a left child
			System.out.println(cur.item + " node has a left child");
			cur.parent.left = cur.left;
			return cur;
		} //test for case 3
		// retrieve and delete the inorder successor
		else {
			System.out.println(cur.item + " node has two children");
			replacementItem = findLeftmost(cur.right);
			cur.item = replacementItem;
			cur.right = deleteLeftmost(cur.right);
			return cur;
		}
	}
	
	// helper method finds leftmost inorder successor
	private E findLeftmost(TreeNode<E> cur){
		if(cur.left == null){
			return cur.item;
		} else {
			return findLeftmost(cur.left);
		}
	}
	// helper method deletes leftmost inorder successor
	private TreeNode<E> deleteLeftmost(TreeNode<E> cur){
		if(cur.left == null){
			return cur.right;
		} else {
			cur.left = deleteLeftmost(cur.left);
			return cur;
		}
	}

	/**
	 * Internal test harness.
	 * @param args Not used.
	 */
	public static void main(String[] args) {
		BinarySearchTree<String> tree = new BinarySearchTree<String>();
		String s1 = new String("optimal");
		String s2 = new String("fruit");
		String s3 = new String("programmers");
		String s4 = new String("CSC115");
		String s5 = new String("needs");
		String s6 = new String("pepper");
		tree.insert(s1);
		tree.insert(s2);
		tree.insert(s3);
		tree.insert(s4);
		tree.insert(s5);
		tree.insert(s6);
		Iterator<String> it = tree.inorderIterator();
		System.out.println("printing out the contents of the tree in sorted order:");
		while (it.hasNext()) {
			System.out.print(it.next()+" ");
		} 
		System.out.println();
		String test = tree.retrieve("fruit");
		if (test != null && !test.equals("")) {
			System.out.println("retrieving the node that contains "+s2);
			if (test.equals(s2)) {
				System.out.println("Confirmed");
			} else {
				System.out.println("retrieve returns the wrong item");
			}
		} else {
			System.out.println("retrieve returns nothing when it should not");
		}
		String test2 = tree.retrieve("coconut");
		if(test2 == null){
			System.out.println("cannot retrieve coconut, it's not in the tree");
		}
		
		test = tree.delete("needs");
		if (test != null && !test.equals("")) {
			System.out.println("retrieving the node that contains "+s5 + " to be deleted");
			if (test.equals(s5)) {
				System.out.println("Confirmed");
			} else {
				System.out.println("delete returns the wrong item");
			}
		} else {
			System.out.println("delete returns nothing when it should not");
		}

		System.out.println("After deleting needs,");
		it = tree.inorderIterator();
		System.out.println("printing out the contents of the tree in sorted order:");
		while (it.hasNext()) {
			System.out.print(it.next()+" ");
		}
		System.out.println();
		test2 = tree.delete("coconut");
		if(test2 == null){
			System.out.println("deleting \"coconut\"...coconut was not found in the tree");
		}
		tree.delete("fruit"); 
		System.out.println("After deleting fruit,");
		it = tree.inorderIterator();
		System.out.println("printing out the contents of the tree in sorted order:");
		while (it.hasNext()) {
			System.out.print(it.next()+" ");
		} 
		System.out.println();
		tree.delete("optimal"); 
		System.out.println("After deleting optimal,");
		it = tree.inorderIterator();
		System.out.println("printing out the contents of the tree in sorted order:");
		while (it.hasNext()) {
			System.out.print(it.next()+" ");
		} 
		System.out.println();
		it = tree.preorderIterator();
		System.out.println("printing out the contents of the tree in preorder:");
		while (it.hasNext()) {
			System.out.print(it.next()+" ");
		} 
		System.out.println();
		it = tree.postorderIterator();
		System.out.println("printing out the contents of the tree in postorder:");
		while (it.hasNext()) {
			System.out.print(it.next()+" ");
		} 
		System.out.println();
		DrawableBTree<String> dbt = new DrawableBTree<String>(tree);
		dbt.showFrame();	
	}
}

	

	
