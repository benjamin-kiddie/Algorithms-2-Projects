/**
 * BST implementation for project 1 of CS 1501
 * @author Benjamin Kiddie 
 */
package project_1;

public class BST<T extends Comparable<T>> implements BST_Inter<T> {
    
    /**
     * Root of BST
     */
    private BTNode<T> root;

    /**
	 * Add a new key to the BST
	 *
	 * @param   key Generic type value to be added to the BST
	 */
	public void put(T key) {
        this.root = this.recursivePut(this.root, key);
    }

    /**
     * Helper function for put(). Recursively steps through BST,
     * then inserts key before returning
     *
     * @param   curr Node currently being operated on
     * @param   key  Generic value being added to the BST
     * 
     * @return  Current BTNode once insertion is complete
     */
    private BTNode<T> recursivePut(BTNode<T> curr, T key) {
        if (curr == null)
            return new BTNode<T>(key);
        else if (curr.getKey().compareTo(key) > 0)
            curr.setLeft(recursivePut(curr.getLeft(), key));
        else if (curr.getKey().compareTo(key) < 0)
            curr.setRight(recursivePut(curr.getRight(), key));
        return curr;
    }

    /**
	 * Check if the BST contains a key
	 *
	 * @param	key Generic type value to look for in the BST
	 *
	 * @return	true if key is in the tree, false otherwise
	 */
	public boolean contains(T key) {
        return this.recursiveContains(this.root, key);
    }

    /**
     *
     * Helper function for contains(). Recursively steps through
     * BST checking for key.
     *
     * @param   curr Current node being operated on
     * @param   key  Generic type value to look for in BST
     *
     * @return  true if key is present, false otherwise
     */
    private boolean recursiveContains(BTNode<T> curr, T key) {
        if (curr == null)
            return false;
        else if (curr.getKey().compareTo(key) > 0)
            return recursiveContains(curr.getLeft(), key);
        else if (curr.getKey().compareTo(key) < 0)
            return recursiveContains(curr.getRight(), key);
        else
            return true;
    }

    /**
	 * Remove a key from the BST, if key is present
	 * 
	 * @param	key Generic type value to remove from the BST
	 */
	public void delete(T key) {
        root = recursiveDelete(this.root, key);
    }

    /**
     * Helper function for delete(). Recursively steps through BST
     * and deletes node with selected key within it.
     *
     * @param   curr Current node being operated on
     * @param   key Generic type value to remove from the BST 
     * 
     * @return Current BTNode when deletion is complete
     */
    private BTNode<T> recursiveDelete(BTNode<T> curr, T key) {
        // base case: tree is empty
        if (curr == null)
            return curr;
        // step through if key is less than or greater than
        else if (curr.getKey().compareTo(key) > 0)
            curr.setLeft(recursiveDelete(curr.getLeft(), key));
        else if (curr.getKey().compareTo(key) < 0)
            curr.setRight(recursiveDelete(curr.getRight(), key));
        
        // target node found
        else {
            // if only one child or no children, replace node with child/null
            if (curr.getLeft() == null)
                return curr.getRight();
            else if (curr.getRight() == null)
                return curr.getLeft();
            // otherwise, find highest value to the left of curr and replace curr with it
            else {
                BTNode<T> replacement = findReplacement(curr.getLeft());
                // make new node using key of replacement
                BTNode<T> newNode = new BTNode<T>(replacement.getKey());
                newNode.setRight(curr.getRight());
                newNode.setLeft(recursiveDelete(curr.getLeft(), replacement.getKey()));
                // finish by setting newNode as child of curr's parent
                return newNode;
            }
        }
        // step back
        return curr;
    }
    
    /**
     * Helper function for recursive_delete(). Finds rightmost node
     * left of the node being deleted.
     * 
     * @param   curr Current node being operated on
     */
    private BTNode<T> findReplacement(BTNode<T> curr) {
        if (curr.getRight() == null)
            return curr;
        return findReplacement(curr.getRight());
    }
    
    /**
	 * Determine the height of the BST
	 *
	 * <p>
	 * A single node tree has a height of 1, an empty tree has a height of 0.
	 *
	 * @return	int value indicating the height of the BST
	 */
	public int height() {
        return recursiveHeight(this.root);
    }

    /**
     * Helper function for height(). Steps through BST recursively to
     * determine height.
     * 
     * @param   curr BST node currently being operated on
     *
     * @return  height of BST as an int
     */
    private int recursiveHeight(BTNode<T> curr) {
         // base case: passed leaf
        if (curr == null)
            return 0;
        // otherwise, find max height between right and left subtrees
        else {
            int heightL = recursiveHeight(curr.getLeft());
            int heightR = recursiveHeight(curr.getRight());
            // return the greater of the two
            if (heightL >= heightR)
                return heightL + 1;
            else 
                return heightR + 1;
        }
    }

    /**
	 * Determine if the BST is height-balanced
	 *
	 * <p>
	 * A height balanced binary tree is one where the left and right subtrees
	 * of all nodes differ in height by no more than 1.
	 *
	 * @return	true if the BST is height-balanced, false if it is not
	 */
	public boolean isBalanced() {
        return recursiveIsBalanced(this.root);
    }

