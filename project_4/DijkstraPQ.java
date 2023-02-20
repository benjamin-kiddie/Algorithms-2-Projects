/**
 * Dijkstra PQ object for CS1501 Project 4
 * @author	Ben Kiddie
 */
package project_4;

public class DijkstraPQ {

    // heap of paths
    private Path[] heap;
    // indirection table
    private int[] table;
    // current number of valid items in PQ
    private int count;

    /**
     * Constructor for DijkstraPQ.
     *
     * @param   size Number of vertices that will be stored
     */
    public DijkstraPQ(int size) {
        // initialize heap to be size + 1 (index 0 will never be used)
        heap = new Path[size + 1];
        // initialize table of size size and make its elements -1
        table = new int[size];
        for (int i = 0; i < size; i++)
            table[i] = -1;
        // initialize count as 0
        count = 0;
    }

    /**
     * Return number of items in PQ.
     *
     * @return count
     */
    public int getCount() {
        return count;
    }

    /**
     * Return whether or not the PQ is emmpty.
     *
     * @return Whether or not PQ is empty.
     */
    public boolean isEmpty() {
        return (count == 0);
    }

    /**
     * Add a path to the PQ.
     *
     * @param   p Path being added
     */
    public void add(Path p) {
        // if this path is replacing an old one, replace it
        if (table[p.getDest()] != -1) {
            substitute(p);
            return;
        }
        // otherwise, add it to the heap
        heap[++count] = p;
        table[p.getDest()] = count;
        swim(count);
    }

    /**
     * Substitute one path in PQ for another
     *
     * @param   newPath Path replacing old one
     */
    public void substitute(Path newPath) {
        // locate where the old path to this vertex is
        int index = table[newPath.getDest()];
        // switch the two
        Path oldPath = heap[index];
        heap[index] = newPath;
        // this new path is shorter, so swim it
        swim(index);
    }

    /**
     * Get the current minimum path in the PQ.
     *
     * @return  Min path.
     */
    public Path getMin() {
        // get the minimum edge
        Path min = heap[1];
        // remove the minimum edge and replace it with the last one
        table[heap[1].getDest()] = -1;
        heap[1] = heap[count];
        table[heap[1].getDest()] = 1;
        heap[count--] = null;
        sink(1);
        // return the min path
        return min;
    }

    /**
     * Swim a path up through the heap.
     *
     * @param   start Index of path to be swum.
     */
    private void swim(int start) {
        // start at the given index
        int i = start;
        // while heap property is being violated, follow the swim algorithm
		while (i > 1) {
            int j = i / 2;
            if (heap[i].getLength() < heap[j].getLength()) {
                swapData(i, j);
                swapIndex(i, j);
                i = j;
            }
            else  
                return;
		}
    }

    /**
     * Sink a path down the heap.
     *
     * @param   start Index of path being sunk.
     */
    private void sink(int start) {
        // start at the given index
        int i = start;
        // while the heap property is being violated, follow the sink algorithm
        while (i * 2 <= count) {
            int j = i * 2;
            if (j < count && heap[j].getLength() > heap[j + 1].getLength())
                j++;
            if (heap[i].getLength() < heap[j].getLength())
                break;
            swapData(i, j);
            swapIndex(i, j);
            i = j;
        }
    }

    /** 
     * Helper method for swim() and sink(). Swaps data at given indices.
     *
     * @param   i Index of first path.
     * @param   j Index of second path.
     */
    private void swapData(int i, int j) {
        Path temp = heap[i];
        heap[i] = heap[j];
        heap[j] = temp;
    }

    /**
     * Helper method for swim() and sink(). Swaps indixes of two given paths.
     *
     * @param   i Index of first path.
     * @param   j Index of second path.
     */
    private void swapIndex(int i, int j) {
        int i1 = heap[i].getDest();
        int i2 = heap[j].getDest();
        int temp = table[i1];
        table[i1] = table[i2];
        table[i2] = temp;
    }

}