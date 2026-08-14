package pod4;

import java.util.ArrayList;
import java.util.List;

public class SelfBalancingTree {

    private Node root;

    private static class Node {
        int key;
        Node left, right;
        int height;

        Node(int key) {
            this.key = key;
            this.height = 1;
        }
    }

    // ---- ADD (with rebalancing) ----
    public void add(int key) {
        root = addRecursive(root, key);
    }

    private Node addRecursive(Node node, int key) {
        if (node == null) return new Node(key);

        if (key < node.key) {
            node.left = addRecursive(node.left, key);
        } else if (key > node.key) {
            node.right = addRecursive(node.right, key);
        } else {
            return node;
        }

        updateHeight(node);
        return rebalance(node);
    }

    // ---- REBALANCING LOGIC ----
    private Node rebalance(Node node) {
        int balance = getBalance(node);

        if (balance > 1) {
            if (getBalance(node.left) < 0) {
                node.left = rotateLeft(node.left);
            }
            return rotateRight(node);
        }

        if (balance < -1) {
            if (getBalance(node.right) > 0) {
                node.right = rotateRight(node.right);
            }
            return rotateLeft(node);
        }

        return node;
    }

    private Node rotateRight(Node y) {
        Node x = y.left;
        Node t2 = x.right;

        x.right = y;
        y.left = t2;

        updateHeight(y);
        updateHeight(x);
        return x;
    }

    private Node rotateLeft(Node x) {
        Node y = x.right;
        Node t2 = y.left;

        y.left = x;
        x.right = t2;

        updateHeight(x);
        updateHeight(y);
        return y;
    }

    private void updateHeight(Node node) {
        node.height = 1 + Math.max(height(node.left), height(node.right));
    }

    private int height(Node node) {
        return node == null ? 0 : node.height;
    }

    private int getBalance(Node node) {
        return node == null ? 0 : height(node.left) - height(node.right);
    }

    // ---- SEARCH ----
    public boolean search(int key) {
        return searchRecursive(root, key);
    }

    private boolean searchRecursive(Node node, int key) {
        if (node == null) return false;
        if (key == node.key) return true;
        return key < node.key
                ? searchRecursive(node.left, key)
                : searchRecursive(node.right, key);
    }

    // ---- IN-ORDER LIST ----
    public List<Integer> inorder() {
        List<Integer> result = new ArrayList<>();
        inorderRecursive(root, result);
        return result;
    }

    private void inorderRecursive(Node node, List<Integer> result) {
        if (node == null) return;
        inorderRecursive(node.left, result);
        result.add(node.key);
        inorderRecursive(node.right, result);
    }

    // ---- Overall tree height ----
    public int treeHeight() {
        return height(root);
    }

    public boolean isEmpty() {
        return root == null;
    }
}
