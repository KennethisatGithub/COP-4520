// Kenneth Tran
// COP 4520
// Spring 2022
// ke035936

import java.util.*;

public class MinotaurCrystalVase implements Runnable {
    public static int numThreads = 2;
    public static long[] guestIDs = new long[numThreads];
    public static long vaseStartTime = System.currentTimeMillis();
    public static long vaseEndTime = vaseStartTime + 3 * 1000; // 10 seconds
    public static boolean vaseViewingOpen = true;
    public static ArrayLock arrayLock = new ArrayLock(numThreads);
    public static List<Long> testSet = new ArrayList<>(numThreads);
    public static Set<Long> testSet2 = new HashSet<>(numThreads);

    public static long testNum = 0;
    public static int count = 0;
    public static int count2 = 0;

    public void run() {
        // System.out.println("Thread ID is : " + Thread.currentThread().getId());

        while (isViewingOpen()) {
            try {
                arrayLock.lock();
                CrystalVaseViewing();
                arrayLock.unlock();
                // Thread.sleep(2000);

            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public static void CrystalVaseViewing() {
        try {
            // System.out.println("I am using the critical area : " +
            // Thread.currentThread().getId());
            testSet2.add(Thread.currentThread().getId());

            if (count2 < 100) {
                testSet.add(Thread.currentThread().getId());
                count2++;
            }

            if (count < 3) {
                testNum += Thread.currentThread().getId();
                count++;
            }
            // Thread.sleep(500);

            // System.out.println("Viewing vase." + count);
            // count++;
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static boolean isViewingOpen() {
        if (System.currentTimeMillis() > vaseEndTime) {
            return false;
        }

        return true;
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

        // System.out.println(testSet);
        // for (int i = 0; i < testSet.size(); i++) {
        // System.out.println(testSet.get(i));
        // }
        // System.out.println(testSet2);
        // for (Long item : testSet) {
        // System.out.println(item);
        // }
        System.out.println(testSet.size());
        // System.out.println(testNum);
        // Start program
        // Assign first thread as counter
        // Randomly select threads to go through maze
        // If counter either reset or add count if cupcake does not exist
        // If not counter and cupcake exists eat if not eaten yet, else ignore
        // If counter > n threads then finish
    }
}