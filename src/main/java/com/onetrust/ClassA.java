package com.onetrust;

import java.util.*;
import java.util.concurrent.ConcurrentSkipListSet;

public class ClassA {

    public static void main(String[] args) {
        CustomerServiceImpl impl = new CustomerServiceImpl();
        impl.addCustomers(new Customer("tech", 11, "Devops"));
        impl.addCustomers(new Customer("com", 11, "Data Scientist"));
        impl.addCustomers(new Customer("interview", 9, "Developer"));
        impl.addCustomers(new Customer("dynamicallyBlunt", 10, "Developer"));


        System.out.println(impl.customers);
        Collections.sort(impl.customers, new CustomerComparator());
        System.out.println(impl.customers);
    }
}

class CustomerServiceImpl {

    List<Customer> customers = new ArrayList<>();

    public void addCustomers(Customer newCust){
        customers.add(newCust);
    }
}


class CustomerComparator implements Comparator<Customer>{

    @Override
    public int compare(Customer o1, Customer o2) {
        if(o1.getAge() < o2.getAge())
            return -1;
        else if (o1.getAge() > o2.getAge())
            return 1;

        //Both scenario not matching then it is same age
        return 0;
    }
}

class Customer implements Comparable<Customer>{

    private String name;
    private int age;
    private String profession;

    Customer(String name, int age, String profession){
        this.name = name;
        this.age = age;
        this.profession = profession;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public String getProfession() {
        return profession;
    }

    public void setProfession(String profession) {
        this.profession = profession;
    }

    public String toString(){
        return "name: " + this.name
                + " age: " + this.age
                + " profession:" + this.profession;
    }

    @Override
    public int hashCode(){
        return this.name.toLowerCase(Locale.ROOT).hashCode();
    }

    @Override
    public boolean equals(Object obj){
        Customer cObj = (Customer)obj;
        return this.getName().equalsIgnoreCase(cObj.getName());
    }

    @Override
    public int compareTo(Customer o) {
        return this.name.toLowerCase(Locale.ROOT).compareTo(o.getName());
    }
}
