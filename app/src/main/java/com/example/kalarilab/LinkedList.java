package com.example.kalarilab;
public class LinkedList {

    private Node head = null;


    public void insert(Node node) {
        node.setNext(head);
        head = node;
    }


    public void remove() {
        if (head.getNext() != null)
            head = head.getNext();
        else head = null;
    }

    public Node getHead(){
        return head;
    }

    public Node nextNode(Node current) {
        return current.getNext();
    }


}