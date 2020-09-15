/*
Date: 04/19/2020
Class: CS5541
Assignment: Dining withoutproblem.Philosopher
Author(s): Mohammad Jaminur Islam
*/
package withoutproblem;

public class Philosopher implements Runnable {

    final int id;
    final Chopstick leftChopstick, rightChopstick;
    final int eatCount;
    public Philosopher leftNeighbor, rightNeighbor;

    final Thread thread;

    int eatNum = 0;     // total # times withoutproblem.Philosopher has eaten
    int eatInRaw = 0;   // how many times withoutproblem.Philosopher has eaten in a row

    boolean goingToEatRequest = false;  // signal "I want to eat"
    boolean isTerminate = false;
    public Philosopher(int id, Chopstick leftChopstick,
                       Chopstick rightChopstick, int eatCount) {
        super();
        this.id = id;
        this.leftChopstick = leftChopstick;
        this.rightChopstick = rightChopstick;
        this.eatCount = eatCount;
        thread = new Thread(this);
    }


    /**
     * Main loop
     */
    public void run() {
        while (eatNum < eatCount) {
            think();
            processChopsticks(true);
            obtainChopsticksIfNecessary();
            eat();
            processChopsticks(eatNum != eatCount);  // give up chopsticks unconditionally after the last iteration
        }
    }
    void obtainChopsticksIfNecessary() {
        // indicate that we need chopsticks so the neighbors send them
        goingToEatRequest = true;
        waitForChopstick(leftChopstick);
        waitForChopstick(rightChopstick);
        goingToEatRequest = false;
    }
    /**
     * Monitor "Guarded Wait" implementation
     */
    void waitForChopstick(Chopstick chopstick) {
        if (chopstick.owner != this) {
            synchronized (chopstick) {
//                System.out.format("withoutproblem.Philosopher %d WAITING for chopstick %d.\n",
//                        id, chopstick.id);
                try {
                    while (chopstick.owner != this) {
                        // while waiting, withoutproblem.Philosopher must be able to release
                        // dirty chopstick he owns to avoid deadlock
                        processChopsticks(true);
                        chopstick.wait();
                    }
                } catch (InterruptedException e) {
                    System.out.println("InterruptedException caught");
                }
            }
        }
        System.out.format("withoutproblem.Philosopher %d picks up %s chopstick.\n", id, chopstick == leftChopstick ? "left" : "right");
    }


    /**
     * withoutproblem.Philosopher is checking his neighbor's state periodically and releases
     * chopsticks synchronously, in his thread. If isRequestRequired set to
     * true philosopher can send chopstick he owns only if the neighbor requested
     * it. If isRequestRequired is false he can release chopstick regardless
     * of neighbor's request (useful when he stops checking neighbor's state).
     */
    void processChopsticks(boolean isRequestRequired) {
        giveUpChopstickIfNecessary(leftChopstick, leftNeighbor, isRequestRequired);
        giveUpChopstickIfNecessary(rightChopstick, rightNeighbor, isRequestRequired);
    }

    /**
     * withoutproblem.Philosopher gives up only dirty chopstick he owns,
     * on request OR unconditionally (e.g. if he's finished dinner
     * and not going to track his neighbor's requests anymore)
     */
    void giveUpChopstickIfNecessary(Chopstick chopstick, Philosopher receiver, boolean isRequestRequired) {

        synchronized (chopstick) {
            if ( (receiver.goingToEatRequest || !isRequestRequired) &&  // give up chopstick only on request OR unconditionally
                    !chopstick.isClean &&                       // only dirty chopstick
                    chopstick.owner == this) {                  // only chopstick he owns
                chopstick.isClean = true;                       // wash chopstick before sending out
                chopstick.owner = receiver;
                eatInRaw = 0;                                   // reset # eats-in-raw because chopstick sent out

//                System.out.format("withoutproblem.Chopstick %d sent to withoutproblem.Philosopher %d by withoutproblem.Philosopher %d\n", chopstick.id,
//                        chopstick.owner.id, id);

                chopstick.notify();                             // notify waiting threads
            }
        }
    }


    void eat() {
        eatInRaw++;
        eatNum++;
        rightChopstick.isClean = leftChopstick.isClean = false;
        String eatersList = eaters.addEater(this);
        System.out.format("Philosopher %d starts eating. Eats in raw: %d. Eating at present: %s.\n",
                id, eatInRaw, eatersList);
//        System.out.format("withoutproblem.Philosopher %d eats.\n", id);
        StaticMethods.sleep(StaticMethods.random(100, 200));
        eaters.removeEater(this);
//        System.out.format("withoutproblem.Philosopher %d finished eating (%d times).\n", id, eatNum);
        System.out.format("Philosopher %d puts down right chopstick.\n", id);
        System.out.format("Philosopher %d puts down left chopstick.\n", id);
    }


    void think() {
//        System.out.format("withoutproblem.Philosopher %d thinks.\n", id);
        StaticMethods.sleep(StaticMethods.random(100, 200));
    }


    public void start() {
        thread.start();
    }


    public void join() {
        try {
            thread.join();
        } catch (InterruptedException e) {
            System.out.println("InterruptedException caught");
        }
    }

}
