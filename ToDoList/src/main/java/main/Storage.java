package main;

import responeces.Task;

import java.util.ArrayList;
import java.util.HashMap;

public class Storage {
    private static final HashMap<Integer, Task> tasks = new HashMap<>();

    public static ArrayList getAllTasks() {
        return new ArrayList(tasks.values());
    }

    public static int addTask(Task task) {
        int id = tasks.size() + 1;
        task.setId(id);
        tasks.put(id, task);

        return id;
    }

    public static Task getTaskById(Integer id) {
        return tasks.get(id);
    }
}
