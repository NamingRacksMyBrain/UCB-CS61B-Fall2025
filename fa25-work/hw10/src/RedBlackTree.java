public class RedBlackTree<T extends Comparable<T>> {

    /* Root of the tree. */
    RBTreeNode<T> root;

    static class RBTreeNode<T> {

        final T item;
        boolean isBlack;
        RBTreeNode<T> left;
        RBTreeNode<T> right;

        /**
         * Creates a RBTreeNode with item ITEM and color depending on ISBLACK
         * value.
         *
         * @param isBlack
         * @param item
         */
        RBTreeNode(boolean isBlack, T item) {
            this(isBlack, item, null, null);
        }

        /**
         * Creates a RBTreeNode with item ITEM, color depending on ISBLACK
         * value, left child LEFT, and right child RIGHT.
         *
         * @param isBlack
         * @param item
         * @param left
         * @param right
         */
        RBTreeNode(boolean isBlack, T item, RBTreeNode<T> left,
                   RBTreeNode<T> right) {
            this.isBlack = isBlack;
            this.item = item;
            this.left = left;
            this.right = right;
        }

        boolean isLeaf() {
            return this.left == null && this.right == null;
        }
    }

    /**
     * Creates an empty RedBlackTree.
     */
    public RedBlackTree() {
        root = null;
    }

    /**
     * Flips the color of node and its children. Assume that NODE has both left
     * and right children
     *
     * @param node
     */
    void flipColors(RBTreeNode<T> node) {
        if (node.left.isBlack && node.right.isBlack && isRed(node)) {
            node.isBlack = true;
            node.left.isBlack = false;
            node.right.isBlack = false;
        } else if (isRed(node.left) && isRed(node.right) && node.isBlack) {
            node.isBlack = false;
            node.left.isBlack = true;
            node.right.isBlack = true;
        }
    }

    /**
     * Rotates the given node to the right. Returns the new root node of
     * this subtree. For this implementation, make sure to swap the colors
     * of the new root and the old root!
     *
     * @param node
     * @return
     */
    RBTreeNode<T> rotateRight(RBTreeNode<T> node) {
        RBTreeNode<T> targetRoot = node.left;
        node.left = targetRoot.right;
        targetRoot.right = node;
        swapColor(node, targetRoot);
        return targetRoot;
    }

    /**
     * Rotates the given node to the left. Returns the new root node of
     * this subtree. For this implementation, make sure to swap the colors
     * of the new root and the old root!
     *
     * @param node
     * @return
     */
    RBTreeNode<T> rotateLeft(RBTreeNode<T> node) {
        RBTreeNode<T> targetRoot = node.right;
        node.right = targetRoot.left;
        targetRoot.left = node;
        swapColor(node, targetRoot);
        return targetRoot;
    }

    /**
     * Swaps the color of node N1 and node N2.
     *
     * @param n1
     * @param n2
     */
    private void swapColor(RBTreeNode<T> n1, RBTreeNode<T> n2) {
        boolean tmp1 = n1.isBlack;
        n1.isBlack = n2.isBlack;
        n2.isBlack = tmp1;
    }

    /**
     * Helper method that returns whether the given node is red. Null nodes (children or leaf
     * nodes) are automatically considered black.
     *
     * @param node
     * @return
     */
    private boolean isRed(RBTreeNode<T> node) {
        return node != null && !node.isBlack;
    }

    /**
     * Inserts the item into the Red Black Tree. Colors the root of the tree black.
     *
     * @param item
     */
    public void insert(T item) {
        root = insertHelper(root, item);
        root.isBlack = true;
    }

    /**
     * Helper method to insert the item into this Red Black Tree. Comments have been provided to help break
     * down the problem. For each case, consider the scenario needed to perform those operations.
     * Make sure to also review the other methods in this class!
     *
     * @param node
     * @param item
     * @return
     */
    private RBTreeNode<T> insertHelper(RBTreeNode<T> node, T item) {
        // Insert new red leaf node.
        if (node == null) {
            node = new RBTreeNode<>(false, item);
        }

        // Handle normal binary search tree insertion.
        if (item.compareTo(node.item) < 0) {
            node.left = insertHelper(node.left, item);
        } else if (item.compareTo(node.item) > 0) {
            node.right = insertHelper(node.right, item);
        } else {
            return node;
        }

        /**
         *  Deal with Invariant: If a node has one red child, it must be on the left.
         *  Rotate left operation
         */
        if (!isRed(node.left) && isRed(node.right)) {
            node = rotateLeft(node);
        }

        /**
         *  Deal with Invariant: No red node can have a red parent (every red node’s parent is black)
         *  Rotate right operation
         */
        if (isRed(node.left) && isRed(node.left.left)) {
            node = rotateRight(node);
        }

        /**
        *  Deal with Invariant: No node can have two red children
        *  Color flip
        */
        if (isRed(node.right) && isRed(node.left)) {
            flipColors(node);
        }
        return node;
    }
}
