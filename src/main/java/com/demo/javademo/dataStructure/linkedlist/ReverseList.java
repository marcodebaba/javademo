package com.demo.javademo.dataStructure.linkedlist;

public class ReverseList {
    public static void main(String[] args) {
        Node n1 = new Node(1);
        Node n2 = new Node(2);
        Node n3 = new Node(3);
        Node n4 = new Node(4);
        Node n5 = new Node(5);
        n1.next = n2;
        n2.next = n3;
        n3.next = n4;
        n4.next = n5;
        n5.next = null;

        Node reverse = reverseList(n1);
        System.out.println(reverse.getData());
        printAll(reverse);
    }

    public static void printAll(Node head) {
        Node node = head.getNext();
        while (node != null) {
            System.out.print(node.getData() + ",");
            node = node.getNext();
        }
        System.out.println();
    }

    public static Node reverseList(Node head) {
        if (head == null) throw new IllegalArgumentException("链表为空");

        Node current = head, preview = null;
        while (current != null) {
            Node next = current.next;
            current.next = preview;
            preview = current;
            current = next;
        }

        return preview;
    }

    static class Node {
        private int data;
        private Node next;

        public Node(int data) {
            this.data = data;
        }

        public int getData() {
            return data;
        }

        public void setData(int data) {
            this.data = data;
        }

        public Node getNext() {
            return next;
        }

        public void setNext(Node next) {
            this.next = next;
        }
    }
}
