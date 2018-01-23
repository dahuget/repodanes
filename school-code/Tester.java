import java.util.Iterator;

public class Tester {
	public static void main(String[] args) {
		TreeNode<Integer> zero = new TreeNode<Integer>(0);
		TreeNode<Integer> one = new TreeNode<Integer>(1);
		TreeNode<Integer> two = new TreeNode<Integer>(2);
		TreeNode<Integer> three = new TreeNode<Integer>(3);
		TreeNode<Integer> four = new TreeNode<Integer>(4);
		TreeNode<Integer> five = new TreeNode<Integer>(5);
		TreeNode<Integer> six = new TreeNode<Integer>(6);
		TreeNode<Integer> seven = new TreeNode<Integer>(7);
		TreeNode<Integer> eight = new TreeNode<Integer>(8);
		TreeNode<Integer> nine = new TreeNode<Integer>(9);
		TreeNode<Integer> ten = new TreeNode<Integer>(10);
		
		
		
		/*
		BinarySearchTree<Integer> myTree = new BinarySearchTree<Integer>(5);
		//we want 2 to go to the left (compare 2 to 5 will give negative number)
		myTree.insert(2);
		myTree.insert(8);
		myTree.insert(6);
		DrawableBTree<Integer> draw = new DrawableBTree<Integer>(myTree);
		draw.showFrame();
		System.out.println(myTree.height());
		*/
		
		/*
		BinaryTree<Integer> myTree = new BinaryTree<Integer>(5);
		myTree.root.left = three;
		myTree.root.right = eight;
		DrawableBTree<Integer> draw = new DrawableBTree<Integer>(myTree);
		draw.showFrame();
		System.out.println(myTree.height());
		Iterator<Integer> it = myTree.inorderIterator();
		System.out.println("printing out the inorder contents of the tree:");
		while (it.hasNext()) {
			System.out.print(it.next()+" ");
		} 
		System.out.println();
		*/
		
		
		BinarySearchTree<Integer> myTree = new BinarySearchTree<Integer>(5);
		two.left = zero;
		two.right = three;
		three.right = four;
		nine.right = ten;
		nine.left = seven;
		myTree.root.left = two;
		myTree.root.right = nine;		
		myTree.insert(1);
		myTree.insert(6);
		DrawableBTree<Integer> draw = new DrawableBTree<Integer>(myTree);
		draw.showFrame();
		System.out.println(myTree.height());
		System.out.println("retrieving 4"+ myTree.retrieve(4));
		System.out.println("retrieving 8"+ myTree.retrieve(8));
		
		
		
		System.out.println("comparing 3 to 10");
		System.out.println(three.item.compareTo(ten.item));
		System.out.println("comparing 10 to 3");
		System.out.println(ten.item.compareTo(three.item));
		System.out.println("comparing 3 to 3");
		System.out.println(three.item.compareTo(three.item));
		
			
	}

}
