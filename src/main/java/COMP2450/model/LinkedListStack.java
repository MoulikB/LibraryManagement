package COMP2450.model;

import com.google.common.base.Preconditions;

public class LinkedListStack<T> implements Stack<T> {

    private static class Node<T> {
        T data;
        Node<T> next;

        private Node(T data) {
            Preconditions.checkNotNull(data);
            this.data = data;
        }
    }

    private Node<T> top;
    private int size = 0;

    /**
     * Pushes a new item onto the stack.
     *
     * @param item the item to push
     * @throws NullPointerException if item is null
     */
    public void push(T item) {
        Preconditions.checkNotNull(item);
        Node<T> newNode = new Node<>(item);
        Node<T> prevTop = top;
        top = newNode;
        top.next = prevTop;
        size++;
    }

    /**
     * Removes and returns the top item from the stack.
     *
     * @return the item that was removed
     * @throws EmptyStackException if the stack is empty
     */
    public T pop() throws EmptyStackException {
        T result = null;
        if (top == null) {
            throw new EmptyStackException("Empty stack");
        } else {
            result = top.data;
            top = top.next;
            size--;
        }
        return result;
    }

    /**
     * Returns the number of items in the stack.
     *
     * @return the size of the stack
     */
    public int size() {
        int result = this.size;
        return result;
    }

    /**
     * Checks if the stack is empty.
     *
     * @return true if the stack has no elements, false otherwise
     */
    public boolean isEmpty() {
        boolean empty = false;
        if (this.size == 0) {
            empty = true;
        }
        return empty;
    }

    /**
     * Returns the top item from the stack without removing it.
     *
     * @return the item at the top of the stack
     * @throws EmptyStackException if the stack is empty
     */
    public T peek() throws EmptyStackException {
        T result = null;
        if (top == null) {
            throw new EmptyStackException("Stack is empty");
        } else {
            result = top.data;
        }
        return result;
    }
}
