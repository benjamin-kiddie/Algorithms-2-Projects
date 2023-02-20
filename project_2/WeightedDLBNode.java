/**
 * WeightedDLBNode object for CS1501 Project 2
 * @author	Benjamin Kiddie
 */
package project_2;

import java.io.Serializable;

public class WeightedDLBNode implements Serializable {
	
	// letter represented by this DLBNode
	private char let;

	// lead to other alternatives for current letter in the path
	private WeightedDLBNode right;

	// leads to keys with prefixed by the current path
	private WeightedDLBNode down;

    // weight of current letter
    private int weight;

	/**
	 * Constructor that accepts the letter for the new node to represent
	 */
	public WeightedDLBNode(char let) {
		this.let = let;
		this.right = null;
		this.down = null;
        this.weight = 0;
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
	public WeightedDLBNode getRight() {
		return right;
	}

	/**
	 * Getter for the child DLBNode
	 *
	 * @return	Reference to the down DLBNode
	 */
	public WeightedDLBNode getDown() {
		return down;
	}

    /**
     * Getter for the weight of this DLBNode
     *
     * @return  The weight
     */
    public int getWeight() {
        return weight;
    }

	/**
	 * Setter for the next linked-list DLBNode
	 *
	 * @param	r DLBNode to set as the right reference
	 */
	public void setRight(WeightedDLBNode r) {
		right = r;
	}

	/**
	 * Setter for the child DLBNode
	 *
	 * @param	d DLBNode to set as the down reference
	 */
	public void setDown(WeightedDLBNode d) {
		down = d;
	}

    /**
     * Setter for the weight
     *
     * @param   w Weight to be set as the new weight
     */
    public void setWeight(int w) {
        weight = w;
    }

}