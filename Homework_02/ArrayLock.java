// Kenneth Tran
// COP 4520
// Spring 2022
// ke035936

import java.util.concurrent.*;
import java.util.concurrent.locks.*;
import java.util.concurrent.atomic.AtomicInteger;

public class ArrayLock implements Lock {
    // Credit wolfram77 for @Override from GitHub & Java Docs
    // https://docs.oracle.com/javase/7/docs/api/java/util/concurrent/locks/Lock.html

    @Override
    public void lockInterruptibly() throws InterruptedException {
        lock();
    }

    @Override
    public boolean tryLock() {
        lock();
        return true;
    }

    @Override
    public boolean tryLock(long time, TimeUnit unit) throws InterruptedException {
        lock();
        return true;
    }

    @Override
    public Condition newCondition() {
        return null;
    }

    // Credit of functionality from The Art of Multiprocessor Programming book
    ThreadLocal<Integer> mySlotIndex = new ThreadLocal<Integer>() {
        protected Integer initialValue() {
            return 0;
        }
    };
    AtomicInteger tail;
    boolean notYetPrinted = true;
    volatile boolean[] flag;
    int size;
    int count = 0;

    public ArrayLock(int capacity) {
        size = (4 * capacity);
        tail = new AtomicInteger(0);
        flag = new boolean[4 * capacity];
        flag[0] = true;
    }

    public void lock() {
        int slot = (4 * tail.getAndIncrement()) % size;
        mySlotIndex.set(slot);
        while (!flag[slot]) {
            // if (notYetPrinted) {
            // System.out.println("Spinning" + Thread.currentThread().getId());
            // notYetPrinted = false;
            // }
        }
        ;

        // System.out.println("Locking");
    }

    public void unlock() {
        // System.out.println("Unlocking");
        int slot = mySlotIndex.get();
        flag[slot] = false;
        flag[(((slot / 4) + 1) * 4) % size] = true;

        // notYetPrinted = true;
    }
}