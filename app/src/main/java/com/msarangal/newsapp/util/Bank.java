package com.msarangal.newsapp.util;

import java.util.Arrays;

public class Bank {

    public static int getInterestRate() {
        return interestRate;
    }

    private static int interestRate;

    // Makes this code thread safe
    public synchronized void updateInterestRate() {
        interestRate += 2;
    }
}