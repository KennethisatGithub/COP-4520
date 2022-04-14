// Kenneth Tran
// COP 4520
// Spring 2022
// ke035936

import java.util.*;
import java.util.concurrent.locks.ReentrantLock;
import java.util.concurrent.atomic.AtomicInteger;
import java.time.*;

public class MinotaurBirthday implements Runnable {
    public static int numThreads = 4;
    public static int numGifts   = 100;
    public static long[] servantIDs = new long[numThreads];

    public static List<Integer> giftBag = Collections.synchronizedList(new ArrayList<Integer>(numGifts));
    public static List<String> thankYouCards = Collections.synchronizedList(new ArrayList<String>(numGifts));

    public static volatile Node head;
    public static volatile Node tail;

    // public MinotaurBirthday(int size) {
    //     numGifts = size;
    //     giftBag = 
    // }
    static AtomicInteger atomCount = new AtomicInteger(1);
    static AtomicInteger emptyCount = new AtomicInteger(numGifts);


    public void run() {
        System.out.println("Thread ID is : " + Thread.currentThread().getId());
        // System.out.println("Thread ID is : " + head.key);
        // System.out.println("Thread ID is : " + head.next.key);

        while (!writtenAllCards()) {
            try {
                writeThankYous();
                // Thread.sleep(2000);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        
        try {
            // Thread.sleep(2000);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static boolean writtenAllCards() {
        // if (giftBag.isEmpty())
        // {
        //     System.out.println("Bag is empty");
        // }
        // if (head.next.key == Integer.MAX_VALUE && emptyCount.intValue() > 0) {
        //     System.out.println("List is empty");
        //     emptyCount.decrementAndGet();
        // } else {
        //     printList();
        // }
        // && head.next.key == Integer.MAX_VALUE
        if (giftBag.isEmpty() &&  head.next.key == Integer.MAX_VALUE) {
            return true;
        }
        
        // System.out.println(giftBag);
        return false;
    }

    public static synchronized void printList() {
        Node test = head.next;
        int numTest = (atomCount).intValue();
        while (test.next != null && numTest == 1)
        {
            System.out.println(" Testing LinkedList " + test.key);
            test = test.next;
        }
        if (numTest == 1) {
            System.out.println("==========================================");
        }
        atomCount.set(0);
    }

    public static synchronized Integer getAndRemove() {
        if (!giftBag.isEmpty()) {
            Integer giftNumber = Integer.valueOf(giftBag.get(0));
            // WriteCard(giftNumber);
            giftBag.remove(0);

            return giftNumber;
        }

        return null;
    }

    public void writeThankYous() {
        try {
            // System.out.println("I am using the critical area : " + Thread.currentThread().getId());
            // System.out.print("+");
            
            // if (head == null) {

            // }
            Integer giftNumber = getAndRemove();
            if (giftNumber != null) {
                add(giftNumber);
            }

            // Random rng = new Random();
            // int randomNum = rng.nextInt(numGifts);
            // if (randomNum % 3 == 0) {
            //     contains(randomNum);
            // }
            
            if (giftNumber != null) {
                remove(giftNumber);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static synchronized void WriteCard(Integer giftNumber) {
        thankYouCards.add("Thank you! " + giftNumber);
    }


    public static class Node {
        public Integer key;
        public volatile Node next;
        ReentrantLock reentryLock = new ReentrantLock();

        Node(int item) {
            this.key = item;
            this.next = null;
        }

        void lock() {
            this.reentryLock.lock();
        }

        void unlock() {
            this.reentryLock.unlock();
        }
    }

    public static boolean add(int item) {
        int key = item;

        while (true) {
            Node pred = head;
            Node curr = pred.next;
            while (curr.key < key) {
                pred = curr; curr = curr.next;
            }
            pred.lock(); curr.lock();
            try {
                if (validate(pred, curr)) {
                    if (curr.key == key) {
                        return false;
                    } else {
                        Node node = new Node(item);
                        node.next = curr;
                        pred.next = node;
                        return true;
                    }
                } 
            } finally {
                pred.unlock(); curr.unlock();
            }
        }
    }

    public static boolean remove(int item) {
        int key = item;
        while (true) {
            Node pred = head;
            Node curr = pred.next;
            while (curr.key < key) {
                pred = curr; curr = curr.next;
            }
            pred.lock(); curr.lock();
            try {
                if (validate(pred, curr)) {
                    pred.next = curr.next;
                    return true;
                } else {
                    return false;
                }
            } finally {
                pred.unlock(); curr.unlock();
            }
        }
    }

    public static boolean contains(int item) {
        int key = item;
        while (true) {
            Node pred = head; // sentinel node;
            Node curr = pred.next;
            while (curr.key < key) {
                pred = curr; curr = curr.next;
            }
            pred.lock(); curr.lock();
            try {
                if (validate(pred, curr)) {
                    return (curr.key == key);
                }
            } finally {
                pred.unlock(); curr.unlock();
            }
        }
    }

    private static boolean validate(Node pred, Node curr) {
        Node node = head;
        while (node.key <= pred.key) {
            if (node == pred)
                return pred.next == curr;
            node = node.next;
        }
        return false;
    }

    public static void main(String[] args) {
        // Spawn n threads
        Thread servant[] = new Thread[numThreads];

        for (int i = 0; i < numGifts; i++) {
            giftBag.add(i);
        }

        System.out.println("Letter writing beginning.");
        Collections.shuffle(MinotaurBirthday.giftBag);

        // Set head and tail
        head = new Node(Integer.MIN_VALUE);
        head.next = new Node(Integer.MAX_VALUE);

        long startTime = System.nanoTime();
        try {
            MinotaurBirthday test = new MinotaurBirthday();
            for (int i = 0; i < numThreads; i++) {
                servant[i] = new Thread(test);

                servant[i].start();

                servantIDs[i] = servant[i].getId();
            }

            for (int i = 0; i < numThreads; i++) {
                servant[i].join();
            }

            printList();

            // System.out.println(thankYouCards);
            // System.out.println(thankYouCards.size());

        } catch (Exception e) {
            e.printStackTrace();
        }

        System.out.println("All letters have been written.");

        long endTime = System.nanoTime();
        long timeElapsed = endTime - startTime;
        System.out.println("Execution time in milliseconds: " + timeElapsed / 1000000);
    }
}