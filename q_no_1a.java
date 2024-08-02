// a)
// Imagine you're a scheduling officer at a university with n classrooms numbered 0 to n-1. Several different courses
// require classrooms throughout the day, represented by an array of classes classes[i] = [starti, endi], where starti is
// the start time of the class and endi is the end time (both in whole hours). Your goal is to assign each course to a
// classroom while minimizing disruption and maximizing classroom utilization. Here are the rules:
// • Priority Scheduling: Classes with earlier start times have priority when assigning classrooms. If multiple
// classes start at the same time, prioritize the larger class (more students).
// • Dynamic Allocation: If no classroom is available at a class's start time, delay the class until a room
// becomes free. The delayed class retains its original duration.
// • Room Release: When a class finishes in a room, that room becomes available for the next class with the
// highest priority (considering start time and size).
// Your task is to determine which classroom held the most classes throughout the day. If multiple classrooms are
// tied, return the one with the lowest number.
import java.util.Arrays;
import java.util.PriorityQueue;
import java.util.Scanner;
import java.util.Arrays;
import java.util.PriorityQueue;
import java.util.Scanner;

public class q_no_1a {

    // Inner class to hold information about each class
    public static class classInfo {
        int start; // Start time of the class
        int end;   // End time of the class
        int size;  // Size of the class

        // Constructor to initialize class information
        public classInfo(int start, int end, int size) {
            this.start = start;
            this.end = end;
            this.size = size;
        }
    }

    // Method to find the most utilized room
    public static int findMostUtilizedRoom(int numRooms, classInfo[] classes) {
        // Sort classes by start time; if start times are equal, sort by size in descending order
        Arrays.sort(classes, (a, b) -> {
            if (a.start == b.start) {
                return b.size - a.size; // Prefer larger size classes first if start times are equal
            }
            return a.start - b.start; // Otherwise, sort by start time
        });

        // PriorityQueue to keep track of the earliest end times of classes in each room
        PriorityQueue<int[]> roomEndTimes = new PriorityQueue<>((a, b) -> a[0] - b[0]);
        // Initialize rooms with end time 0
        for (int i = 0; i < numRooms; i++) {
            roomEndTimes.offer(new int[]{0, i}); // {end_time, room_number}
        }

        // Array to count the number of classes held in each room
        int[] roomClassCounts = new int[numRooms];

        // Process each class
        for (classInfo classInfo : classes) {
            int start = classInfo.start;
            int end = classInfo.end;

            // Get the earliest available room
            int[] earliest = roomEndTimes.poll();

            // Schedule the class in the earliest available room
            if (earliest[0] <= start) {
                roomClassCounts[earliest[1]]++; // Increment class count for the room
                roomEndTimes.offer(new int[]{end, earliest[1]}); // Update end time for the room
            } else {
                // If no room is available at the start time, delay the class
                roomClassCounts[earliest[1]]++;
                roomEndTimes.offer(new int[]{earliest[0] + (end - start), earliest[1]});
            }
        }

        // Find the room with the most classes held
        int maxClasses = 0;
        int roomWithMostClasses = 0;
        for (int i = 0; i < numRooms; i++) {
            // Update the room with the highest number of classes
            if (roomClassCounts[i] > maxClasses) {
                maxClasses = roomClassCounts[i];
                roomWithMostClasses = i;
            } else if (roomClassCounts[i] == maxClasses) {
                // If two rooms have the same count, prefer the one with the smaller index
                roomWithMostClasses = Math.min(roomWithMostClasses, i);
            }
        }

        return roomWithMostClasses; // Return the index of the most utilized room
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        System.out.print("Enter the number of rooms: ");
        int numRooms = scanner.nextInt();

        System.out.print("Enter the number of classes: ");
        int numClasses = scanner.nextInt();

        // Validate input
        if (numRooms <= 0) {
            System.out.println("Error: Number of rooms must be a positive integer.");
            return;
        }

        if (numClasses <= 0) {
            System.out.println("Error: Number of classes must be a positive integer.");
            return;
        }

        // Array to hold class information
        classInfo[] classes = new classInfo[numClasses];
        System.out.println("Enter the start time, end time, and size of each class:");

        // Read class details from user input
        for (int i = 0; i < numClasses; i++) {
            System.out.println("Class " + (i + 1) + ":");
            System.out.print("  Start time: ");
            int start = scanner.nextInt();
            System.out.print("  End time: ");
            int end = scanner.nextInt();
            System.out.print("  Size: ");
            int size = scanner.nextInt();

            // Validate class times
            if (end <= start) {
                System.out.println("Error: End time must be greater than start time for class " + (i + 1) + ".");
                return;
            }

            classes[i] = new classInfo(start, end, size);
        }

        // Find the most utilized room and display the result
        int result = findMostUtilizedRoom(numRooms, classes);
        System.out.println("The room with the most classes held is: Room " + (result + 1));

        scanner.close();
    }
}
