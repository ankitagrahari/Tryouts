package com;

abstract class Animal{
    public abstract void makeNoise();
    public abstract void move();
}

abstract class Canine extends Animal{
    public void wagTail(){
        System.out.println("Wagging");
    }

    @Override
    public void move() {
        System.out.println("Run");
    }
}
class Dog extends Canine{
    public void fetch(){
        System.out.println("fetch");
    }
    @Override
    public void makeNoise() {
        System.out.println("Bark");
    }
}
public class Inheritance {

    public static void main(String[] args) {
        Dog d = new Dog();
        d.makeNoise();
        d.move();
        d.wagTail();
        d.fetch();

        Canine c = new Dog();
        c.makeNoise();
        c.move();
        c.wagTail();

        String s = "yes";
        String s1 = "yes";
        if(s==s1)
            System.out.println("abc");
    }
}
