/**
 * MinHeap object for CS 1501 project 3
 * @author	Benjamin Kiddie
 */
package project_3;

public class MinHeap {
    
    private String minHeap[] = new String[4];
    private int count = 0;

    /**
     * Gives the number of elements in heap.
     *
     * @return  Number of elements in heap
     */
    public int getCount() {
        return count;
    }

    /**
     * Tells whether or not heap is currently empty.
     *
     * @return  True if heap is empty, false otherwise 
     */
    public boolean isEmpty() {
        return (count == 0);
    }

    /**
     * Returns the VIN of the minimum Car in this heap
     * 
     * @return  VIN of minumum Car in heap
     */
    public String getMin() {
        return minHeap[1];
    }

    /**
     * Adds element to heap.
     * 
     * @param vin       VIN of Car being added
     * @param type      Indicator of what kind of heap this is
     * @param reference Indirection DLB for reference
     */
    public void add(String vin, int type, VinDLB reference) {
        // incremement count
        count++;
        // if the heap is full, resize
        if (count == minHeap.length)
            resizeHeap();
        // add element to heap at current index
        minHeap[count] = vin;
        reference.getCar(vin).setIndex(count, type);
        // swim it
        this.swim(count, type, reference);
    }

    /**
     * Removes element from heap.
     * 
     * @param vin       VIN of Car being removed
     * @param type      Indicator of what kind of heap this is
     * @param reference Indirection DLB for reference
     */
    public void remove(String vin, int type, VinDLB reference) {
        // find the correct index
        int i = reference.getIndex(vin, type);
        // if removing from the end, simply get rid of that element
        if (i == count) {
            minHeap[i] = null;
            count--;
            return;
        }
        // otherwise, swap the string at i and the string at the end, make the end null
        minHeap[i] = minHeap[count];
        minHeap[count] = null;
        // decrement count
        count--;
        // sink the replacement
        this.sink(i, type, reference);
    }

    /**
     * Helper method for add(). Resizes both priority queues to double their current
     * length, so as to allow for the addition of more vehicles to the system.
     */
    public void resizeHeap() {
        // preserve old contents
        String oldHeap[] = minHeap;
        // double size
        minHeap = new String[minHeap.length * 2];
        // copy old values to new heap
        for (int i = 1; i < oldHeap.length; i++)
            minHeap[i] = oldHeap[i];
    }

    /**
     * Updates the position of an element in the heap.
     *
     * @param index     Position of element being updated
     * @param type      Indicator of what kind of heap this is
     * @param oldVal    Old value of element for reference
     * @param reference Indirection DLB for reference
     */
    public void update(int index, int type, int oldVal, VinDLB reference) {
        // figure out if we need to sink or swim this element, then do it
        if (type == 1 || type == 3) {
            if (reference.getCar(minHeap[index]).getPrice() <= oldVal)
                this.swim(index, type, reference);
            else
                this.sink(index, type, reference);
        } 
        else {
            if (reference.getCar(minHeap[index]).getMileage() <= oldVal)
                this.swim(index, type, reference);
            else
                this.sink(index, type, reference);
        }
    }

    /**
     * "Swims" an element up through the heap to restore heap property.
     * 
     * @param index     Position of element being moved
     * @param type      Indicator of what kind of heap this is  
     * @param reference Indireciton DLB for reference
     */
    private void swim(int start, int type, VinDLB reference) {
		// start at given index
		int i = start;
		// while heap property is being violated, follow the swim algorithm
		while (i > 1) {
            int j = i / 2;
			if (type == 1 || type == 3) {
                if (reference.getCar(minHeap[i]).getPrice() < reference.getCar(minHeap[j]).getPrice()) {
                    swapIndex(i, j, type, reference);
                    swapData(i, j);
                    i = j;
                }
                else  
                    return;
            }
            else {
                if (reference.getCar(minHeap[i]).getMileage() < reference.getCar(minHeap[j]).getMileage()) {
                    swapIndex(i, j, type, reference);
                    swapData(i, j);
                    i = j;
                }
                else  
                    return;
            }
		}
	}

    /**
     * "Sinks" an element down through the heap to restore heap property.
     * 
     * @param index     Position of element being moved
     * @param type      Indicator of what kind of heap this is  
     * @param reference Indireciton DLB for reference
     */
    private void sink(int start, int type, VinDLB reference) {
        // start at the given index
        int i = start;
        // while the heap property is being violated, follow the sink algorithm
        while (i * 2 <= count) {
            int j = i * 2;
            if (type == 1 || type == 3) {
                if (j < count && reference.getCar(minHeap[j]).getPrice() > reference.getCar(minHeap[j + 1]).getPrice())
                    j++;
                if (reference.getCar(minHeap[i]).getPrice() < reference.getCar(minHeap[j]).getPrice())
                    break;
                swapIndex(i, j, type, reference);
                swapData(i, j);
                i = j;
            }
            else {
                if (j < count && reference.getCar(minHeap[j]).getMileage() > reference.getCar(minHeap[j + 1]).getMileage())
                    j++;
                if (reference.getCar(minHeap[i]).getMileage() < reference.getCar(minHeap[j]).getMileage())
                    break;
                swapIndex(i, j, type, reference);
                swapData(i, j);
                i = j;
            }
        }
	}

    /**
     * Helper method for sink() and swim(). Swaps data of elements at given positions.
     *
     * @param i Position of first element
     * @param j Position of second element
     */
    private void swapData(int i, int j) {
        String temp = minHeap[i];
        minHeap[i] = minHeap[j];
        minHeap[j] = temp;
    }

    /**
     * Helper method for sink() and swim(). Swaps indices of elements at given positions.
     *
     * @param i         Position of first element
     * @param j         Position of second element
     * @param type      Indicator of what kind of heap this is
     * @param reference Indirection DLB for reference
     */
    private void swapIndex(int i, int j, int type, VinDLB reference) {
        reference.setIndex(minHeap[i], j, type);
        reference.setIndex(minHeap[j], i, type);
    }

}
