/*
Date: 04/19/2020
Class: CS5541
Assignment: Dining withoutproblem.Philosopher
Author(s): Mohammad Jaminur Islam
*/
package withoutproblem;

class eaters {

    static java.util.List<Philosopher> eaters = new java.util.ArrayList<Philosopher>();

    public synchronized static String addEater(Philosopher philosopher) {
        eaters.add(philosopher);
        return eatersToString();
    }

    public synchronized static String removeEater(Philosopher philosopher) {
        eaters.remove(philosopher);
        return eatersToString();
    }

    private static String eatersToString() {
        String s = "";
        for (Philosopher philosopher : eaters)
            s += philosopher.id + " ";
        return s.trim();
    }
}
