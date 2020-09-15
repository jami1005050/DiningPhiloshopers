/*
Date: 04/19/2020
Class: CS5541
Assignment: Dining withoutproblem.Philosopher
Author(s): Mohammad Jaminur Islam
*/
package deadlock;
import utility.Fork;

import java.util.Random;
class PhilosophersInDeadlock extends Thread {
    private static Random rnd = new Random();
    private static int cnt = 0;
    private int num = cnt++;
    private Fork leftFork;
    private Fork rightFork;
    static int waiting = 0;
    volatile boolean isTummyFull = false;
    public int eat_num = 0;
    public PhilosophersInDeadlock(Fork left, Fork right) {// initialization
        leftFork = left;
        rightFork = right;
//        start();
    }
    public void think() { //thinking for a random time
        System.out.println(this + " is thinking");
        if (waiting > 0) {
            try {
                sleep(rnd.nextInt(waiting));
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
    }
    public void eat() {
        synchronized (leftFork) { //taking left fork
            System.out.println(this + " has " + this.leftFork + " Waiting for " + this.rightFork);
            synchronized (rightFork) { //taking right fork
                System.out.println(this + " eating");
            }
        }
        eat_num++; // incrementing number of time got chance to eat
    }
    @Override
    public String toString() {
        return "withoutproblem.Philosopher " + num;
    }
    @Override
    public void run() {
        while (!isTummyFull) {
            think();
            eat();
        }
    }
}

