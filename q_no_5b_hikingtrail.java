// b)
// Imagine you're on a challenging hiking trail represented by an array nums, where each element represents the
// altitude at a specific point on the trail. You want to find the longest consecutive stretch of the trail you can hike
// while staying within a certain elevation gain limit (at most k).
// Constraints:
// You can only go uphill (increasing altitude).
// The maximum allowed elevation gain between two consecutive points is k.
// Goal: Find the longest continuous hike you can take while respecting the elevation gain limit.
// Examples:
// Input:
// Trail: [4, 2, 1, 4, 3, 4, 5, 8, and 15], Elevation gain limit (K): 3
// Output: 5
// Explanation
// Longest hike: [1, 3, 4, 5, 8] (length 5) - You can climb from 1 to 3 (gain 2), then to 4 (gain 1), and so on, all within
// the limit.
// Invalid hike: [1, 3, 4, 5, 8, 15] - The gain from 8 to 15 (7) exceeds the limit

public class q_no_5b_hikingtrail {
    public static int longestHike(int[] nums, int k) {
        if (nums == null || nums.length == 0) {
            return 0;
        }

        int maxLength = 0; 
        int start = 0; 

        for (int end = 1; end < nums.length; end++) {
            // Check if the elevation gain between consecutive points exceeds k
            while (start < end && nums[end] - nums[end - 1] > k) {
                // Move the start index to the current end point
                // This skips invalid segments where the gain is too high
                start = end;
            }

            // Check if the hike is strictly increasing and within the elevation gain limit
            if (nums[end] > nums[end - 1] && nums[end] - nums[end - 1] <= k) {
                // Update the maximum length if the current segment is longer
                maxLength = Math.max(maxLength, end - start + 1);
            } else {
                // Reset the start index if the current segment is not valid
                start = end;
            }
        }

        return maxLength; // Return the length of the longest valid hike
    }

    public static void main(String[] args) {
        int[] trail = {4, 2, 1, 4, 3, 4, 5, 8, 15};
        int k = 3;
        // Output the length of the longest hike with the given elevation gain limit
        System.out.println("Longest hike: " + longestHike(trail, k)); // Expected Output: 5
    }
}
