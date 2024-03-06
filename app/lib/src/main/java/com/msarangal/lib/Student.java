package com.msarangal.lib;

/**
 * Singleton implementation of Student class
 */
public class Student {

    private String name;

    public String getName() {
        return name;
    }

    public int getAge() {
        return age;
    }

    private int age;
    private static Student instance;

    private Student(String name, int age) {
        this.name = name;
        this.age = age;
    }

    public static synchronized Student getInstance(String name, int age) {
        if (instance == null) {
            synchronized (Student.class) {
                instance = new Student(name, age);
            }
        }
        return instance;
    }
}

class University {
    public static void main(String[] args) {
        //experimentWithSingleton();

        Hamburger hamburger = Hamburger.builder()
                .cheese(true)
                .onions(false)
                .pickles(true)
                .build();

        System.out.println("Hamburger cheese:" + hamburger.hasCheese() + " onions: " + hamburger.hasOnions() + " pickles: " + hamburger.hasPickles());
    }

    private static void experimentWithSingleton() {
        Thread threadOne = new Thread(new RunnableOne());
        threadOne.start();

        Thread threadTwo = new Thread(() -> System.out.println("thread code"));
        threadTwo.start();
    }

    private static class RunnableOne implements Runnable {
        @Override
        public void run() {
            Student manu = Student.getInstance("Mandeep", 32);
            System.out.println("Student name: " + manu.getName() + ", age: " + manu.getAge());
        }
    }

    private static class RunnableTwo implements Runnable {
        @Override
        public void run() {
            Student manu = Student.getInstance("Navdeep", 28);
            System.out.println("Student name: " + manu.getName() + ", age: " + manu.getAge());
        }
    }
}