/* *****************************************************************************
 *  Name: Yamilev Ilyas
 *  Date: 16.11.2019
 *  Description: Client program that takes an integer k as a command-line argument; reads a sequence
 *  of strings from standard input using StdIn.readString(); and prints exactly k of them, uniformly
 *  at random. Print each item from the sequence at most once.
 **************************************************************************** */

import edu.princeton.cs.algs4.StdIn;

public class Permutation {
    public static void main(String[] args) {
        int k = Integer.parseInt(args[0]);
        String str = "";
        RandomizedQueue<String> testQueue = new RandomizedQueue<String>();
        try {
            while (true) {
                str = StdIn.readString();
                testQueue.enqueue(str);
            }
        }
        catch (java.util.NoSuchElementException e) {};
        
        for (int i = 0; i < k; i++)
            System.out.printf("%s\n", testQueue.dequeue());
    }
}
