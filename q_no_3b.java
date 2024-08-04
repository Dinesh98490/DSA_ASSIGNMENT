
// Imagine you are managing a bus service with passengers boarding at various stops along the route. Your task is to
// optimize the boarding process by rearranging the order of passengers at intervals of k stops, where k is a positive
// integer. If the total number of passengers is not a multiple of k, then the remaining passengers at the end of the route
// should maintain their original order.

import java.util.InputMismatchException;
import java.util.Scanner;

public class q_no_3b {
    // Definition of ListNode for singly linked list
    static class ListNode {
        int value;
        ListNode next;
        ListNode(int value) { this.value = value; }
    }

    // Method to reverse nodes in k groups
    public static ListNode reverseKGroup(ListNode head, int k) {
        if (head == null || k <= 1) return head; // Edge case: empty list or k <= 1

        ListNode dummy = new ListNode(0); // Dummy node to handle edge cases
        dummy.next = head;
        ListNode previousGroupEnd = dummy, current = dummy, nextNode = dummy;
        int totalNodes = 0;

        // Count total number of nodes in the list
        while (current.next != null) {
            current = current.next;
            totalNodes++;
        }

        // Reverse every k nodes in the list
        while (totalNodes >= k) {
            current = previousGroupEnd.next;
            nextNode = current.next;
            for (int i = 1; i < k; i++) {
                current.next = nextNode.next;
                nextNode.next = previousGroupEnd.next;
                previousGroupEnd.next = nextNode;
                nextNode = current.next;
            }
            previousGroupEnd = current;
            totalNodes -= k;
        }

        return dummy.next; // Return the new head of the list
    }

    // Method to create a linked list from an array
    public static ListNode createList(int[] array) {
        if (array == null || array.length == 0) return null; // Edge case: empty array
        ListNode head = new ListNode(array[0]);
        ListNode current = head;
        for (int i = 1; i < array.length; i++) {
            current.next = new ListNode(array[i]);
            current = current.next;
        }
        return head;
    }

    // Method to print the linked list
    public static void printList(ListNode head) {
        while (head != null) {
            System.out.print(head.value + " ");
            head = head.next;
        }
        System.out.println(); // Print newline at the end
    }

    // Main method to interact with the user and demonstrate the functionality
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        try {
            // Input the number of elements in the list
            System.out.print("enter the number of elements in the list: ");
            int numberOfElements = scanner.nextInt();
            if (numberOfElements <= 0) {
                System.out.println("number of elements must be positive.");
                return;
            }

            int[] elementsArray = new int[numberOfElements];
            for (int i = 0; i < numberOfElements; i++) {
                System.out.print("enter the " + (i + 1) + getoridianlsuffix(i + 1) + " element of the list: ");
                elementsArray[i] = scanner.nextInt();
            }

            // Input the value of k
            System.out.print("enter the value of k: ");
            int k = scanner.nextInt();
            if (k <= 0) {
                System.out.println("value of k must be positive.");
                return;
            }

            // Create list and display original list
            ListNode head = createList(elementsArray);
            System.out.print("original list: ");
            printList(head);

            // Reverse in groups of k and display the modified list
            head = reverseKGroup(head, k);
            System.out.print("list reversed in groups of " + k + ": ");
            printList(head);

        } catch (InputMismatchException e) {
            System.out.println("Invalid input. Please enter valid integers.");
        } finally {
            scanner.close();
        }
    }

    // Helper method to get the ordinal suffix for a number
    private static String getoridianlsuffix(int number) {
        int lastDigit = number % 10;
        int lastTwoDigits = number % 100;
        if (lastDigit == 1 && lastTwoDigits != 11) {
            return "st";
        }
        if (lastDigit == 2 && lastTwoDigits != 12) {
            return "nd";
        }
        if (lastDigit == 3 && lastTwoDigits != 13) {
            return "rd";
        }
        return "th";
    }
}
