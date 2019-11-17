/* *****************************************************************************
 *  Name: Yamilev Ilyas
 *  Date: 16.11.2019
 *  Description:  A double-ended queue or deque (pronounced “deck”) is a
 *  generalization of a stack and a queue that supports adding and removing
 *  items from either the front or the back of the data structure.
 **************************************************************************** */

import java.util.Iterator;

public class Deque<Item> implements Iterable<Item> {
    
    private int size = 0;
    private Node first = null;
    private Node last = null;
    private class Node {
        Item value;
        Node next;
        Node prev;
    }
    // construct an empty deque
    public Deque() {
        
    }

    // is the deque empty?
    public boolean isEmpty() {
        return size == 0;
    }

    // return the number of items on the deque
    public int size() {
        return size;
    }

    // add the item to the front
    public void addFirst(Item item) {
        if (item == null)
            throw new IllegalArgumentException("Tried to add null object");
        Node newfirst = new Node();
        newfirst.value = item;
        if (size == 0) {
            first = newfirst;
            last = newfirst;
            first.next = null;
            first.prev = null;
            size = 1;
            return;
        }
        newfirst.next = first;
        newfirst.prev = null;
        first.prev = newfirst;
        first = newfirst;
        ++size;
    }

    // add the item to the back
    public void addLast(Item item) {
        if (item == null)
            throw new IllegalArgumentException("Tried to add null object");
        Node newlast = new Node();
        newlast.value = item;
        if (size == 0) {
            first = newlast;
            last = newlast;
            first.next = null;
            first.prev = null;
            size = 1;
            return;
        }
        last.next = newlast;
        newlast.prev = last;
        newlast.next = null;
        last = newlast;
        ++size;
    }

    // remove and return the item from the front
    public Item removeFirst() {
        if (size == 0) {
            throw new java.util.NoSuchElementException("Error: trying remove element from empty deque");
        }
        Node oldfirst = first;
        Item val = oldfirst.value;
        if (size == 1) {
            first = null;
            last = null;
            size = 0;
            return val;
        }
        first = first.next;
        first.prev = null;
        oldfirst.next = null;
        --size;
        return val;
    }

    // remove and return the item from the back
    public Item removeLast() {
        if (size == 0) {
            throw new java.util.NoSuchElementException("Error: trying remove element from empty deque");
        }
        Node oldlast = last;
        Item val = oldlast.value;
        if (size == 1) {
            first = null;
            last = null;
            size = 0;
            return val;
        }
        last = last.prev;
        last.next = null;
        oldlast.prev = null;
        --size;
        return val;
    }

    // return an iterator over items in order from front to back
    public Iterator<Item> iterator() {
        return new DequeIterator();
    }

    private class DequeIterator implements Iterator<Item> {
        private Node current = first;
        public boolean hasNext() {
            return current != null;
        }
        public Item next() {
            if (current == null)
                throw new java.util.NoSuchElementException("No more elements in deque!");
            Item item = current.value;
            current   = current.next;
            return item;
        }
        public void remove() {
            throw new UnsupportedOperationException("remove() is not supported in this implementation");
        }
    }

    // unit testing (required)
    public static void main(String[] args) {
        
    }

}