    /**
     * Helper function for isBalanced(). Recursively steps through
     * BST to determine if the BST is height-balanced.
     * 
     * @param   curr Current node being operated on
     * 
     * @return  true if tree is height balanced, false otherwise
     */
    private boolean recursiveIsBalanced(BTNode<T> curr) {
        // base case: end of tree reached, return true
        if (curr == null)
            return true;
        // otherwise, we need to examine both subtrees
        // if both trees have a height differential of <=|1|, examine both subtrees
        else if (recursiveHeight(curr.getLeft()) - recursiveHeight(curr.getRight()) >= -1
            && (recursiveHeight(curr.getLeft()) - recursiveHeight(curr.getRight())) <= 1)
            return (recursiveIsBalanced(curr.getRight()) && recursiveIsBalanced(curr.getLeft()));
        // failing this, the tree is not height balanced
        else
            return false;
    }

    /**
	 * Produce a ':' separated String of all keys in ascending order
	 *
	 * <p>
	 * Perform an in-order traversal of the tree and produce a String
	 * containing the keys in ascending order, separated by ':'s.
	 * 
	 * @return	String containing the keys in ascending order, ':' separated
	 */
	public String inOrderTraversal() {
        // base case: empty tree
        if (this.root == null)
            return "";
        // otherwise, recursively step through BST
        else {
            String result = recursiveIOT(this.root);
            // removing extra colon
            return result.substring(0, (result.length() - 1));
        }
    }

    /**
     * Helper function for inOrderTraversal. Resursively steps through
     * BST to create string summary of keys.
     *
     * @param   curr BST node currently being operated on
     *
     * @return  String containing keys explored so far
     */
    private String recursiveIOT(BTNode<T> curr) {
        if (curr == null)
            return "";
        return recursiveIOT(curr.getLeft()) + curr.getKey() 
            + ":" + recursiveIOT(curr.getRight());
    }

    /**
	 * Produce String representation of the BST
	 * 
	 * <p>
	 * Perform a pre-order traversal of the BST in order to produce a String
	 * representation of the BST. The reprsentation should be a comma separated
	 * list where each entry represents a single node. Each entry should take
	 * the form: *type*(*key*). You should track 4 node types:
	 *     `R`: The root of the tree
	 *     `I`: An interior node of the tree (e.g., not the root, not a leaf)
	 *     `L`: A leaf of the tree
	 *     `X`: A stand-in for a null reference
	 * For each node, you should list its left child first, then its right
	 * child. You do not need to list children of leaves. The `X` type is only
	 * for nodes that have one valid child.
	 * 
	 * @return	String representation of the BST
	 */
	public String serialize() {
        // make initial serialzization
        String serialization = recursive_serialize(this.root);
        // this has an extra comma and an incorrect I for the root, so we'll fix that
        serialization = serialization.substring(2);
        return "R" + serialization;
    }
    
    /**
     * Helper method for serialize(). Recursively steps through
     * BST to create string representation of tree.
     * 
     * @param   curr Current node being operated on
     * 
     * @return  String representation of BST
     */
    private String recursive_serialize(BTNode<T> curr) {
        // base case: null node
        if (curr == null)
            return ",X";
        // otherwise, check for children
        // if node has no children, a leaf has been reached
        else if (curr.getLeft() == null && curr.getRight() == null)
            return ",L(" + curr.getKey() + ")";
        // if node has children, we're at an internal node and must continue traversing
        else
            return ",I(" + curr.getKey() + ")" + recursive_serialize(curr.getLeft()) 
                + recursive_serialize(curr.getRight());
    }

    /**
	 * Produce a deep copy of the BST that is reversed (i.e., left children
	 * hold keys greater than the current key, right children hold keys less
	 * than the current key).
	 *
	 * @return	Deep copy of the BST reversed
	 */
	public BST<T> reverse() {
        BST<T> rtree = new BST<T>();
        recursivePreOT(this.root, rtree);
        return rtree;
    }

    /**
     * Helper method for reverse(). Recursively steps through BST in a pre-order traversal
     * and updates a reversed tree as it goes. 
     *
     * @param   curr  Node currently being operated on
     * @param   rtree Reversed tree  
     * 
     * @return  Completed reversed tree.
     */
    private void recursivePreOT(BTNode<T> curr, BST<T> rtree) {
        if (curr == null)
            return;
        rtree.root = recursiveReversePut(rtree.root, curr.getKey());
        recursivePreOT(curr.getLeft(), rtree);
        recursivePreOT(curr.getRight(), rtree);
        return;
    }

    /**
     * Recursively steps through reversed BST, then inserts key before returning.
     *
     * @param   curr Node currently being operated on
     * @param   key  Generic value being added to the BST
     * 
     * @return  Current BTNode once insertion is complete
     */
    private BTNode<T> recursiveReversePut(BTNode<T> curr, T key) {
        if (curr == null)
            return new BTNode<T>(key);
        else if (curr.getKey().compareTo(key) < 0)
            curr.setLeft(recursiveReversePut(curr.getLeft(), key));
        else if (curr.getKey().compareTo(key) > 0)
            curr.setRight(recursiveReversePut(curr.getRight(), key));
        return curr;
    }
}