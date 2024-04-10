package org.example;

import org.example.model.Status;
import org.example.model.Task;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Random;
import java.util.logging.Logger;

public class Main {
    public static void main(String[] args) {
        List<Task> initialList = createList(10);

        List<Task> statusList = getByStatus(initialList, Status.WorkProgress);
        Logger.getLogger(Main.class.getName()).info("Tasks by status:");
        printList(statusList);

        int id = 4;
        boolean hasId = hasById(initialList, id);
        Logger.getLogger(Main.class.getName()).info(String.format("Has task with id %d %s", id, hasId));

        List<Task> sortedList = sortedByStatus(initialList);
        Logger.getLogger(Main.class.getName()).info("Sorted by status:");
        printList(sortedList);

        Status status = Status.WorkProgress;
        int count = countByStatus(initialList, status);
        Logger.getLogger(Main.class.getName()).info(String.format("Count %d by status %s", count, status));
    }

    private static List<Task> getByStatus(List<Task> taskList, Status status){
        List<Task> resultTask = taskList.stream().
                filter(task -> task.getStatus() == status).
                toList();
        return resultTask;
    }

    private static boolean hasById(List<Task> taskList, int id){
        var result = taskList.stream().anyMatch(task -> task.getId() == id);
        return result;
    }

    private static List<Task> sortedByStatus(List<Task> taskList){
        List<Task> resultTask = taskList.stream().
                sorted(Comparator.comparing(Task::getStatus)).
                toList();
        return resultTask;
    }

    private static int countByStatus(List<Task> taskList, Status status){
        long taskCount = taskList.stream().filter(task -> task.getStatus() == status).count();
        return (int)taskCount;
    }

    private static List<Task> createList(int count){
        List<Task> taskList = new ArrayList<>();
        Random random = new Random();
        Status[] statuses = Status.values();
        int id = 0;
        for (int i = 0; i < count; i++) {
            int randomIndex = random.nextInt(statuses.length);
            Status status = statuses[randomIndex];
            Task task = new Task(id++, "Task-"+id, status);
            taskList.add(task);
        }
        return taskList;
    }

    private static void printList(List<Task> list){
        list.forEach(task -> Logger.getLogger(Main.class.getName()).info(task.toString()));
    }
}