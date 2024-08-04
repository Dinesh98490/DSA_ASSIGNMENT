// Imagine you're a city planner tasked with improving the road network between key locations (nodes) represented
// by numbers 0 to n-1. Some roads (edges) have known travel times (positive weights), while others are under
// construction (weight -1). Your goal is to modify the construction times on these unbuilt roads to achieve a specific
// travel time (target) between two important locations (from source to destination).
// Constraints:
// You can only modify the travel times of roads under construction (-1 weight).
// The modified times must be positive integers within a specific range. [1, 2 * 10^9]
// You need to find any valid modification that achieves the target travel time.
// Examples:
// Input:
// City: 5 locations
// Roads: [[4, 1,-1], [2, 0,-1],[0,3,-1],[4,3,-1]]
// Source: 0, Destination: 1, Target time: 5 minutes
// Output: [[4,1,1],[2,0,1],[0,3,3],[4,3,1]]
// Solution after possible modification



import java.util.*;

public class q_no_4a {
    // Represents an edge in the graph with a target node and weight
    static class Edge {
        int target, weight;

        Edge(int target, int weight) {
            this.target = target;
            this.weight = weight;
        }
    }

    // Represents a node in the priority queue with an ID and distance
    static class Node {
        int id, distance;

        Node(int id, int distance) {
            this.id = id;
            this.distance = distance;
        }
    }

    
    public static List<int[]> modifyRoads(int numNodes, int[][] roads, int start, int end, int maxTime) {
        // Create the graph as an adjacency list
        List<Edge>[] graph = new ArrayList[numNodes];
        for (int i = 0; i < numNodes; i++) {
            graph[i] = new ArrayList<>();
        }

        // List to store roads currently under construction
        List<int[]> underConstruction = new ArrayList<>();
        for (int[] road : roads) {
            if (road[2] == -1) {
                // Road is under construction
                underConstruction.add(road);
            } else {
                // Road has a defined weight
                graph[road[0]].add(new Edge(road[1], road[2]));
                graph[road[1]].add(new Edge(road[0], road[2]));
            }
        }

        // Compute the shortest path without considering roads under construction
        int initialDistance = dijkstra(graph, start, end, numNodes);

        // If the initial shortest path is already valid, return the original roads
        if (initialDistance <= maxTime) {
            return Arrays.asList(roads);
        }

        // Attempt to modify the roads under construction
        for (int[] road : underConstruction) {
            // Temporarily set the road weight to 1 and add to the graph
            road[2] = 1;
            graph[road[0]].add(new Edge(road[1], road[2]));
            graph[road[1]].add(new Edge(road[0], road[2]));

            // Recalculate the shortest path with the new road weight
            int newDistance = dijkstra(graph, start, end, numNodes);

            // If the new distance meets the target time, adjust the road weight
            if (newDistance <= maxTime) {
                // Calculate the remaining time required
                int remainingTime = maxTime - (initialDistance - 1);
                road[2] = remainingTime;

                // Update the graph with the modified road weight
                updateEdgeWeight(graph, road[0], road[1], remainingTime);
                break;
            }
        }

        return Arrays.asList(roads);
    }

    

    private static int dijkstra(List<Edge>[] graph, int source, int destination, int numNodes) {
        PriorityQueue<Node> pq = new PriorityQueue<>(Comparator.comparingInt(node -> node.distance));
        int[] distances = new int[numNodes];
        Arrays.fill(distances, Integer.MAX_VALUE);
        distances[source] = 0;
        pq.add(new Node(source, 0));

        while (!pq.isEmpty()) {
            Node current = pq.poll();
            if (current.id == destination) {
                return current.distance;
            }

            // Explore neighbors of the current node
            for (Edge edge : graph[current.id]) {
                int newDist = current.distance + edge.weight;
                if (newDist < distances[edge.target]) {
                    distances[edge.target] = newDist;
                    pq.add(new Node(edge.target, newDist));
                }
            }
        }

        // If destination is unreachable, return a large number
        return distances[destination];
    }

   
    private static void updateEdgeWeight(List<Edge>[] graph, int from, int to, int newWeight) {
        for (Edge edge : graph[from]) {
            if (edge.target == to) {
                edge.weight = newWeight;
            }
        }
        for (Edge edge : graph[to]) {
            if (edge.target == from) {
                edge.weight = newWeight;
            }
        }
    }

    public static void main(String[] args) {
        int numNodes = 5;
        int[][] roads = {
                {4, 1, -1}, // {start, end, weight}
                {2, 0, -1},
                {0, 3, -1},
                {4, 3, -1}
        };
        int start = 0;
        int end = 1;
        int maxTime = 5;

        List<int[]> modifiedRoads = modifyRoads(numNodes, roads, start, end, maxTime);

        // Print the modified roads
        for (int[] road : modifiedRoads) {
            System.out.println(Arrays.toString(road));
        }
    }
}
