// Kenneth Tran
// COP 4520
// Spring 2022
// ke035936

import java.util.*;
import java.util.concurrent.locks.ReentrantLock;
import java.lang.*;
import java.time.*;

public class MinotaurParty implements Runnable {

    public boolean cupcakeExists = true;
    public static int numThreads = 100;
    public static int count = 1;
    public static long counterID;
    public static long[] guestIDs = new long[numThreads];
    public static boolean everyoneHasEaten = false;
    private final Object lock = new Object();
    ReentrantLock reentryLock = new ReentrantLock();

    public static boolean everyoneAte() {
        if (count == numThreads) {
            return true;
        }

        return false;
    }

    // public void run() {
    // Random rand = new Random();
    // while (everyoneAte() == false) {
    // int num = rand.nextInt(numThreads);
    // if (guestIDs[num] == Thread.currentThread().getId()) {
    // runProgram();
    // }
    // // System.out.println("Thread ID is : " + Thread.currentThread().getId());
    // }
    // }

    public void run() {
        System.out.println("Thread ID is : " + Thread.currentThread().getId());

        // Random rand = new Random();
        boolean iHaveEaten = false;
        // boolean ans;

        while (everyoneAte() == false) {
            // int num = rand.nextInt(numThreads);
            // if (guestIDs[num] == Thread.currentThread().getId()) {
            try {
                // while (everyoneAte() == false) {

                // ans = reentryLock.tryLock();
                // if (true) {
                // reentryLock.lock();
                iHaveEaten = maze(iHaveEaten);
                // Thread.sleep(1000);
                // }
                // reentryLock.unlock();
                // }
            } catch (Exception e) {
                e.printStackTrace();
            }
            // }
            // System.out.println("Thread ID is : " + Thread.currentThread().getId());
        }

    }

    public synchronized boolean maze(boolean iHaveEaten) {
        synchronized (lock) {
            if (counterID == Thread.currentThread().getId()) {
                // System.out.println("Counter is : " + Thread.currentThread().getId());

                counterChecksCupcake();

            } else {
                // System.out.println("Guest " + Thread.currentThread().getId() + " has eaten "
                // + iHaveEaten);
                // System.out.println(Thread.currentThread().getId() + " The cupcake exists! " +
                // cupcakeExists);

                iHaveEaten = shouldIEat(iHaveEaten);
            }

            // System.out.println("The count is " + count);
            return iHaveEaten;
        }
    }

    public synchronized void counterChecksCupcake() {
        // System.out.println("I'm called " + cupcakeExists);

        if (cupcakeExists == false) {
            count++;
            // System.out.println("Cupcake Restored!");
            requestCupcake();
        }
    }

    public synchronized boolean shouldIEat(boolean iHaveEaten) {
        if (iHaveEaten == false && cupcakeExists) {
            eatCupcake();

            return iHaveEaten = true;
        }

        return iHaveEaten;
    }

    public synchronized void eatCupcake() {
        // System.out.println(Thread.currentThread().getId() + " Cupcake has been
        // eaten!");
        cupcakeExists = false;
        // System.out.println(Thread.currentThread().getId() + " Cupcake exists! " +
        // cupcakeExists);
    }

    public synchronized void requestCupcake() {
        cupcakeExists = true;
    }

    public static void main(String[] args) {
        // Spawn n threads
        Thread guests[] = new Thread[numThreads];

        long startTime = System.nanoTime();
        try {
            MinotaurParty test = new MinotaurParty();
            for (int i = 0; i < numThreads; i++) {
                guests[i] = new Thread(test);
                guests[i].start();
                if (i == 0) {
                    // ID of the guest that will do the counting
                    counterID = guests[0].getId();
                    // System.out.println("This is the ID " + guests[0].getId());
                    // System.out.println("This is the ID " + counterID);
                }

                // guestIDs[i] = guests[i].getId();
            }

            // Random rand = new Random();
            // while (everyoneAte() == false) {
            // int num = rand.nextInt(numThreads);

            // guests[num].runProgram();
            // }
            // boolean everyoneHasEaten = false;
            // while (everyoneHasEaten == false) {
            // int num = rand.nextInt(numThreads);
            // guests[num].run();

            // if (everyoneAte()) {
            // everyoneHasEaten = true;
            // }

            // }

            for (int i = 0; i < numThreads; i++) {
                guests[i].join();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        long endTime = System.nanoTime();
        long timeElapsed = endTime - startTime;
        System.out.println("Execution time in milliseconds: " + timeElapsed / 1000000);

        // Start program
        // Assign first thread as counter
        // Randomly select threads to go through maze
        // If counter either reset or add count if cupcake does not exist
        // If not counter and cupcake exists eat if not eaten yet, else ignore
        // If counter > n threads then finish
    }
}