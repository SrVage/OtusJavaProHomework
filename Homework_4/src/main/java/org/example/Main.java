package org.example;

import java.util.logging.Logger;

public class Main{
    public static void main(String[] args){
        ThreadPool pool = new ThreadPool(4);
        createTasks(0, 50, pool);

        threadSleep(1000);
        pool.stop();

        createTasks(50, 100, pool);
    }

    private static void createTasks(int start, int finish, ThreadPool pool) {
        for (int i = start; i < finish; i++) {
            int num = i;
            var success = pool.execute(() -> {
                threadSleep(500);
                Logger.getLogger(Main.class.getSimpleName()).info(Thread.currentThread().getName() + " task: " + num);
            });
            Logger.getLogger(Main.class.getSimpleName()).info("Success added task: " + num +"\t"+ success);
        }
    }

    private static void threadSleep(int time){
        try {
            Thread.sleep(time);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }
}