package com.demo.javademo.dataStructure.linkedlist;

import java.util.HashMap;
import java.util.Map;

public class LRULinkedList {
    private Map<String, LruNode> cache = new HashMap<>();
    private int count;
    private int capacity;
    private LruNode head, tail;

    public LRULinkedList(int capacity) {
        this.count = 0;
        this.capacity = capacity;
        this.head = new LruNode();
        head.pre = null;
        this.tail = new LruNode();
        tail.next = null;
        head.next = tail;
        tail.pre = head;
    }

    public static void main(String[] args) {
        LRULinkedList cache = new LRULinkedList(4);
        cache.set("key1", 1);
        cache.set("key2", 2);
        cache.set("key3", 3);
        cache.set("key4", 4);
        cache.get("key2");
        cache.set("key5", 5);
        cache.get("key2");
    }

    public int get(String key) {
        LruNode node = cache.get(key);
        if (node == null) return -1;
        this.moveToFirst(node);
        return node.value;
    }

    public void set(String key, int value) {
        LruNode node = cache.get(key);
        if (node == null) {
            LruNode newNode = new LruNode();
            newNode.key = key;
            newNode.value = value;
            this.cache.put(key, newNode);
            this.addNodeToHead(newNode);
            ++count;
            if (count > capacity) {
                // pop the tail
                LruNode tail = this.popTail();
                this.cache.remove(tail.key);
                --count;
            }
        } else {
            node.value = value;
            this.moveToFirst(node);
        }
    }

    // 将结点移动到头结点
    private void moveToFirst(LruNode node) {
        this.removeNode(node);
        this.addNodeToHead(node);
    }

    // 加入头结点
    private void addNodeToHead(LruNode node) {
        node.pre = head;
        node.next = head.next;
        head.next.pre = node;
        head.next = node;
    }

    private void removeNode(LruNode node) {
        node.pre.next = node.next;
        node.next.pre = node.pre;
    }

    private LruNode popTail() {
        LruNode res = tail.pre;
        this.removeNode(res);
        return res;
    }

    static class LruNode {
        String key;
        int value;
        LruNode pre;
        LruNode next;

        public LruNode() {
        }

        public LruNode(String key, int value) {
            this.key = key;
            this.value = value;
        }
    }
}
