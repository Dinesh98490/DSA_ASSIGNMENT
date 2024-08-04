// Imagine a small community with n houses, numbered 0 to n-1. Some houses have restrictions against becoming
// friends, represented by pairs in the restrictions list. For example, if [0, 1] is in the list, houses 0 and 1 cannot be
// directly or indirectly friends (through common friends).
// Residents send friend requests to each other, represented by pairs in the requests list. Your task is to determine if
// each friend request can be accepted based on the current friendship network and the existing restrictions.
// Example2:
// Input:
// Number of houses: 3
// Restrictions: [0, 1]
// [0, 1] (House 0 and House 1 cannot be friends)
// Friend requests: [[0, 2], [2,1]]
// [0, 2] (House 0 requests friendship with House 2)
// [2, 1] (House 2 requests friendship with House 1)
// Outcome: [approved, denied]
// Request 0: Approved (House 0 and 2 don't have any restrictions)
// Request 1: Denied (House 2 and 1 would be indirectly connected through House 0, violating the restriction).
// Example 2:
// Input:
// Number of Houses = 5
// Restrictions = [[0, 1], [1, 2], [2, 3]]
// Requests = [[0, 4], [1, 2], [3, 1], [3, 4]]
// Output: [approved, denied, approved, denied]
// Explanation:
// Request 0: house 0 and house 4 can be friends, so they become direct friends.
// Request 1: house 1 and house 2 cannot be friends since they are directly restricted.
// Request 2: house 3 and house 1 can be friends, so they become direct friends.
// Request 3: house 3 and house 4 cannot be friends since person 0 and person 1 would be indirect friends ((0--4--3--1)


import java.util.ArrayList;
import java.util.List;

public class q_no_3a {
    // Union-Find class for managing connected components
    static class UnionFind {
        private int[] parent; // parent[i] points to the parent of i, if parent[i] == i, then i is a root
        private int[] rank;   // rank[i] stores the rank (approximate depth) of tree rooted at i

        // Constructor initializes the Union-Find structure
        public UnionFind(int size) {
            parent = new int[size];
            rank = new int[size];
            for (int i = 0; i < size; i++) {
                parent[i] = i; // each element is initially its own parent
                rank[i] = 1;   // initial rank is 1 for all elements
            }
        }

        // Find the root of element x with path compression
        public int find(int x) {
            if (parent[x] != x) {
                parent[x] = find(parent[x]); // path compression
            }
            return parent[x];
        }

        // Union operation to join two sets containing elements x and y
        public void union(int x, int y) {
            int rootX = find(x);
            int rootY = find(y);
            if (rootX != rootY) {
                // Union by rank
                if (rank[rootX] > rank[rootY]) {
                    parent[rootY] = rootX;
                } else if (rank[rootX] < rank[rootY]) {
                    parent[rootX] = rootY;
                } else {
                    parent[rootY] = rootX;
                    rank[rootX]++;
                }
            }
        }

        // Check if two elements x and y are in the same set
        public boolean isConnected(int x, int y) {
            return find(x) == find(y);
        }
    }

    // Method to process friend requests with given restrictions
    public static List<String> processFriendRequests(int n, int[][] restrictions, int[][] requests) {
        UnionFind uf = new UnionFind(n);
        List<String> result = new ArrayList<>();

        // Process each friend request
        for (int[] request : requests) {
            int personA = request[0];
            int personB = request[1];
            boolean canBeFriends = true;

            // Check if the request violates any restriction
            for (int[] restriction : restrictions) {
                int restrictedA = restriction[0];
                int restrictedB = restriction[1];
                // If personA and personB can be connected, it should not form any restricted connection
                if ((uf.isConnected(personA, restrictedA) && uf.isConnected(personB, restrictedB)) ||
                    (uf.isConnected(personA, restrictedB) && uf.isConnected(personB, restrictedA))) {
                    canBeFriends = false; // violates restriction, cannot be friends
                    break;
                }
            }

            // If no restriction is violated, approve the friend request
            if (canBeFriends) {
                uf.union(personA, personB); // connect the sets
                result.add("approved");
            } else {
                result.add("denied");
            }
        }

        return result; // return the results of all requests
    }

    // Main method to test the friend request processing
    public static void main(String[] args) {
        // Example 1
        int n1 = 3;
        int[][] restrictions1 = {{0, 1}};
        int[][] requests1 = {{0, 2}, {2, 1}};
        System.out.println(processFriendRequests(n1, restrictions1, requests1)); // [approved, denied]

        // Example 2
        int n2 = 5;
        int[][] restrictions2 = {{0, 1}, {1, 2}, {2, 3}};
        int[][] requests2 = {{0, 4}, {1, 2}, {3, 1}, {3, 4}};
        System.out.println(processFriendRequests(n2, restrictions2, requests2)); // [approved, denied, approved, denied]
    }
}
