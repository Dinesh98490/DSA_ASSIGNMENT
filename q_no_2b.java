// Imagine you're at a movie theater with assigned seating. You have a seating chart represented by an array nums
// where nums[i] represents the seat number at row i. You're looking for two friends to sit together, considering their
// seat preferences and your limitations:
// Seating Distance: Your friends prefer to sit close together, with a maximum allowed seat difference of indexDiff.
// Imagine indexDiff = 2, meaning they'd be comfortable within 2 seats of each other (e.g., seats 3 and 5).
// Movie Preference: They also want similar movie tastes, requiring a difference in their seat numbers (abs(nums[i] -
// nums[j])) to be within valueDiff. Think of valueDiff = 1 as preferring movies with similar ratings (e.g., seats 4 and
// 5 for movies rated 4.5 and 5 stars).
// Your task is to determine if there are two friends (represented by two indices i and j) who can sit together while
// satisfying both the seating distance and movie preference limitations.
// Example:
// Input:
// Seating chart: nums = [2, 3, 5, 4, 9]
// Seating distance limit: indexDiff = 2
// Movie preference limit: valueDiff = 1
// Output: true
// Possible pairs:
// (0, 1): Seat difference (1) within indexDiff, movie difference (1) within valueDiff.
// (3, 4): Seat difference (1) within indexDiff, movie difference (5) exceeds valueDiff.
// In this scenario, you can find two friends who can sit together, so the answer would be true.


import java.util.TreeSet;

public class q_no_2b {

    // Method to determine if there are two numbers in the array 
    // such that their indices differ by at most indexDiff and 
    // their absolute difference is at most valueDiff
    public static boolean CanSitTogether(int[] nums, int indexDiff, int valueDiff) {
        // TreeSet to maintain the sorted order of elements within the sliding window
        TreeSet<Integer> set = new TreeSet<>();

        for (int i = 0; i < nums.length; i++) {
            // Find the closest elements to the left and right within the valueDiff range
            Integer floor = set.floor(nums[i] + valueDiff);
            Integer ceiling = set.ceiling(nums[i] - valueDiff);

            // Check if any of the found elements satisfy the condition
            if ((floor != null && Math.abs(floor - nums[i]) <= valueDiff) ||
                (ceiling != null && Math.abs(ceiling - nums[i]) <= valueDiff)) {
                return true;
            }

            // Add the current number to the set
            set.add(nums[i]);

            // Remove the element that is out of the sliding window range
            if (i >= indexDiff) {
                set.remove(nums[i - indexDiff]);
            }
        }

        // If no such pair is found, return false
        return false;
    }

    // Main method to test the canSitTogether function
    public static void main(String[] args) {
        int[] nums = {2, 3, 5, 4, 9};
        int indexDiff = 2;
        int valueDiff = 1;

        // Call the function and print the result
        boolean output = CanSitTogether(nums, indexDiff, valueDiff);
        System.out.println("Can friends sit together? " + output); // Output: true
    }
}
