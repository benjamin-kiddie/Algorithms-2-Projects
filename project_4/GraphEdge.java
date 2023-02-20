/**
 * Edge object for CS1501 Project 4
 * @author	Ben Kiddie
 */
package project_4;

public class GraphEdge {

    // int endpoints of edge
    private int u;
    private int w;
    // boolean describing whether or not this edge is copper
    private boolean copper;
    // bandwidth along this edge
    private int bandwidth;
    // length of this edge
    private int length;
    // latency, computed using bool copper and length
    private double latency;
    // next edge in list
    private GraphEdge next;

    public GraphEdge(int u, int w, boolean c, int b, int l) {
        // assign basic values
        this.u = u;
        this.w = w;
        this.copper = c;
        this.bandwidth = b;
        this.length = l;
        this.next = null;
        // compute latency
        if (c) this.latency = (length/230000000.0);
        else this.latency = (length/200000000.0);
    }

    /**
     * Setter for next edge.
     *
     * @param  GraphEdge n
     */
    public void setNext(GraphEdge n) {
        next = n;
    }

    /**
     * Getter for starting point.
     *
     * @return  int u
     */
    public int getStart() {
        return u;
    }

    /**
     * Getter for ending point.
     *
     * @return  int u
     */
    public int getEnd() {
        return w;
    }

    /**
     * Getter for copper property.
     *
     * @return  boolean copper
     */
    public boolean isCopper() {
        return copper;
    }

    /**
     * Getter for bandwidth.
     *
     * @return  int bandwidth
     */
    public int getBandwidth() {
        return bandwidth;
    }

    /**
     * Getter for length.
     *
     * @return  int length
     */
    public int getLength() {
        return length;
    }

    /**
     * Getter for latency.
     *
     * @return  double latency
     */
    public double getLatency() {
        return latency;
    }

    /**
     * Getter for next edge.
     *
     * @return  GraphEdge next
     */
    public GraphEdge getNext() {
        return next;
    }

}