import java.util.*;

public class SlidingPuzzleSolver {
    public static int slidingPuzzle(List<List<Integer>> board) {
        // Step 1: Convert board into a string
        String target = "123450"; // The solved board state
        StringBuilder start = new StringBuilder();

        // Convert List<List<Integer>> to a single string
        for (List<Integer> row : board) {
            for (int num : row) {
                start.append(num);
            }
        }

        // Step 2: Set up BFS
        Queue<String> queue = new LinkedList<>();
        Set<String> visited = new HashSet<>();
        
        queue.offer(start.toString()); // Start BFS from the initial state
        visited.add(start.toString());

        int moves = 0;  // Count the number of moves

        // Step 3: Define possible swaps (adjacent positions of 0 in a 1D string)
        int[][] directions = {
            {1, 3},     // index 0 → can move right (1), down (3)
            {0, 2, 4},  // index 1 → left (0), right (2), down (4)
            {1, 5},     // index 2 → left (1), down (5)
            {0, 4},     // index 3 → up (0), right (4)
            {1, 3, 5},  // index 4 → left (3), up (1), right (5)
            {2, 4}      // index 5 → up (2), left (4)
        };

        // Step 4: BFS loop
        while (!queue.isEmpty()) {
            int size = queue.size(); // Number of states at the current level

            for (int i = 0; i < size; i++) {
                String current = queue.poll();  // Get the current board state

                if (current.equals(target)) {
                    return moves;  // If we reach the solved state, return moves
                }

                int zeroIndex = current.indexOf('0'); // Find position of empty tile (0)

                // Try all possible swaps based on adjacent positions
                for (int neighbor : directions[zeroIndex]) {
                    String nextState = swap(current, zeroIndex, neighbor); // Swap 0 with a neighbor

                    if (!visited.contains(nextState)) { // If the state is new
                        queue.offer(nextState);
                        visited.add(nextState);
                    }
                }
            }

            moves++; // Increase move count
        }

        return -1; // If unsolvable, return -1
    }

    // Function to swap characters in a string and return a new state
    private static String swap(String s, int i, int j) {
        char[] arr = s.toCharArray();  // Convert string to char array
        char temp = arr[i];
        arr[i] = arr[j];
        arr[j] = temp;
        return new String(arr);  // Convert back to string
    }

    // Main method to test the function
    public static void main(String[] args) {
        List<List<Integer>> board1 = Arrays.asList(
            Arrays.asList(1, 2, 0),
            Arrays.asList(4, 5, 3)
        );

        List<List<Integer>> board2 = Arrays.asList(
            Arrays.asList(1, 2, 3),
            Arrays.asList(5, 4, 0)
        );

        System.out.println(slidingPuzzle(board1)); // Output: 1
        System.out.println(slidingPuzzle(board2)); // Output: -1
    }
}
