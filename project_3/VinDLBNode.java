/**
 * VinDLBNode object for CS1501 project 3
 * @author	Benjamin Kiddie
 */
package project_3;

import java.io.Serializable;

public class VinDLBNode implements Serializable {
	
	// Letter represented by this node
	private char let;
	// Reference to neighbor
	private VinDLBNode right;
	// Reference to child
	private VinDLBNode down;
	// Reference to car object
	private Car car;

	/**
	 * Constructor that accepts the character for the new node to represent.
	 *
	 * @param let	Character being put in this node
	 */
	public VinDLBNode(char let) {
		this.let = let;
        this.car = null;
		this.right = null;
		this.down = null;
	}

    /**
	 * Constructor that accepts the character and car for the new node to represent.
	 *
	 * @param let	Character being put in this node.
	 * @param c		Car being put in this node.
	 */
    public VinDLBNode(char let, Car c) {
        this.let = let;
		this.car = c;
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
	 * Getter for the car this DLBNode represents.
	 *
	 * @return	The car
	 */
	public Car getCar() {
		return car;
	}

	/**
	 * Getter for the next linked-list DLBNode.
	 *
	 * @return	Reference to the right DLBNode
	 */
	public VinDLBNode getRight() {
		return right;
	}

	/**
	 * Getter for the child DLBNode.
	 *
	 * @return	Reference to the down DLBNode
	 */
	public VinDLBNode getDown() {
		return down;
	}

	/**
	 * Setter for the next linked-list DLBNode.
	 *
	 * @param	r DLBNode to set as the right reference
	 */
	public void setRight(VinDLBNode r) {
		right = r;
	}

	/**
	 * Setter for the child DLBNode.
	 *
	 * @param	d DLBNode to set as the down reference
	 */
	public void setDown(VinDLBNode d) {
		down = d;
	}
	
}