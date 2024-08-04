// Imagine you're on a treasure hunt in an enchanted forest represented by a binary tree root. Each node in the tree has
// a value representing a magical coin. Your goal is to find the largest collection of coins that forms a magical grove.
// A magical grove is defined as a subtree where:
// Every coin's value on left subtree less than to the value of the coin directly above it (parent node).
// Every coin's value on right subtree is greater than to the value of the coin directly above it (parent node).
// Every coin in the grove needs to be binary search tree.
// Your task is to find the magical grove with the highest total value of coins.
// Examples:
// Forest: [1,4,3,2,4,2,5,null,null,null,null,null,null,4,6]
// Output: 20
// Largest Magical Grove: The subtree rooted at node 3, with a total value of 20 (3+2+5+4+6).
// [
    class TreeNode {
        int value;
        TreeNode left;
        TreeNode right;
        TreeNode(int x) { value = x; }
    }
    
    public class q_no_4b {
        // Helper class to store details about each subtree
        static class SubtreeDetails {
            boolean isBST;    // Indicates if the subtree is a BST
            int minValue;     // Minimum value in the subtree
            int maxValue;     // Maximum value in the subtree
            int totalSum;     // Sum of all node values in the subtree
    
            SubtreeDetails(boolean isBST, int minValue, int maxValue, int totalSum) {
                this.isBST = isBST;
                this.minValue = minValue;
                this.maxValue = maxValue;
                this.totalSum = totalSum;
            }
        }
    
        private int maxBSTSum; // Tracks the maximum sum of any BST subtree
    
       
        public int largestBSTSubtreeSum(TreeNode root) {
            maxBSTSum = 0; // Initialize the maximum sum
            computeSubtreeDetails(root); // Start the post-order traversal
            return maxBSTSum;
        }
    
        /**
         * Post-order traversal to compute details of each subtree.
         *
         * @param node The root node of the current subtree.
         * @return Details about the current subtree.
         */
        private SubtreeDetails computeSubtreeDetails(TreeNode node) {
            if (node == null) {
                // Base case: An empty subtree is a valid BST with sum 0
                return new SubtreeDetails(true, Integer.MAX_VALUE, Integer.MIN_VALUE, 0);
            }
    
            // Recursively get details about the left and right subtrees
            SubtreeDetails leftDetails = computeSubtreeDetails(node.left);
            SubtreeDetails rightDetails = computeSubtreeDetails(node.right);
    
            // Check if the current subtree is a valid BST
            if (leftDetails.isBST && rightDetails.isBST &&
                node.value > leftDetails.maxValue && node.value < rightDetails.minValue) {
                // Calculate the total sum for the current subtree
                int currentSubtreeSum = leftDetails.totalSum + rightDetails.totalSum + node.value;
                // Update the maximum sum if the current subtree's sum is larger
                maxBSTSum = Math.max(maxBSTSum, currentSubtreeSum);
                // Return updated details for the current subtree
                return new SubtreeDetails(true, Math.min(node.value, leftDetails.minValue), Math.max(node.value, rightDetails.maxValue), currentSubtreeSum);
            } else {
                // Return details for a non-BST subtree
                return new SubtreeDetails(false, 0, 0, 0);
            }
        }
    
        public static void main(String[] args) {
            q_no_4b magicalGroveFinder = new q_no_4b();
    
            // Construct the binary tree
            TreeNode root = new TreeNode(1);
            root.left = new TreeNode(4);
            root.right = new TreeNode(3);
            root.left.left = new TreeNode(2);
            root.left.right = new TreeNode(4);
            root.right.left = new TreeNode(2);
            root.right.right = new TreeNode(5);
            root.right.right.left = new TreeNode(4);
            root.right.right.right = new TreeNode(6);
    
            // Output the largest sum of any BST subtree
            System.out.println("Largest BST Subtree Sum: " + magicalGroveFinder.largestBSTSubtreeSum(root)); // Expected Output: 20
        }
    }
    