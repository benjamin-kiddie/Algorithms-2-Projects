/**
 * MMDLB object for CS 1501 project 3
 * @author	Benjamin Kiddie
 */
package project_3;

// for String methods
import java.lang.String;

public class MMDLB {
	
	// root node of DLB
    private MMDLBNode root = null;
    // constant terminator character
    private static char terminator = '^';

    /**
	 * Add a new Car to the system
	 *
	 * @param make		Make of the car being added
	 * @param model		Model of the car being added
	 * @param vin		VIN of the car being added
	 * @param reference Indirection DLB for reference
	 */
	public void add(String make, String model, String vin, VinDLB reference) {
		// combine make and model into a single key with a terminator
        String key = (make + model + terminator).toUpperCase();
        // call recursive method
		root = recursiveAdd(this.root, key, 0, vin, reference);
    }

	/**
	 * Helper method for add(). Recursively through MMDLB, adding nodes as necessary
	 * along the way.
	 * 
	 * @param curr 		MMDLBNode currently being operated on
	 * @param key 		String being added to DLB
	 * @param charCount Index of current character being added into DLB
	 * @param vin		VIN of car being added
	 * @param reference Indirection DLB for reference
	 * 
	 * @return	Current MMDLBNode when alterations are finished
	 */
	private MMDLBNode recursiveAdd(MMDLBNode curr, String key, int charCount, String vin, VinDLB reference) {
		// base case: current node is null
		if (curr == null) {
			curr = new MMDLBNode(key.charAt(charCount));
            // if we reach the end of the key
			if (key.charAt(charCount) == terminator) {
                // insert into heaps
				curr.setHeap(new MinHeap(), 3);
				curr.getHeap(3).add(vin, 3, reference);
				curr.setHeap(new MinHeap(), 4);
				curr.getHeap(4).add(vin, 4, reference);
				// return curr
				return curr;
			}
			// otherwise, continue down
			curr.setDown(recursiveAdd(curr.getDown(), key, ++charCount, vin, reference));
		}

		// otherwise, check validity of this letter
		else if (curr.getLet() == key.charAt(charCount)) {
			// if we reach the end of inserting a dupe, add to heaps and return
			if (key.charAt(charCount) == terminator) {
                curr.getHeap(3).add(vin, 3, reference);
                curr.getHeap(4).add(vin, 4, reference);
				return curr;
            }
			// otherwise, continue down the DLB
			curr.setDown(recursiveAdd(curr.getDown(), key, ++charCount, vin, reference));
		}

		// key doesn't match, we'll have to go to the right
		else
			curr.setRight(recursiveAdd(curr.getRight(), key, charCount, vin, reference));
		
		// when we're done, return curr
		return curr;
	}

	/**
	 * Remove a Car from the system.
	 *
	 * @param vin		VIN of the car being removed
	 * @param make		Make of the car being removed
	 * @param model		Model of the car being removed
	 * @param reference Indirection DLB for reference
     */
	public void remove(String vin, String make, String model, VinDLB reference) {
		// combine make and model into a single key with a terminator
        String key = (make + model + terminator).toUpperCase();
		// call recursive function
		root = recursiveRemove(this.root, key, 0, vin, reference);
	}

	/** 
	 * Helper method for remove(). Recursively steps through MMDLB, removing
	 * nodes as necessary along the way.
	 *
	 * @param curr		MMDLB node currently being operated on
	 * @param key		Key being used to navigate MMDLB
	 * @param charCount Index of current character being investigated 
	 * @param vin		VIN of car being removed
	 * @param reference Indirection DLB for reference
	 *
	 * @return	MMDLB when alterations are finished
	 */
	private MMDLBNode recursiveRemove(MMDLBNode curr, String key, int charCount, String vin, VinDLB reference) {
		// part 1: traverse to the right queue bottom
		// base case: dead end
		if (curr == null)
			return null;
		// otherwise, check validity of this letter
		else if (curr.getLet() == key.charAt(charCount)) {
			// if we reach the bottom of our traversal, we'll remove
			if (key.charAt(charCount) == terminator && key.charAt(charCount) == curr.getLet()) {
				// take this car out of both heaps
				curr.getHeap(3).remove(vin, 3, reference);
				curr.getHeap(4).remove(vin, 4, reference);
				// if they're now empty, this make/model is now irrelevant
				if (curr.getHeap(3).isEmpty())
					return null;
				// otherwise, return the node as is
				return curr;
			}
			// otherwise, continue down the DLB
			curr.setDown(recursiveRemove(curr.getDown(), key, ++charCount, vin, reference));
		}
		// key doesn't match, we'll have to go to the right
		else
			curr.setRight(recursiveRemove(curr.getRight(), key, charCount, vin, reference));
		
		// when we're coming back, we need to see if this node is relevant anymore
		// if curr doesn't have any children anymore, it is now irrelevant
		if (curr.getDown() == null)
			return curr.getRight();
		// otherwise, it can stay
		return curr;
	}

	/**
	 * Get the price or mileage heap of a specific make and model.
	 * 
	 * @param make	Make being looked for
	 * @param model	Model being looked for
	 * @param type	Type of heap being looked for
	 *
	 * @return	MinHeap being searched for, null if it doesn't exist
	 */
    public MinHeap getHeap(String make, String model, int type) {
		MMDLBNode curr = this.root;
		String key = (make + model + terminator).toUpperCase();
		int i = 0;
		// look through DLB for designated vin, return -1 if not found
		while (!(key.charAt(i) == terminator && curr.getLet() == key.charAt(i))) {
			if (curr == null)
				return null;
			else if (curr.getLet() == key.charAt(i)) {
				i++;
				curr = curr.getDown();
			}
			else
				curr = curr.getRight();
		}
		return curr.getHeap(type);
	}

	/**
	 * Retrieve the minimum value from one of the heaps of a 
	 * specific make and model.
	 *
	 * @param make	Make of car being looked for
	 * @param model	Model of car being looked for
	 * @param type	Type of heap to draw from
	 *
	 * @return	String of minumum Car being requested
	 */
	public String getMin(String make, String model, int type) {
		// get the heap corresponding to this make and model
		MinHeap heap = this.getHeap(make, model, type);
		// if we got nothing, this make and model don't exist. return null
		if (heap == null)
			return null;
		// otherwise, return the min
		return heap.getMin();
	}

}