// Kenneth Tran
// COP 4520
// Spring 2022
// ke035936

import java.util.*;
import java.lang.*;
import java.time.*;

public class PrimeCalculator extends Thread {
    public static int sum = 0;
    public static int sharedCount = 2;
    public static int numToCalculate = 100000000;

    // We create an array to check which values are prime
    public static Boolean[] primes = new Boolean[numToCalculate];

    public static void calculatePrimesSingleThread(int numToCalc) {
        // We create an array to check which values are prime
        Boolean[] primes = new Boolean[numToCalc];
        Arrays.fill(primes, Boolean.TRUE);

        // Using Sieve method for determining primes
        for (int i = 2; i <= Math.sqrt(numToCalc); i++) {
            if (primes[i] == true) {
                for (int j = i * i; j < numToCalc; j = i + j) {
                    primes[j] = false;
                }
            }
        }

        calculateNumOfPrimes(primes);
    }

    public synchronized void run() {
        // Using Sieve method for determining primes
        for (; sharedCount <= Math.sqrt(numToCalculate); sharedCount++) {
            if (primes[sharedCount] == true) {
                for (int j = sharedCount * sharedCount; j < numToCalculate; j = sharedCount + j) {
                    primes[j] = false;
                }
            }
        }

    }

    public static int sharedCount2 = 2;
    public static int sharedNumPrimes = 0;
    public static long sumOfPrimes = 0;

    public static void calculateNumOfPrimes(Boolean[] primes) {

        for (; sharedCount2 < primes.length; sharedCount2++) {
            if (primes[sharedCount2] == true) {
                sharedNumPrimes += 1;
                sumOfPrimes += sharedCount2;
            }
        }

        System.out.println("Total number of primes found: " + sharedNumPrimes);
        System.out.println("Sum of all primes found: " + sumOfPrimes);
    }

    // public int calculateNumOfPrimes(Boolean[] primes)
    public static void main(String[] args) {
        Arrays.fill(primes, Boolean.TRUE);

        // Starting time and then running single threaded prime calculation
        long startTime = System.nanoTime();
        calculatePrimesSingleThread(100000000);
        long endTime = System.nanoTime();

        long timeElapsed = endTime - startTime;
        System.out.println("Execution time in milliseconds: " + timeElapsed /
                1000000);

        System.out.println("==================================================");

        long startTime2 = System.nanoTime();
        Thread thread1 = new PrimeCalculator();
        Thread thread2 = new PrimeCalculator();
        Thread thread3 = new PrimeCalculator();
        Thread thread4 = new PrimeCalculator();
        Thread thread5 = new PrimeCalculator();
        Thread thread6 = new PrimeCalculator();
        Thread thread7 = new PrimeCalculator();
        Thread thread8 = new PrimeCalculator();

        try {
            thread1.start();
            thread2.start();
            thread3.start();
            thread4.start();
            thread5.start();
            thread6.start();
            thread7.start();
            thread8.start();

            thread1.join();
            thread2.join();
            thread3.join();
            thread4.join();
            thread5.join();
            thread6.join();
            thread7.join();
            thread8.join();
        } catch (Exception e) {
            e.printStackTrace();
        }

        calculateNumOfPrimes(primes);

        long endTime2 = System.nanoTime();
        long timeElapsed2 = endTime2 - startTime2;
        System.out.println("Execution time in milliseconds: " + timeElapsed2 / 1000000);
    }
}