/**
 * MMDLBNode object for CS1501 project 3
 * @author	Benjamin Kiddie
 */
package project_3;

public class MMDLBNode {
	
	// Letter represented by this node
	private char let;
	// Reference to neighbor
	private MMDLBNode right;
	// Reference to child
	private MMDLBNode down;
    // Reference to heap objects if this is the end of a make and model
    private MinHeap priceHeap;
    private MinHeap mileageHeap;

	/**
	 * Constructor that accepts the letter for the new node to represent
	 */
	public MMDLBNode(char let) {
		this.let = let;
		this.right = null;
		this.down = null;
    }

	/**
	 * Getter for the letter this DLBNode represents
	 *
	 * @return	The letter
	 */
	public char getLet() {
		return let;
	}

	/**
	 * Getter for the next linked-list DLBNode
	 *
	 * @return	Reference to the right DLBNode
	 */
	public MMDLBNode getRight() {
		return right;
	}

	/**
	 * Getter for the child DLBNode
	 *
	 * @return	Reference to the down DLBNode
	 */
	public MMDLBNode getDown() {
		return down;
	}

	/**
	 * Getter for MinHeap objects.
	 * 
	 * @param type	Indicator of what kind of heap is requested
	 *
	 * @return	MinHeap being requested
	 */
	public MinHeap getHeap(int type) {
		if (type == 3)
			return priceHeap;
		else
			return mileageHeap;
	}
	/**
	 * Setter for the next linked-list DLBNode
	 *
	 * @param	r DLBNode to set as the right reference
	 */
	public void setRight(MMDLBNode r) {
		right = r;
	}

	/**
	 * Setter for the child DLBNode
	 *
	 * @param	d DLBNode to set as the down reference
	 */
	public void setDown(MMDLBNode d) {
		down = d;
	}

	/**
	 * Setter for MinHeap objects.
	 *
	 * @param h		MinHeap to be inserted into this node
	 * @param type	Type of heap being changed
	 */
	public void setHeap(MinHeap h, int type) {
		if (type == 3)
			priceHeap = h;
		else
			mileageHeap = h;
	}
	
}