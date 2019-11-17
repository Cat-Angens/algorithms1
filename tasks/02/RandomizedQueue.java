/* *****************************************************************************
 *  Name: Yamilev Ilyas
 *  Date: 16.11.2019
 *  Description: randomized queue is similar to a stack or queue, except that the item removed is
 *  chosen uniformly at random among items in the data structure.
 **************************************************************************** */

import edu.princeton.cs.algs4.StdRandom;
import java.util.Iterator;

public class RandomizedQueue<Item> implements Iterable<Item> {
    
    private Item[] vals;
    private int size = 0;
    private void resize(int capacity) {
        Item[] copy = (Item[]) new Object[capacity];
        for (int i = 0; i < size; i++)
            copy[i] = vals[i];
        vals = copy;
    }
    
    // construct an empty randomized queue
    public RandomizedQueue() {
        vals = (Item[]) new Object[2];
    }

    // is the randomized queue empty?
    public boolean isEmpty() {
        return size == 0;
    }

    // return the number of items on the randomized queue
    public int size() {
        return size;
    }

    // add the item
    public void enqueue(Item item) {
        if (item == null)
            throw new IllegalArgumentException("Cannot add null objext in randomized queue");
        if (size == vals.length)
            resize(size * 2);
        vals[size] = item;
        ++size;
    }

    // remove and return a random item
    public Item dequeue() {
        if (size == 0)
            throw new java.util.NoSuchElementException("Cannot return element from empty queue");
        int ind = StdRandom.uniform(size);
        Item res = vals[ind];
        vals[ind] = vals[size - 1];
        vals[size - 1] = null;
        --size;
        return res;
    }

    // return a random item (but do not remove it)
    public Item sample() {
        if (size == 0)
            throw new java.util.NoSuchElementException("Cannot return element from empty queue");
        int ind = StdRandom.uniform(size);
        return vals[ind];
    }

    // return an independent iterator over items in random order
    public Iterator<Item> iterator() {
        return new RandomizedQueueIterator();
    }
    
    private class RandomizedQueueIterator implements Iterator<Item> {
        private int[] order = StdRandom.permutation(size);
        private int currentInd = 0;
        public boolean hasNext() {
            return currentInd != order.length;
        }
        public Item next() {
            if (currentInd == order.length)
                throw new java.util.NoSuchElementException("No more elements in randomized queue!");
            int ind = order[currentInd++];
            return vals[ind];
        }
        public void remove() {
            throw new UnsupportedOperationException("remove() is not supported in this implementation");
        }
    }

    // unit testing (required)
    public static void main(String[] args) {
        RandomizedQueue<Double> test = new RandomizedQueue<Double>();
        System.out.printf("is empty: %b\n", test.isEmpty());
        System.out.printf("Size: %d\n", test.size());
        test.enqueue(1.);
        System.out.printf("Value: %f\n", test.dequeue());
        test.enqueue(2.);
        test.enqueue(3.);
        test.enqueue(4.);
        System.out.printf("Size: %d\n", test.size());
        System.out.printf("Value: %f\n", test.dequeue());
        System.out.printf("Size: %d\n", test.size());
        System.out.printf("Value: %f\n", test.sample());
        System.out.printf("Size: %d\n", test.size());
        System.out.printf("Value: %f\n", test.dequeue());
        System.out.printf("is empty: %b\n", test.isEmpty());
        System.out.printf("Size: %d\n", test.size());
        System.out.printf("Value: %f\n", test.dequeue());
        System.out.printf("is empty: %b\n", test.isEmpty());
        for (Double elem : test)
            System.out.printf("Elem: %f\n", elem);
        test.enqueue(5.);
        test.enqueue(6.);
        test.enqueue(7.);
        test.enqueue(8.);
        test.enqueue(9.);
        for (Double elem : test)
            System.out.printf("Elem: %f\n", elem);
        for (Double elem : test)
            System.out.printf("Elem: %f\n", elem);
    }

}