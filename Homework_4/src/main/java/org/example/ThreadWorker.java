package org.example;

import java.util.Deque;
import java.util.LinkedList;

public class ThreadWorker extends Thread {
    private final Deque<Runnable> tasks;
    private boolean isStopped;

    public ThreadWorker(Deque<Runnable> tasks) {
        this.tasks = tasks;
    }

    @Override
    public void run() {
        Runnable task;
        while (true) {
            synchronized (tasks) {
                while (tasks.isEmpty()) {
                    if (isStopped) {
                        return;
                    }
                    try {
                        tasks.wait();
                    } catch (InterruptedException e) {
                        throw new RuntimeException(e);
                    }
                }
                task = tasks.pollFirst();
            }
            try {
                task.run();
            } catch (RuntimeException e) {
                e.printStackTrace();
            }
        }
    }

    public void setStopped() {
        isStopped = true;
    }
}
