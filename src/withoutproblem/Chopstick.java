/*
Date: 04/19/2020
Class: CS5541
Assignment: Dining withoutproblem.Philosopher
Author(s): Mohammad Jaminur Islam
*/
package withoutproblem;

public class Chopstick {
    final int id;
    volatile Philosopher owner;
    volatile boolean isClean = false;
    public Chopstick(int id) {
        super();
        this.id = id;
    }
}