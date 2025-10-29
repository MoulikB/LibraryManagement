package COMP2450.model;

public class Stack {

    private class Node {

        Position data;
        Node next;

        /**
         * Constructor: Creates a new book instance. It holds various info about
         * the book
         * @param data the point which is being stored in this node
         */
        Node(Position data) {
            this.data = data;
        }
    }

    private Node top;

    /**
     * Pushes a new Position item to the stack
     *
     * @param item The item being pushed
     */
    public void push(Position item) {
        Node newNode = new Node(item);
        newNode.next = this.top;
        this.top = newNode;
    }

    /**
     * Pops the last most item from the stack
     *
     * @return The item being popped and removed
     */
    public Position pop() {
        if (isEmpty()) return null;
        Position item = top.data;
        this.top = top.next;
        return item;
    }

    /**
     * Checks whether the stack is empty or not
     *
     * @return a boolean confirming if stack is empty
     */
    public boolean isEmpty() {
        return this.top == null;
    }
}