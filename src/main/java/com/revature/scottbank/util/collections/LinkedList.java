package com.revature.scottbank.util.collections;

import com.revature.scottbank.util.collections.List;

public class LinkedList<T> implements List<T> {

    private int size;
    private Node<T> head;
    private Node<T> tail;

    @Override
    public boolean add(T data) {
        if (data == null) { return false; }
        Node<T> newNode = new Node<>(data);
        if (head == null) {
            tail = head = newNode;
        } else {
            tail = tail.nextNode = newNode;
        }
        size++;
        return true;
    }

    @Override
    public boolean contains(T data) {
        Node<T> runner = head;
        while (runner != null) {
            if (runner.data.equals(data)) {
                return true;
            }
            runner = runner.nextNode;
        }
        return false;
    }

    @Override
    public boolean isEmpty() { return size == 0; }

    @Override
    public boolean remove(T data) {
        Node<T> prevNode = null;
        Node<T> currentNode = head;
        if (size == 0) { return false; }
        for (int i = 0; i < size; i++) {
            if (currentNode.data == data) {
                if (currentNode == head) {
                    head = currentNode.nextNode;
                } else {
                    prevNode.nextNode = currentNode.nextNode;
                }
                size++;
                return true;
            }
            prevNode = currentNode;
            currentNode = currentNode.nextNode;
        }
        return false;
    }

    @Override
    public int size() { return size; }

    @Override
    public T get(int index) {
        if (index < 0 || index >= size) {
            throw new RuntimeException("Provided index is out of bounds");
        }
        Node<T> currentNode = head;
        for (int i = 0; i <= index; i++) {
            if (i == index) {
                return currentNode.data;
            }
            currentNode = currentNode.nextNode;
        }
        return null;
    }

    private static class Node<T> {

        T data;
        Node<T> nextNode;

        public Node(T data) {
            this.data = data;
        }

    }

}
