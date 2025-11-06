package COMP2450.model;


import com.google.common.base.Preconditions;

public class LinkedListStack<T> implements Stack<T>{

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

    public void push(T item) {
        Preconditions.checkNotNull(item);
        Node<T> newNode = new Node<>( item);
        Node<T> prevTop = top;

        top = newNode;
        top.next = prevTop;

        size++;
    }

    public T pop() throws EmptyStackException {
        if (top == null) {
            throw new EmptyStackException("Empty stack");
        }

        T output = top.data;
        top = top.next;      // move the top pointer down one node
        size--;
        return output;
    }


    public int size() {
        return this.size;
    }

    public boolean isEmpty() {
        return this.size == 0;
    }

    public T peek() throws EmptyStackException {
        if (top == null) {
            throw new EmptyStackException("Stack is empty");
        }
        return top.data;
    }
}
