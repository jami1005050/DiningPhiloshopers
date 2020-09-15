/*
Date: 04/19/2020
Class: CS5541
Assignment: Dining withoutproblem.Philosopher
Author(s): Mohammad Jaminur Islam
*/
package deadlock;

import utility.Fork;

public class DeadlockMain {
    public  static void main(String []args) {
        PhilosophersInDeadlock[] philosophers = new PhilosophersInDeadlock[5];
        PhilosophersInDeadlock.waiting = 8;
        Thread[] thread_array = new Thread[5];
        Fork left = new Fork(), right = new Fork(), first = left;
        int i = 0;
        while (i < philosophers.length - 1) { // creating circular dependency
            philosophers[i] = new PhilosophersInDeadlock(left, right);
            left = right;
            right = new Fork();
            thread_array[i] = new Thread(philosophers[i], Integer.toString(i));
            thread_array[i].start();
            i++;
        }
        philosophers[i] = new PhilosophersInDeadlock(left, first);
        thread_array[i] = new Thread(philosophers[i], Integer.toString(i));
        thread_array[i].start();
    }

}
