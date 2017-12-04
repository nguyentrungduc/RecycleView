package com.example.sony.recycleview;

import java.util.ArrayList;

/**
 * Created by Sony on 12/4/2017.
 */

public class Person {
    private String name;
    private int age;


    public Person(String name, int age) {
        this.name = name;
        this.age = age;
    }

    public Person() {
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

    public static ArrayList<Person> createArrayList(){
        ArrayList<Person> personArrayList = new ArrayList<>();
        for(int i = 0; i < 30; i ++){
            personArrayList.add(new Person("nam",20));
        }
        return personArrayList;
    }
}
