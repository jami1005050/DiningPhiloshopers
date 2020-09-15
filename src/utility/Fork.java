/*
Date: 04/19/2020
Class: CS5541
Assignment: Dining withoutproblem.Philosopher
Author(s): Mohammad Jaminur Islam
*/
package utility;
public class Fork {
    public boolean status = false; // false means the fork is not in use
    // true means the fork is in usew
    int id;

    public Fork(int id) {
        this.id=id; // another constructor initialization of the id
    }
    public Fork(){
        //default constructor
    }
    private static int cnt = 0;
    private int num = cnt++;
    @Override
    public String toString() {
        return "utility.Fork " + num;
    }
}
