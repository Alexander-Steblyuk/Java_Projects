package main;

import responeces.Task;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class Storage {
    private static int currId = 1;
    private static final HashMap<Integer, Task> tasks = new HashMap<>();

    private Storage(){

    }

    public static List<Task> getAllTasks() {
        return new ArrayList(tasks.values());
    }

    public static int addTask(Task task) {
        int id = currId++;
        task.setId(id);
        tasks.put(id, task);

        return id;
    }

    public static Task getTaskById(int id) {
        if (tasks.containsKey(id)) {
            return tasks.get(id);
        }

        return null;
    }

    public static Task deleteTaskById(int id) {
        if (tasks.containsKey(id)) {
            return tasks.remove(id);
        }

        return null;
    }
}
