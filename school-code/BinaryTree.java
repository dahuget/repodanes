import java.util.Iterator;

/**
 * Name: DANA HUGET
 * ID: V00860786
 * Date: July 10, 2016
 * CSC115 Assignment 5 : Fun With Binary Trees.
 * File Name: BinaryTree.java.
 */

/**
 * BinaryTree is a basic generic BinaryTree data structure.
 * It is referenced based, using TreeNodes to connect the tree.
 * It contains any element determined by the placeholder E.
 * The basic ADT is adapted from <i>Java, Walls &amp; Mirrors,</i> by Prichard and Carrano.
 */
public class BinaryTree<E> {

	/* The root is inherited by any subclass
	 * so it must be protected instead of private.
	 */
	protected TreeNode<E> root;

	/**
	 * Create an empty BinaryTree.
	 */
	public BinaryTree() {
	}

	/**
	 * Create a BinaryTree with a single item.
	 * @param item The only item in the tree.
	 */
	public BinaryTree(E item) {
		root = new TreeNode<E>(item);
	}

	/**
	 * Used only by subclasses and classes in the same package (directory).
	 * @return The root node of the tree.
	 */
	protected TreeNode<E> getRoot() {
		return root;
	}
	
	/**
	 * Creates a binary tree from a single item for the root and two subtrees.
	 * Once the two subtrees are attached, their references are nullified to prevent
	 * multiple entries into <i>this</i> tree.
	 * @param item The item to be inserted into the root of this tree.
	 * @param leftTree A binary tree, which may be empty.
	 * @param rightTree A binary tree, which may be empty.
	 */
	public BinaryTree(E item, BinaryTree<E> leftTree, BinaryTree<E> rightTree) {
		root = new TreeNode<E>(item);
		attachLeftSubtree(leftTree);
		attachRightSubtree(rightTree);
	}

	/**
	 * Attaches a subtree to the left of the root node, replacing any subtree that was 
	 * previously there.
	 * @param left The new left subtree of the root.
	 *	This subtree may be empty.
	 *	Once attached, this parameter reference is nullified to prevent multiple
	 *	access to <i>this</i> tree.
	 * @throws TreeException if <i>this</i> tree is empty.
	 */
	public void attachLeftSubtree(BinaryTree<E> left) {
		if (root == null) {
			throw new TreeException("Cannot attach to an empty tree.");
		}
		if (left == null) {
			return;
		}
		root.left = left.root;
		left.root.parent = root;
		left = null;
	}

	/**
	 * @return a preorder iterator of the binary tree.
	 */
	public Iterator<E> preorderIterator() {
		return new BinaryTreeIterator<E>("preorder",root);
	}

	/**
	 * @return an inorder iterator of the binary tree.
	 */
	public Iterator<E> inorderIterator() {
		return new BinaryTreeIterator<E>("inorder", root);
	}

	/**
	 * @return a postorder iterator of the binary tree.
	 */
	public Iterator<E> postorderIterator() {
		return new BinaryTreeIterator<E>("postorder",root);

	}

	/*
 	 *  Returns true if the tree is empty, else returns false.
 	*/
	public boolean isEmpty() {
		return root == null;
	}
	 
	/**
	 * Attaches a subtree to the right of the root node, replacing any subtree that was 
	 * previously there.
	 * @param right The new right subtree of the root.
	 *	This subtree may be empty.
	 *	Once attached, this parameter reference is nullified to prevent multiple
	 *	access to <i>this</i> tree.
	 * @throws TreeException if <i>this</i> tree is empty.
	 */
	public void attachRightSubtree(BinaryTree<E> right) {
		if (root == null) {
			throw new TreeException("Cannot attach to an empty tree.");
		}
		if (right == null) {
			return;
		}
		root.right = right.root;
		right.root.parent = root;
		right = null;	
	}
	
 /*
 *  Returns the height of the tree using recursive helper method.
 */		
	public int height() {
		// starts recursive calls
		return height(root);
	}
	
	/* 
	 * NOTE TO STUDENT:
	 * The height of a tree is recursively defined as:
	 * 1 + the maximum (height of the left subtree rooted at node
	 *		or height of right subtree rooted at node.
	 */
	private int height(TreeNode<E> node) {
		// base case
		if (node == null){
			return 0;
		}
		// recursive calls
		int leftHeight = height(node.left);
		int rightHeight = height(node.right);
		if(leftHeight > rightHeight){
			return leftHeight + 1;
		} else {
			return rightHeight + 1;
		}
	}

	/* NOTE TO STUDENT:
	 * You do not need to implement a main method for this class
	 * To test your BinaryTree, compile and run the Expression class.
	 */
	 public static void main(String[] args){

		Integer s1 = new Integer(1);
		BinaryTree<Integer> tree = new BinaryTree<Integer>(s1);
		Integer s2 = new Integer(2);
		BinaryTree<Integer> treeL = new BinaryTree<Integer>(s2);
		tree.attachLeftSubtree(treeL);
		Integer s3 = new Integer(3);
		BinaryTree<Integer> treeR = new BinaryTree<Integer>(s3);
		tree.attachRightSubtree(treeR);
		System.out.println("Tree height, should be 2: " + tree.height());
		
		DrawableBTree<Integer> draw = new DrawableBTree<Integer>(tree);
		draw.showFrame();
		
		
		Iterator<Integer> it = tree.inorderIterator();
		System.out.println("printing out the inorder contents of the tree:");
		while (it.hasNext()) {
			System.out.print(it.next()+" ");
		} 
		System.out.println();
		Iterator<Integer> it2 = tree.preorderIterator();
		System.out.println("printing out the preorder contents of the tree:");
		while (it2.hasNext()) {
			System.out.print(it2.next()+" ");
		} 
		System.out.println();
		Iterator<Integer> it3 = tree.postorderIterator();
		System.out.println("printing out the postorder contents of the tree:");
		while (it3.hasNext()) {
			System.out.print(it3.next()+" ");
		} 
		System.out.println();
	 }

}

	
