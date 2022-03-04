// Kenneth Tran
// COP 4520
// Spring 2022
// ke035936

import java.util.*;
import java.util.concurrent.locks.ReentrantLock;
import java.time.*;

public class MinotaurCrystalVase implements Runnable {
    public static int numThreads = 5;
    public static long[] guestIDs = new long[numThreads];
    public static long vaseStartTime = System.currentTimeMillis();
    public static long vaseEndTime = vaseStartTime + 3 * 1000; // 10 seconds
    public static boolean vaseViewingOpen = true;

    public void run() {
        System.out.println("Thread ID is : " + Thread.currentThread().getId());

        while (isViewingOpen()) {
            try {
                // while (everyoneAte() == false) {

                // ans = reentryLock.tryLock();
                // if (true) {
                // reentryLock.lock();
                maze();
                // Thread.sleep(1000);
                // }
                // reentryLock.unlock();
                // }
            } catch (Exception e) {
                e.printStackTrace();
            }
            // System.out.println("Thread ID is : " + Thread.currentThread().getId());
        }

    }

    public static boolean isViewingOpen() {
        if (System.currentTimeMillis() > vaseEndTime) {
            return false;
        }

        return true;
    }

    public synchronized void maze() {
        // synchronized (lock) {
        // // if (counterID == Thread.currentThread().getId()) {
        // // // System.out.println("Counter is : " + Thread.currentThread().getId());

        // // // counterChecksCupcake();

        System.out.println("Viewing vase.");
        // // } else {

        // // // iHaveEaten = shouldIEat(iHaveEaten);
        // // }

        // // return iHaveEaten;
        // }
    }

    public static void main(String[] args) {
        // Spawn n threads
        Thread guests[] = new Thread[numThreads];

        System.out.println("Vase room is now open.");

        long startTime = System.nanoTime();
        try {
            MinotaurCrystalVase test = new MinotaurCrystalVase();
            for (int i = 0; i < numThreads; i++) {
                guests[i] = new Thread(test);
                guests[i].start();

                guestIDs[i] = guests[i].getId();
            }

            for (int i = 0; i < numThreads; i++) {
                guests[i].join();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        System.out.println("Vase room is now closed.");

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