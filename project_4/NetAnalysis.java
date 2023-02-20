/**
 * Network Analysis object for CS1501 Project 4
 * @author	Ben Kiddie
 */
package project_4;

// for IO purposes
import java.io.*;
import java.util.Scanner;
// for ArrayList object
import java.util.ArrayList;
// for String methods
import java.lang.String;

public class NetAnalysis implements NetAnalysis_Inter {

	// adjacency list representation of graph
    private GraphEdge[] adjacencyList;
	// number of vertices
	private int numVert = 0;
	// number of edges
	private int numEdges = 0;
	
	/**
	 * Constructor for network.
	 *
	 * @param	fname Path to file containing graph properties.
	 */
	public NetAnalysis(String fname) {
		populateGraph(fname);
	}

	/**
	 * Helper method for constructor. Constructs graph using details 
	 * found in given file.
	 *
	 * @param	fname Path to file containing graph properties.
	 */
	private void populateGraph(String fname) {
		try {
			// construct a new buffered reader using the given file
			Scanner read = new Scanner(new File(fname));
			// the first line will be the size of our adjacency list
			String line = read.nextLine();
			// if it's null, then our graph is empty
			if (line == null)
				return;
			// otherwise, grab the integer representing size
			numVert = Integer.parseInt(line);
			// if it's zero or 1, edges don't matter
			if (numVert == 0 || numVert == 1)
				return;
			// otherwise, create an adjacency list of this size
			adjacencyList = new GraphEdge[numVert];
			// go through the rest of the file
			while (read.hasNextLine()) {
				// construct and add an edge using the properties on this line
				line = read.nextLine();
				String[] properties = line.split("\\s+");
				int u = Integer.parseInt(properties[0]);
				int w = Integer.parseInt(properties[1]);
				// if u and w are the same, this edge is irrelevant
				if (u == w)
					continue;
				boolean copper = (properties[2].equals("copper"));
				int bandwidth = Integer.parseInt(properties[3]);
				int length = Integer.parseInt(properties[4]);
				// add edge to adjacency list
				this.add(new GraphEdge(u, w, copper, bandwidth, length));
				// add inverse edge to adjacency list
				this.add(new GraphEdge(w, u, copper, bandwidth, length));
				numEdges += 2;
			}
			// close the reader
			read.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Helper method for populateGraph(). Adds given edge to adjacency list.
	 *
	 * @param	e Edge to be added to adjacency list.
	 */
	private void add(GraphEdge e) {
        // add this edge to it's designated spot in the adjacenct list
        int start = e.getStart();
        if (adjacencyList[start] == null)
            adjacencyList[start] = e;
        else {
            GraphEdge curr = adjacencyList[start];
            while (curr.getNext() != null)
                curr = curr.getNext();
            curr.setNext(e);
        }
    }

    /**
	 * Find the lowest latency path from vertex `u` to vertex `w` in the graph
	 *
	 * @param	u Starting vertex
	 * @param	w Destination vertex
	 *
	 * @return	ArrayList<Integer> A list of the vertex id's representing the
	 * 			path (should start with `u` and end with `w`)
	 * 			Return `null` if no path exists
	 */
	public ArrayList<Integer> lowestLatencyPath(int u, int w) {
		// base cases: u and w aren't in graph
		if (numVert == 0)
			return null;
		else if (u < 0 || u >= numVert)
			return null;
		else if (w < 0 || w >= numVert)
			return null;

		// create ArrayList to store path
		ArrayList<Integer> finalPath = new ArrayList<Integer>();
		// if u and w are the same, return the starting/ending point
		if (u == w) {
			finalPath.add(u);
			return finalPath;
		}
		// otherwise, create PQ for paths
		DijkstraPQ pq = new DijkstraPQ(numVert);
		// create a boolean array to indicate if edges are in tree
		boolean[] visited = new boolean[numVert];
		for (int i = 0; i < visited.length; i++)
			visited[i] = false;
		// create a double array for the shortest path to a vertex and an int for its source
		double[] shortestPath = new double[numVert];
		for (int i = 0; i < shortestPath.length; i++)
			shortestPath[i] = Double.MAX_VALUE;
		int[] pathSource = new int[numVert];
		for (int i = 0; i < pathSource.length; i++)
			pathSource[i] = -1;
		
		// prep Dijkstra's to start at the specified starting point
		visited[u] = true;	
		shortestPath[u] = 0;
		for (GraphEdge curr = adjacencyList[u]; curr != null; curr = curr.getNext()) {
			shortestPath[curr.getEnd()] = curr.getLatency();
			pathSource[curr.getEnd()] = u;
			pq.add(new Path(curr.getEnd(), curr.getLatency()));
		}
		// run dijstra's
		while (!pq.isEmpty()) {
			// find the shortest path in the PQ
			Path p = pq.getMin();
			int d = p.getDest();
			// mark its destination as visited
			visited[d] = true;
			// if this destination is our goal, then we can break out
			if (d == w)
				break;
			// otherwise, consider every path branching from this one
			for (GraphEdge curr = adjacencyList[d]; curr != null; curr = curr.getNext()) {
				if (!visited[curr.getEnd()]) {
					if ((shortestPath[d] + curr.getLatency()) < shortestPath[curr.getEnd()]) {
						shortestPath[curr.getEnd()] = shortestPath[d] + curr.getLatency();
						pathSource[curr.getEnd()] = d;
						pq.add(new Path(curr.getEnd(), shortestPath[curr.getEnd()]));
					}
				}
			}
		}

		// if we never visited w, then there is no path to w
		if (!visited[w])
			return null;
		// otherwise, put the shortest path back together
		// start at the destination
		int i = w;
		finalPath.add(i);
		// for each vertex in the path,
		while (i != u) {
			// backtrack to the previous node in their path and add it
			i = pathSource[i];
			finalPath.add(0, i);
		}
		return finalPath;
	
	}

	/**
	 * Find the bandwidth available along a given path through the graph
	 * (the minimum bandwidth of any edge in the path). Should throw an
	 * `IllegalArgumentException` if the specified path is not valid for
	 * the graph.
	 *
	 * @param	ArrayList<Integer> A list of the vertex id's representing the
	 * 			path
	 *
	 * @return	int The bandwidth available along the specified path
	 */
	public int bandwidthAlongPath(ArrayList<Integer> p) throws IllegalArgumentException {
		// base cases: graph is empty or path is null
		if (p == null)
			throw new IllegalArgumentException("Path is null.");
		else if (numVert == 0)
			throw new IllegalArgumentException("Path does not exist.");

		// start with an impossible bandwidth
		int minBandwidth = -1;
		// iterate through entire path
		for (int i = 0; i < p.size() - 1; i++) {
			// indentify the desired starting point and end point of this edge
			int start = p.get(i);
			int dest = p.get(i + 1);
			// iterate through edges starting at this point
			GraphEdge curr = adjacencyList[start];
			while (curr != null) {
				// if we find one that end at the desired destination, break
				if (curr.getEnd() == dest)
					break;
				 curr = curr.getNext();
			}
			// if we never found the desired edge, this path does not exist
			if (curr == null)
				throw new IllegalArgumentException("Path does not exist.");
			// otherwise, consider the bandwidth along this edge
			if (curr.getBandwidth() < minBandwidth || minBandwidth == -1)
				minBandwidth = curr.getBandwidth();
		}
		// return the mininum mbandwidth along this path
		return minBandwidth;
	}

	/**
	 * Return `true` if the graph is connected considering only copper links
	 * `false` otherwise
	 *
	 * @return	boolean Whether the graph is copper-only connected
	 */
	public boolean copperOnlyConnected() {
		// base case: empty graph or single edge
		if (numVert == 0 || numVert == 1)
			return true;
		
		// create PQ for edges
		PrimPQ pq = new PrimPQ(numEdges);
		// create a boolean array to indicate if edges are in tree
		boolean[] inTree = new boolean[numVert];
		for (int i = 0; i < inTree.length; i++)
			inTree[i] = false;

		// prepare Prim's to start at vertex 0
		int num = 0;
		inTree[0] = true;
		for (GraphEdge curr = adjacencyList[0]; curr != null; curr = curr.getNext())
			if (curr.isCopper()) pq.add(curr);
		// run Prim's while only considering copper edges
		while (!pq.isEmpty() && numEdges != (numVert - 1)) {
			// get the min edge from the pq. if it is redundant, skip it
			GraphEdge edge = pq.getMin();
			if (inTree[edge.getStart()] && inTree[edge.getEnd()])
				continue;
			// increment numEdges
			num++;
			// mark this edge's destination as in the tree
			inTree[edge.getEnd()] = true;
			// add the non-redundant edges starting at this next vertex to the pq
			for (GraphEdge curr = adjacencyList[edge.getEnd()]; curr != null; curr = curr.getNext()) {
				if (!inTree[curr.getEnd()] && curr.isCopper())
					pq.add(curr);
			}
		}
		
		// if we failed to construct a spanning tree, then this graph is not copper connected
		if (num != numVert - 1)
			return false;
		// otherwise, it is
		return true;
	}

	/**
	 * Return `true` if the graph would remain connected if any two vertices in
	 * the graph would fail, `false` otherwise
	 *
	 * @return	boolean Whether the graph would remain connected for any two
	 * 			failed vertices
	 */
	public boolean connectedTwoVertFail() {
		// base case: empty graph, single vertex, or two vertices
		if (numVert < 4)
			return false;

		// first, check if we have a disconnected graph. if we do, then we know that
		// no vertices are required to fail to disconnect the graph
		if (this.lowestAvgLatST() == null)
			return true;
		// run the articulation point algorithm v times, assuming each vertice doesn't exist
		// until we encounter an articulation point
		for (int i = 0; i < numVert; ++i) {
			// create an arrays for enumeration, parent, low values, and seen
			int[] enumerate = new int[numVert];
			int[] low = new int[numVert];
			boolean[] visited = new boolean[numVert];
			// run a post-order DFS, recording 
			int start = (i != 0) ? 0 : 1;
			enumerate[start] = 0;
			if (DFS(start, 0, -1, i, enumerate, low, visited));
				return true;
		}
		// return false if we never found a set of vulnerablities
		return false;
	}

	/**
	 * Helper method for connectedTwoVertFail. Runs a depth-first search of 
	 * the graph, updating enumerated values and low values as it goes.
	 *
	 * @param	   curr	Current vertex being examined
	 * @param	    num	Enumeration of current vertex
	 * @param	 parent	Parent of current vertex
	 * @param	   skip	Vertex being ignored
	 * @param enumerate Array of enumerated vertices
	 * @param	    low	Array of lowest reachable enumeration
	 * @param   visited	Array indicating whether or not vertices have been visited
	 *
	 * @return	True if graph will fail upon removal, false otherwise.
	 */
	private boolean DFS(int curr, int num, int parent, int skip,
		int[] enumerate, int[] low, boolean[] visited) {
		// define number of children for base case
		int children = 0;
		// define array data for this vertex
		visited[curr] = true;
		enumerate[curr] = num;
		low[curr] = curr;
		// go through this vertex's children
		for (GraphEdge edge = adjacencyList[curr]; edge != null; edge = edge.getNext()) {
			// if the end of this edge is our skipped vertex, then skip it
			if (edge.getEnd() == skip)
				continue;
			// otherwise, if the end of this edge has been visited, see if its enumeration is 
			// less than our current lowest reach. change lowestReach if so
			else if (visited[edge.getEnd()]) {
				if (enumerate[curr] < low[curr])
					low[curr] = enumerate[edge.getEnd()];
			}
			// otherwise, if this node hasn't been visited, check it out
			else {
				// increment children
				children++;
				// if we encountered an articulation point down there, return true
				if (DFS(edge.getEnd(), (num + 1), curr, skip, enumerate, low, visited))
					return true;
				// otherwise, if this child vertex has a lower low value, then it is now our
				// current low value
				else if (low[edge.getEnd()] < low[curr])
					low[curr] = low[edge.getEnd()];
			}
		}
		// if this is the root and it has more than one child, it is an articulation point
		if (parent == -1) {
			if (children > 1)
				return true;
		}
		// otherwise, if our lowest value is our parent, then our parent is an articulation point
		else if (low[curr] == enumerate[parent])
			return true;
		// if neither of these cases apply, return false
		return false;
	}

	/**
	 * Find the lowest average (mean) latency spanning tree for the graph
	 * (i.e., a spanning tree with the lowest average latency per edge). Return
	 * it as an ArrayList of STE edges.
	 *
	 * Note that you do not need to use the STE class to represent your graph
	 * internally, you only need to use it to construct return values for this
	 * method.
	 *
	 * @return	ArrayList<STE> A list of STE objects representing the lowest
	 * 			average latency spanning tree
	 * 			Return `null` if the graph is not connected
	 */
	public ArrayList<STE> lowestAvgLatST() {
		// base case: empty graph
		if (numVert == 0)
			return null;

		// create list of STEs
		ArrayList<STE> treeEdges = new ArrayList<STE>();
		// create PQ for edges
		PrimPQ pq = new PrimPQ(numEdges);
		// create a boolean array to indicate if edges are in tree
		boolean[] inTree = new boolean[numVert];
		for (int i = 0; i < inTree.length; i++)
			inTree[i] = false;

		// prepare Prim's to start at vertex 0
		int num = 0;
		inTree[0] = true;
		for (GraphEdge curr = adjacencyList[0]; curr != null; curr = curr.getNext())
			pq.add(curr);
		// run Prim's
		while (!pq.isEmpty() && numEdges != (numVert - 1)) {
			// get the min edge from the pq. if it is redundant, skip it
			GraphEdge edge = pq.getMin();
			if (inTree[edge.getEnd()] && inTree[edge.getStart()])
				continue;
			// increment numEdges
			num++;
			// mark this edge's destination as in the tree
			inTree[edge.getEnd()] = true;
			// add the non-redundant edges starting at this next vertex to the pq
			for (GraphEdge curr = adjacencyList[edge.getEnd()]; curr != null; curr = curr.getNext()) {
				if (!inTree[curr.getEnd()])
					pq.add(curr);
			}
			// finally, add this edge to the list of STEs
			treeEdges.add(new STE(edge.getStart(), edge.getEnd()));
		}

		// check if we missed any vertices. if we did, return null, as there is no spanning tree
		if (num != (numVert - 1))
			return null;
		// otherwise, return our list of STEs
		return treeEdges;
	}

}