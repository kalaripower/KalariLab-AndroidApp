package com.example.kalarilab;

import androidx.fragment.app.Fragment;

public class Node{
    private Fragment value;
    private Node next;

    public Node(Fragment value) {
        this.value = value;
    }

    public void setNext(Node next) {
        this.next = next;
    }

    public Node getNext() {
        return next;
    }

    public Fragment getValue() {
        return value;
    }

    @Override
    public String toString() {
        return  value.toString();
    }
}
