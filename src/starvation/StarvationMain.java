/*
Date: 04/19/2020
Class: CS5541
Assignment: Dining withoutproblem.Philosopher
Author(s): Mohammad Jaminur Islam
*/
package starvation;
import utility.Fork;
public class StarvationMain {
    private static final int SIMULATION_MILLIS = 1000 * 10;
    public static void main(String []args){
        Fork[] forks = new Fork[5];
        for(int i = 0; i < 5; i++) {
            forks[i] = new Fork(i);
        }
        PhilosophersInStarvation[] philosophers = new PhilosophersInStarvation[5];
        Thread [] threads = new Thread[5];
        for(int i = 0; i < 5; i++) {
            int fork1 = i;
            int fork2 = (i+1) % 5;
            int highFork;
            int lowFork;
            if(fork1 > fork2) {
                lowFork = fork2;
                highFork = fork1;
            } else { // breaking circular dependency
                lowFork = fork1;
                highFork = fork2;
            }
            philosophers[i] = new PhilosophersInStarvation(i, 50, forks[lowFork], forks[highFork]);
            threads[i] = new Thread(philosophers[i] ); //starting the thread
        }
        for(int i = 0; i < 5; i++) {
            threads[i].start();
        }
        try {
            Thread.sleep(SIMULATION_MILLIS);
            // Stop all philosophers.
            for (PhilosophersInStarvation philosopher : philosophers) {
                philosopher.isTerminate = true;
            }
        }catch (Exception e){

        }finally {
            for (PhilosophersInStarvation philosopher : philosophers) {
                System.out.println("withoutproblem.Philosopher: "+philosopher.id+" => No of Turns to Eat = "+philosopher.eat_num);
                // printing the number of times it got the chance to eat.
            }
        }
    }
}