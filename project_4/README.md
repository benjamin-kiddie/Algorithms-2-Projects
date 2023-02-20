# Network Analysis

## Project Overview
Assigned as project 4 of course CS 1501 (Algorithms and Data Structures 2). Requires implementing a network graph representation that implements the following methods:

1. `lowestLatencyPath(int u, int w)` - Finds the lowest latency path from vertex u to vertex w.
2. `bandwidthAlongPath(ArrayList<Integer> p)` - Finds the bandwidth along the path given by the `ArrayList<Integer>` p.
3. `copperOnlyConnected()` - Determines if the graph is fully connected using only copper links.
4. `connectedTwoVertFail()` - Determines if the graph would remain connected if any two vertices were to fail.
5. `lowestAvgLatST()` - Constructs the lowest latency spanning tree of the graph.

To facilitate this, I have implemented GraphEdge and Path objects along with priority quees for use in DIjkstra's and Prim's algorithms.

## Contributors
Benjamin Kiddie - NetAnalysis, Path, GraphEdge, DijkstraPQ, and PrimPQ implementations.

Dr. Nicholas Farnan - STE object and driver program. Project design and assignment.