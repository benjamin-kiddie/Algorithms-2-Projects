/**
 * A very very basic driver for CS1501 Project 1
 * @author	Dr. Farnan
 */
package project_1;

public class App {
	public static void main(String[] args) {
		BST<Integer> tree = new BST<Integer>();
		System.out.println("Successfully built an empty tree!");

		tree.put(8);
		tree.put(12);
		tree.put(14);
		tree.put(15);
		tree.put(13);
		tree.put(10);
		tree.put(11);
		tree.put(9);
		tree.put(4);
		tree.put(6);
		tree.put(7);
		tree.put(5);
		tree.put(2);
		tree.put(3);
		tree.put(1);
		
		/*for (int i = 1; i < 11; i++) {
			tree.put(i);
		}*/

		System.out.println("Initial Tree - " + tree.inOrderTraversal());
		System.out.println("Serialization - " + tree.serialize());
		System.out.println("Height of Tree - " + tree.height());
		System.out.println("Balanced? " + tree.isBalanced());

		BST<Integer> ReversedTree = tree.reverse();
		System.out.println("\nReversed Tree - " + ReversedTree.inOrderTraversal());

		tree.delete(8);
		System.out.println("Tree After Deletion - " + tree.inOrderTraversal());

	}
}
