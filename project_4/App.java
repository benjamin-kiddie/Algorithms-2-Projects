/**
 * A driver for CS1501 Project 4
 * @author	Dr. Farnan
 */
package project_4;

import java.util.ArrayList;

public class App {

    public static void main(String[] args) {
        // construct network
        NetAnalysis na = new NetAnalysis("build/resources/main/network_data2.txt");

        // test lowestLatencyPath()
        ArrayList<Integer> path1 = na.lowestLatencyPath(0, 5);
		ArrayList<Integer> intendedPath1 = new ArrayList<Integer>();
		intendedPath1.add(0);
		intendedPath1.add(2);
        intendedPath1.add(5);
        System.out.println("Results for lowestLatencyPath():");
        for (int i = 0; i < path1.size(); i++)
            System.out.printf("Expected %d, result %d\n", intendedPath1.get(i), path1.get(i));
        System.out.println();
        
        // test bandwidthAlongPath()
        int start = 0;
        int end = 5;
        ArrayList<Integer> path2 = na.lowestLatencyPath(start, end);
        int bandwidth = na.bandwidthAlongPath(path2);
        System.out.println("Results for bandwidthAlongPath():");
        System.out.println("Bandwidth from " + start + " to " + end + " is " + bandwidth + "\n");

        // test copperOnlyConnected()
        System.out.println("Results for copperOnlyConnected:");
        System.out.println(na.copperOnlyConnected() + "\n");

        // test lowestAvgLatST
        ArrayList<STE> mst = na.lowestAvgLatST();
        System.out.println("Spanning tree constructed as follows:");
        if (mst == null)
            System.out.println("Graph is disconnected, spannign tree impossible.");
        else
            for (STE e : mst)
                System.out.println(e.toString());
        System.out.println();

        // test connectedTwoVertFail()
        System.out.println("Results for connectedTwoVertFail():");
        System.out.println(na.connectedTwoVertFail());

    }
}
