package main;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import responeces.Task;

import java.util.List;

@RestController
public class TaskController {
    @RequestMapping(value = "/tasks/", method = RequestMethod.GET)
    public List<Task> list() {
        return Storage.getAllTasks();
    }

    @RequestMapping(value = "/tasks/", method = RequestMethod.POST)
    public int add(Task task) {
        return Storage.addTask(task);
    }
}
