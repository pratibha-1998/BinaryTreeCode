import java.util.*;

class TreeNode<T> {
    T val;
    TreeNode<T> left, right, random;

    // Constructor to create a new node
    TreeNode(T val) {
        this.val = val;
        this.left = null;
        this.right = null;
        this.random = null;
    }
}

public class CloneBinaryTree {

    public static TreeNode<Integer> cloneBinaryTreeRandomPointer(TreeNode<Integer> root) {
        // Step 1: Edge case - If the tree is empty, return null
        if (root == null) {
            return null;
        }

        // Step 2: Create a HashMap to store the mapping of original nodes to their cloned nodes
        Map<TreeNode<Integer>, TreeNode<Integer>> map = new HashMap<>();

        // Step 3: First pass - Clone all nodes (without connecting pointers yet)
        cloneNodes(root, map);

        // Step 4: Second pass - Assign left, right, and random pointers
        assignPointers(root, map);

        // Step 5: Return the cloned root from the map
        return map.get(root);
    }

    /**
     * First pass: Create cloned nodes and store them in a HashMap
     */
    private static void cloneNodes(TreeNode<Integer> node, Map<TreeNode<Integer>, TreeNode<Integer>> map) {
        if (node == null) {
            return;
        }

        // Create a cloned node for the current node
        TreeNode<Integer> clonedNode = new TreeNode<>(node.val);

        // Store the mapping from the original node to its cloned version
        map.put(node, clonedNode);

        // Recursively clone left and right subtrees
        cloneNodes(node.left, map);
        cloneNodes(node.right, map);
    }

    /**
     * Second pass: Assign left, right, and random pointers in the cloned tree
     */
    private static void assignPointers(TreeNode<Integer> node, Map<TreeNode<Integer>, TreeNode<Integer>> map) {
        if (node == null) {
            return;
        }

        // Get the cloned version of the current node
        TreeNode<Integer> clonedNode = map.get(node);

        // Assign left, right, and random pointers using the map
        clonedNode.left = map.get(node.left);      // Assign the cloned left child
        clonedNode.right = map.get(node.right);    // Assign the cloned right child
        clonedNode.random = map.get(node.random);  // Assign the cloned random pointer

        // Recursively assign pointers for left and right subtrees
        assignPointers(node.left, map);
        assignPointers(node.right, map);
    }

    // Helper function to print the tree (for debugging)
    public static void printTree(TreeNode<Integer> root) {
        if (root == null) {
            return;
        }
        System.out.println("Node: " + root.val + ", " +
                           "Left: " + (root.left != null ? root.left.val : "null") + ", " +
                           "Right: " + (root.right != null ? root.right.val : "null") + ", " +
                           "Random: " + (root.random != null ? root.random.val : "null"));

        printTree(root.left);
        printTree(root.right);
    }

    // Main function to test cloning
    public static void main(String[] args) {
        // Create a sample binary tree
        TreeNode<Integer> root = new TreeNode<>(1);
        root.left = new TreeNode<>(2);
        root.right = new TreeNode<>(3);
        root.left.left = new TreeNode<>(4);
        root.left.right = new TreeNode<>(5);

        // Assign random pointers
        root.random = root.right;       // 1.random -> 3
        root.left.random = root.left.right;  // 2.random -> 5
        root.right.random = root.left;  // 3.random -> 2
        root.left.left.random = root;   // 4.random -> 1
        root.left.right.random = root.left.left; // 5.random -> 4

        System.out.println("Original Tree:");
        printTree(root);

        // Clone the binary tree
        TreeNode<Integer> clonedRoot = cloneBinaryTreeRandomPointer(root);

        System.out.println("\nCloned Tree:");
        printTree(clonedRoot);
    }
}
====================================================Explenation==========================================

  🔹 Key Points of the Cloning Binary Tree Program
1️⃣ Problem Understanding
Given a binary tree where each node has:
left → Points to the left child
right → Points to the right child
random → Points to any random node in the tree
Goal: Clone the entire tree while preserving all pointers.
2️⃣ Approach (Two-Pass Algorithm)
First pass → Clone nodes and store them in a HashMap

Create new nodes with the same values as the original.
Store them in a HashMap:
Key → Original node
Value → Cloned node
Do NOT assign any pointers yet.
Second pass → Assign left, right, and random pointers

Use the HashMap to set left, right, and random pointers for each cloned node.
3️⃣ HashMap Usage
Key → Original node reference
Value → Corresponding cloned node
This helps to quickly find the cloned version of any original node.
4️⃣ Recursion for Tree Traversal
First pass (cloneNodes):
Creates and stores cloned nodes in the HashMap.
Calls itself recursively for left and right children.
Second pass (assignPointers):
Uses the HashMap to correctly assign left, right, and random pointers.
Calls itself recursively for left and right children.
5️⃣ Key Code Snippets
Storing cloned nodes:
java
Copy
Edit
map.put(originalNode, clonedNode);
Setting pointers using HashMap:
java
Copy
Edit
clonedNode.left = map.get(originalNode.left);
clonedNode.right = map.get(originalNode.right);
clonedNode.random = map.get(originalNode.random);
6️⃣ Time & Space Complexity
Time Complexity: O(N) → Each node is visited twice.
Space Complexity: O(N) → HashMap stores N nodes.
7️⃣ Final Output
The cloned tree has:
The same structure as the original tree.
The same values in each node.
Correct left, right, and random pointers.
