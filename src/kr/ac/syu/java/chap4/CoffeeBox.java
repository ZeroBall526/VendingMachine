package kr.ac.syu.java.chap4;

public class CoffeeBox {
    private int coffeeAmount = 300;
    public boolean useCoffee(int amount) {
        if( coffeeAmount > amount) {
            coffeeAmount -= amount;
            return true;
        }
        else {
            return false;
        }
    }
}
