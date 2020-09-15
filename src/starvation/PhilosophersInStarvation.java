/*
Date: 04/19/2020
Class: CS5541
Assignment: Dining withoutproblem.Philosopher
Author(s): Mohammad Jaminur Islam
*/
package starvation;
import utility.Fork;
class PhilosophersInStarvation implements Runnable {
    int id; //philosopher id
    long eatTime;
    Fork lowFork;
    Fork highFork;
    int eat_num=0; //to check the starvation
    boolean isTerminate = false;
    public PhilosophersInStarvation(int id, long eatTime, Fork lowFork, Fork highFork) {//initialization of object
        this.id = id;
        this.eatTime = eatTime;
        this.lowFork = lowFork;
        this.highFork = highFork;
    }

    public void run() {
        long lastAteTime = System.currentTimeMillis();
        while(!isTerminate) {
            // to prevent deadlocks, we use the resource heiarchy strategy:
            // 1) apply acyclic heiarchy to resources
            // 2) grab lower resource to high resource always
            System.out.println("Philosopher: "+id+" is thinking");
            synchronized(lowFork) { //left fork as monitor
                System.out.println("Philosopher: "+id+" picked the left fork");
                synchronized(highFork) { // right fork as monitor
                    System.out.println("Philosopher: "+id+" picked the right fork");

                    eat_num++; //increment after each eat
                    System.out.println(id + " ate and starved for " + (System.currentTimeMillis() - lastAteTime));
                    try{
                        if(eatTime > 0 ) {
                            Thread.sleep(eatTime); //random time eating
                        }
                    } catch (InterruptedException e) {
                        System.exit(0);
                    }
                    lastAteTime = System.currentTimeMillis();
                }
                System.out.println("Philosopher: "+id+" released the right fork");
            }
            System.out.println("Philosopher: "+id+" released the left fork");
        }
    }
}