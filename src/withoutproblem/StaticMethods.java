/*
Date: 04/19/2020
Class: CS5541
Assignment: Dining withoutproblem.Philosopher
Author(s): Mohammad Jaminur Islam
*/
package withoutproblem;

class StaticMethods {

    /**
     * Generates random integer [Min, Max]
     */
    public static int random(int Min, int Max) {
        return Min + (int) (Math.random() * ((Max - Min) + 1));
    }

    /**
     * Sleeps delay ms
     */
    public static void sleep(int delay)
    {
        try {
            Thread.sleep(delay);
        } catch (InterruptedException e) {
            System.out.println("InterruptedException caught");
        }
    }
}
