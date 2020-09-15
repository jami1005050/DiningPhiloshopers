/*
Date: 04/19/2020
Class: CS5541
Assignment: Dining withoutproblem.Philosopher
Author(s): Mohammad Jaminur Islam
*/
package withoutproblem;

public class WithoutProblemMain {
    private static final int SIMULATION_MILLIS = 1000 * 10;
    public static void main(String []args) {
        final int philosophersCount = 5;
        final int eatCount = 5;

        Philosopher[] philosophers = new Philosopher[philosophersCount];
        Chopstick[] chopsticks = new Chopstick[philosophersCount];

        for (int i = 0; i < philosophersCount; i++)
            chopsticks[i] = new Chopstick(i + 1);

        for (int i = 0; i < philosophersCount; i++)
            philosophers[i] = new Philosopher(i + 1, chopsticks[i],
                    chopsticks[(i + 1) % philosophersCount], eatCount);

        //  set neighbors and assign owners: give the chopsticks to the philosopher with the lower ID
        philosophers[0].leftNeighbor = philosophers[philosophersCount - 1];
        philosophers[0].rightNeighbor = philosophers[1];
        chopsticks[0].owner = philosophers[0];
        for (int i = 1; i < philosophersCount; i++) {
            philosophers[i].leftNeighbor = philosophers[(i - 1) % philosophersCount];
            philosophers[i].rightNeighbor = philosophers[(i + 1) % philosophersCount];
            chopsticks[i].owner = philosophers[i - 1];
        }

        System.out.println("Dinner is starting!\n");
        for (int i = 0; i < philosophersCount; i++)
            philosophers[i].start();
//        try{
//            Thread.sleep(SIMULATION_MILLIS);
//            for (Philosopher philosopher : philosophers) { //after simulation time it shutdown the thread by setting boolean false
//                philosopher.isTerminate = true;
//            }
//        }catch (Exception e){
//            e.printStackTrace();
//        }
        // Stop all philosophers.
        // wait all threads to complete
        for (int i = 0; i < philosophersCount; i++)
            philosophers[i].join();
        System.out.println("Dinner is over!");
        for (Philosopher philosopher : philosophers) { //after simulation time it shutdown the thread by setting boolean false
            System.out.println("Philosopher: "+philosopher.id+" eat count is: "+philosopher.eatNum);
        }
    }
}
