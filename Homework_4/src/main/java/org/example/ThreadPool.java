package org.example;

import java.util.Deque;
import java.util.LinkedList;

public class ThreadPool {
    private final Deque<Runnable> tasks = new LinkedList<>();
    private final ThreadWorker[] threads;
    private boolean isWorked;

    public ThreadPool(int threadCount) {
        threads = new ThreadWorker[threadCount];
        for (int i = 0; i < threadCount; i++) {
            threads[i] = new ThreadWorker(tasks);
            threads[i].start();
        }
        isWorked = true;
    }

    public boolean execute(Runnable task) {
        if (!isWorked) {
            return false;
        }
        synchronized (tasks) {
            tasks.add(task);
            tasks.notify();
        }
        return true;
    }

    public void stop() {
        isWorked = false;
        for (ThreadWorker thread : threads) {
            try {
                thread.setStopped();
            } catch (RuntimeException e) {
                throw new RuntimeException(e);
            }
        }
    }
}
