package com.TaskTracker.Tasktracker.services;

import com.TaskTracker.Tasktracker.exception.TaskNotFoundException;
import com.TaskTracker.Tasktracker.model.Task;
import com.TaskTracker.Tasktracker.repository.TaskRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
public class TaskService {
    private static final Logger logger = LoggerFactory.getLogger(TaskService.class);
    private final TaskRepository repository = new TaskRepository();

    public void addTask(String description) {
        List<Task> tasks = repository.getAllTasks();
        int id = tasks.isEmpty() ? 1 : tasks.stream().mapToInt(Task::getId).max().getAsInt() + 1;
        Task task = new Task(id, description);
        tasks.add(task);
        repository.saveAllTasks(tasks);
        logger.info("Task added: {}", description);
    }

    public void updateTask(int id, String description) {
        List<Task> tasks = repository.getAllTasks();
        Optional<Task> taskOptional = tasks.stream().filter(t -> t.getId() == id).findFirst();
        if (taskOptional.isPresent()) {
            Task task = taskOptional.get();
            task.setDescription(description);
            task.setUpdatedAt(LocalDateTime.now());
            repository.saveAllTasks(tasks);
            logger.info("Task {} updated.", id);
        } else throw new TaskNotFoundException("Task " + id + " not found.");
    }

    public void deleteTask(int id) {
        List<Task> tasks = repository.getAllTasks();
        boolean removed = tasks.removeIf(t -> t.getId() == id);
        if (removed) {
            repository.saveAllTasks(tasks);
            logger.info("Task {} deleted.", id);
        } else throw new TaskNotFoundException("Task " + id + " not found.");
    }

    public void markTask(int id, String status) {
        if (!status.equals("todo") && !status.equals("in-progress") && !status.equals("done"))
            throw new IllegalArgumentException("Invalid status. Use todo, in-progress, or done.");

        List<Task> tasks = repository.getAllTasks();
        Optional<Task> taskOptional = tasks.stream().filter(t -> t.getId() == id).findFirst();
        if (taskOptional.isPresent()) {
            Task task = taskOptional.get();
            task.setStatus(status);
            task.setUpdatedAt(LocalDateTime.now());
            repository.saveAllTasks(tasks);
            logger.info("Task {} marked as {}", id, status);
        } else throw new TaskNotFoundException("Task " + id + " not found.");
    }

    public void listTasks(String filter) {
        List<Task> tasks = repository.getAllTasks();
        tasks.stream()
                .filter(t -> filter == null || t.getStatus().equals(filter))
                .forEach(t -> System.out.println(
                        t.getId() + ". " + t.getDescription() +
                                " [" + t.getStatus() + "] " +
                                "(Created: " + t.getCreatedAt() + ", Updated: " + t.getUpdatedAt() + ")"
                ));
    }
}

