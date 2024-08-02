// Imagine you have a secret decoder ring with rotating discs labeled with the lowercase alphabet. You're given a
// message s written in lowercase letters and a set of instructions shifts encoded as tuples (start_disc, end_disc,
// direction). Each instruction represents rotating the discs between positions start_disc and end_disc (inclusive)
// either clockwise (direction = 1) or counter-clockwise (direction = 0). Rotating a disc shifts the message by one
// letter for each position moved on the alphabet (wrapping around from ‘z’ to ‘a’ and vice versa).
// Your task is to decipher the message s by applying the rotations specified in shifts in the correct order.
// Your task is to decipher the message s by applying the rotations specified in shifts in the correct order.
// Example:
// In Input: s = "hello", shifts = [[0, 1, 1], [2, 3, 0], [0, 2, 1]]
// Output: jglko
// Shifts:
// (0, 1, 1) - Rotate discs 0 and 1 clockwise (h becomes i, e becomes f)
// (2, 3, 0) - Rotate discs 2 and 3 counter-clockwise (both l becomes k)
// (0, 2, 1) - Rotate discs 0, 1, and 2 clockwise (i becomes j, f becomes g, and k becomes l)

import java.util.Scanner;


public class q_no_1b {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        // Prompt user for input string
        System.out.print("Enter the string to encode: ");
        String inputString = scanner.nextLine();

        // Predefined shifts: {start_index, end_index, direction}
        int[][] shifts = {
                {0, 1, 1},  // Rotate characters from index 0 to 1 clockwise
                {2, 3, 0},  // Rotate characters from index 2 to 3 counter-clockwise
                {0, 2, 1}   // Rotate characters from index 0 to 2 clockwise
        };

        // Encode the message based on the predefined shifts
        String encodedMessage = decipherMessage(inputString, shifts);
        System.out.println("Encoded message: " + encodedMessage);

        scanner.close();  
    }


    public static String decipherMessage(String input, int[][] shifts) {
        char[] message = input.toCharArray();  // Convert string to character array for in-place modification

        // Process each shift operation
        for (int[] shift : shifts) {
            int start = shift[0];  // Starting index of the shift
            int end = shift[1];    // Ending index of the shift
            int direction = shift[2];  // Direction of rotation (1 for clockwise, 0 for counter-clockwise)

            // Validate shift range
            if (start < 0 || end >= message.length || start > end) {
                System.out.println("Invalid shift range: [" + start + ", " + end + "]");
                continue;  // Skip invalid shift ranges
            }

            // Apply the shift to the specified range of characters
            for (int i = start; i <= end; i++) {
                if (Character.isLetter(message[i])) {
                    message[i] = rotateCharacter(message[i], direction);  // Rotate the character
                }
            }
        }

        return new String(message);  // Convert character array back to string
    }

  
    private static char rotateCharacter(char c, int direction) {
        if (Character.isLowerCase(c)) {
            // Rotate lowercase character
            return (char) ((c - 'a' + (direction == 1 ? 1 : -1) + 26) % 26 + 'a');
        } else if (Character.isUpperCase(c)) {
            // Rotate uppercase character
            return (char) ((c - 'A' + (direction == 1 ? 1 : -1) + 26) % 26 + 'A');
        } else {
            return c;  // Non-alphabet characters remain unchanged
        }
    }
}
