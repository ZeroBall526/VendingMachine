package kr.ac.syu.java.chap4;

class Animal{
    String name;
    int age;

    void eat(){
        System.out.println("animal eat");
    }
    void speak(){
        System.out.println("animal speak");
    }
    void love() {
        System.out.println("animal love");
    }
}

class Dog extends Animal{
    @Override
    public void speak() {
        System.out.println("멍멍!");
    }
}

class Cat extends Animal{
    @Override
    public void speak() {
        System.out.println("야옹!");
    }
}

class Chicken extends Animal{
    @Override
    public void speak() {
        System.out.println("꼬꼬댁!");
    }
}

public class PolymorphismTest {
    public static void main(String[] args) {
        Animal myDog = new Dog();
        Animal myCat = new Cat();
        Animal myChicken = new Chicken();

        myDog.speak();
        myCat.speak();
        myChicken.speak();
    }
}
//100,500,1000,5000,10000 자판기에 삽입 가능
