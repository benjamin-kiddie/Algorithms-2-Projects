/**
 * VinDLB object for CS 1501 project 3
 * @author	Benjamin Kiddie
 */
package project_3;

// for String methods
import java.lang.String;

public class VinDLB {
	
	// root node of DLB
    private VinDLBNode root = null;
	// count of VINs in DLB
	private int count = 0;
    
    /**
	 * Get the number of VINs in the VinDLB.
	 *
	 * @return	int, the number of VINs in the dictionary
	 */
	public int getCount() {
		return count;
    }

    /**
	 * Adds a new VIN and Car to the DLB.
	 *
	 * @param c		Car object to add
	 * @param vin	VIN of car object to add
	 */
	public void add(Car c, String vin) {
		// call recursive method
		root = recursiveAdd(this.root, vin, 0, c);
    }

	/**
	 * Helper method for add(). Steps through VinDLB recursively, adding nodes as necessary
	 * along the way.
	 * 
	 * @param curr 		VinDLBNode currently being operated on
	 * @param vin 		String being added to DLB
	 * @param charCount Index of current character being added into DLB
	 * @param c			Car being added to the DLB
	 * 
	 * @return Current VinDLBNode when alterations are finished
	 */
	private VinDLBNode recursiveAdd(VinDLBNode curr, String vin, int charCount, Car c) {
		// base case: current node is null
		if (curr == null) {
			// if we've reached the end of an addition
			if (charCount == 16) {
				// add a new node with a car
				curr = new VinDLBNode(vin.charAt(charCount), c);
				// incrememnt count
				count++;
				// return curr
				return curr;
			}
			// otherwise, add a new node and continue downwards
			curr = new VinDLBNode(vin.charAt(charCount));
			curr.setDown(recursiveAdd(curr.getDown(), vin, ++charCount, c));
		}

		// otherwise, check validity of this letter
		else if (curr.getLet() == vin.charAt(charCount)) {
			// if we reach the end of inserting a dupe, return
			if (charCount == 16)
				return curr;
			// otherwise, continue down the DLB
			curr.setDown(recursiveAdd(curr.getDown(), vin, ++charCount, c));
		}

		// key doesn't match, we'll have to go to the right
		else
			curr.setRight(recursiveAdd(curr.getRight(), vin, charCount, c));
		
		// when we're done, return curr
		return curr;
	}

	/**
	 * Removes a VIN and car from the VinDLB.
	 *
	 * @param vin	VIN of car being removed.
	 */
	public void remove(String vin) {
		// call recursive function
		root = recursiveRemove(this.root, vin, 0);
	}

	/**
	 * Helper method for remove(). Steps through VinDLB recursively, removing nodes
	 * as necessary along the way.
	 *
	 * @param curr		VinDLB node currently being operated on.
	 * @param vin		VIN of Car being removed
	 * @param charCount Index of current character being examined.
	 *
	 * @return	VinDLBNode when alterations are done
	 */
	private VinDLBNode recursiveRemove(VinDLBNode curr, String vin, int charCount) {
		// part 1: traverse to the bottom
		// base case: dead end
		if (curr == null)
			return null;
		// otherwise, check validity of this letter
		else if (curr.getLet() == vin.charAt(charCount)) {
			// if we reach the bottom of our traversal, we'll start removing
			if (charCount == 16)
				return curr.getRight();
			// otherwise, continue down the DLB
			curr.setDown(recursiveRemove(curr.getDown(), vin, ++charCount));
		}
		// key doesn't match, we'll have to go to the right
		else
			curr.setRight(recursiveRemove(curr.getRight(), vin, charCount));
		
		// when we're coming back, we need to see if this node is relevant anymore
		// if curr doesn't have any children anymore, it is now irrelevant
		if (curr.getDown() == null)
			return curr.getRight();
		// otherwise, it can stay
		return curr;
	}

	/**
	 * Retrieves a car from the VinDLB.
	 * 
	 * @param vin	VIN of car being retrieved
	 *
	 * @return	Car being requested
	 */
	public Car getCar(String vin) {
		// start at root and beginning of vin
		VinDLBNode curr = this.root;
		int i = 0;
		// look through DLB for designated VIN, return -1 if not found
		while (i < 16) {
			if (curr == null)
				return null;
			if (curr.getLet() == vin.charAt(i)) {
				i++;
				curr = curr.getDown();
			}
			else
				curr = curr.getRight();
		}
		// return the car if we found it
		return curr.getCar();
	}

	/**
	 * Retrieve a specific index of a Car.
	 *
	 * @param vin	VIN of car to retrieve index of
	 * @param type	Type of index to be retrieved 
	 * 
	 * @return	Index being requested
	 */
	public int getIndex(String vin, int type) {
		// return the requested type of index at this vin
		return this.getCar(vin).getIndex(type);
	}

	/**
	 * Set a specific index of a Car.
	 *
	 * @param vin	VIN of Car whose index is changing
	 * @param index	New index
	 * @param type	Type of index being changed
	 */
	public void setIndex(String vin, int index, int type) {
		// start at root and beginning of vin
		this.getCar(vin).setIndex(index, type);
	}

}