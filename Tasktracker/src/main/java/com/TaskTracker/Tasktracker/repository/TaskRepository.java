package com.TaskTracker.Tasktracker.repository;

import com.TaskTracker.Tasktracker.model.Task;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class TaskRepository {

    private static final Logger logger = LoggerFactory.getLogger(TaskRepository.class);
    private static final String FILE_PATH = "./data/tasks.json";
    private final ObjectMapper mapper = new ObjectMapper();


    public TaskRepository() {
        try {
            File folder = new File("./data");
            if (!folder.exists()) folder.mkdirs();
            File file = new File(FILE_PATH);
            if (!file.exists()) {
                mapper.writeValue(file, new ArrayList<Task>());
            }
        } catch (IOException e) {
            logger.error("Error initializing task storage", e);
        }
    }

    public List<Task> getAllTasks() {
        try {
            return mapper.readValue(new File(FILE_PATH), new TypeReference<List<Task>>() {});
        } catch (IOException e) {
            logger.error("Error reading tasks.json", e);
            return new ArrayList<>();
        }
    }

    public void saveAllTasks(List<Task> tasks) {
        try {
            mapper.writerWithDefaultPrettyPrinter().writeValue(new File(FILE_PATH), tasks);
        } catch (IOException e) {
            logger.error("Error writing tasks.json", e);
        }
    }
}
