/**
 * Latency PQ object for CS1501 Project 4
 * @author	Ben Kiddie
 */
package project_4;

public class PrimPQ {

    // heap of edges
    private GraphEdge[] heap;
    // current number of valid items in PQ
    private int count;

    /**
     * Constructor for PrimPQ.
     *
     * @param   size Number of edges that will be stored
     */
    public PrimPQ(int size) {
        // initialize heap to be size + 1 (index 0 will never be used)
        heap = new GraphEdge[size + 1];
        // initialize count as 0
        count = 0;
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
     * Add an edge to the PQ.
     *
     * @param   edge Edge being added
     */
    public void add(GraphEdge edge) {
        heap[++count] = edge;
        swim();
    }

    /**
     * Get the current minimum edge in the PQ.
     *
     * @return  Min edge.
     */
    public GraphEdge getMin() {
        // get the minimum edge
        GraphEdge min = heap[1];
        // remove this edge and reorder the heap
        heap[1] = heap[count--];
        sink(1);
        // return the min edge
        return min;
    }

    /**
     * Swim the most recently added edge up the PQ.
     */
    private void swim() {
        // start at the most recent item
        int i = count;
        // while heap property is being violated, follow the swim algorithm
		while (i > 1) {
            int j = i / 2;
            if (heap[i].getLatency() < heap[j].getLatency()) {
                swap(i, j);
                i = j;
            }
            else
                return;
		}
    }

    /**
     * Sink the first edge down the PQ. 
     */
    private void sink(int start) {
        // start at the given index
        int i = start;
        // while the heap property is being violated, follow the sink algorithm
        while (i * 2 <= count) {
            int j = i * 2;
            if (j < count && heap[j].getLatency() > heap[j + 1].getLatency())
                j++;
            if (heap[i].getLatency() < heap[j].getLatency())
                break;
            swap(i, j);
            i = j;
        }
    }

    /** 
     * Helper method for swim() and sink(). Swaps edges at given indices.
     *
     * @param   i Index of first edge.
     * @param   j Index of second edge.
     */
    private void swap(int i, int j) {
        GraphEdge temp = heap[i];
        heap[i] = heap[j];
        heap[j] = temp;
    }

}