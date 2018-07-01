package com.sunjiahui;

import java.util.concurrent.Executors;

/**
 * Hello world!
 */
public class App {
    public static void main(String[] args) {
        Executors.newFixedThreadPool(10);
        System.out.println("Hello World!");
    }
}
