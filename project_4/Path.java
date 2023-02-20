/**
 * Path object for CS1501 Project 4
 * @author	Ben Kiddie
 */
package project_4;

public class Path {

    // destination vertex
    private int dest;
    // length of this path
    private double length;

    /**
     * Constructor for path.
     * 
     * @param   d Destination
     * @param   l Length (latency)
     */
    public Path(int d, double l) {
        this.dest = d;
        this.length = l;
    }

    /**
     * Getter for destination.
     *
     * @return  Destination
     */
    public int getDest() {
        return dest;
    }

    /**
     * Getter for latency
     *
     * @return  Length (latency)
     */
    public double getLength() {
        return length;
    }

}