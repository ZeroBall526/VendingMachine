package kr.ac.syu.java.chap4;

//홍가현님 제작
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
    public boolean availableCoffee(int amountTest) {
        if( coffeeAmount >= amountTest) {
            return true;
        }
        else {
            return false;
        }
    }
}
