public class BST<V> {
    private class Node {
        String key;
        V value;
        Node left, right;

        Node(String key, V value) {
            this.key = key;
            this.value = value;
        }
    }

    private Node root;

    public V search(String key) {
        return searchRecursive(root, key);
    }

    private V searchRecursive(Node node, String key) {
        if (node == null) return null;
        if (key.equals(node.key)) return node.value;
        if (key.compareTo(node.key) < 0) return searchRecursive(node.left, key);
        return searchRecursive(node.right, key);
    }

    public void insert(String key, V value) {
        root = insertRecursive(root, key, value);
    }

    private Node insertRecursive(Node node, String key, V value) {
        if (node == null) return new Node(key, value);
        if (key.compareTo(node.key) < 0) node.left = insertRecursive(node.left, key, value);
        else if (key.compareTo(node.key) > 0) node.right = insertRecursive(node.right, key, value);
        return node;
    }

    public void delete(String key) {
        root = deleteRecursive(root, key);
    }

    private Node deleteRecursive(Node node, String key) {
        if (node == null) return null;

        if (key.compareTo(node.key) < 0) node.left = deleteRecursive(node.left, key);
        else if (key.compareTo(node.key) > 0) node.right = deleteRecursive(node.right, key);
        else {
            if (node.left == null) return node.right;
            else if (node.right == null) return node.left;

            Node successor = findMin(node.right);
            node.key = successor.key;
            node.value = successor.value;
            node.right = deleteRecursive(node.right, successor.key);
        }
        return node;
    }

    private Node findMin(Node node) {
        while (node.left != null) node = node.left;
        return node;
    }

    public Node getRoot() {
        return root;
    }
}