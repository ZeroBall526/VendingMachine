package kr.ac.syu.java.chap4;

//홍가현님 제작
public class CoffeeBox {
    public CoffeeBox(int amount) {
        coffeeAmount = amount;
    }
    int coffeeAmount;
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
