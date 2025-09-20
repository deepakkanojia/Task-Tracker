package com.TaskTracker.Tasktracker;

import com.TaskTracker.Tasktracker.exception.TaskNotFoundException;
import com.TaskTracker.Tasktracker.services.TaskService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
@SpringBootApplication
public class TasktrackerApplication implements CommandLineRunner{
	private final TaskService taskService = new TaskService();
	public static void main(String[] args) {
		SpringApplication.run(TasktrackerApplication.class, args);
	}
	@Override
	public void run(String... args) {
		if (args.length < 1) {
			System.out.println("Usage: add|update|delete|mark|list ...");
			return;
		}

		String action = args[0];
		try {
			switch (action) {
				case "add":
					taskService.addTask(String.join(" ", slice(args, 1)));
					break;
				case "update":
					taskService.updateTask(Integer.parseInt(args[1]), String.join(" ", slice(args, 2)));
					break;
				case "delete":
					taskService.deleteTask(Integer.parseInt(args[1]));
					break;
				case "mark":
					taskService.markTask(Integer.parseInt(args[1]), args[2]);
					break;
				case "list":
					String filter = args.length > 1 ? mapFilter(args[1]) : null;
					taskService.listTasks(filter);
					break;
				default:
					System.out.println("Unknown action: " + action);
			}
		} catch (TaskNotFoundException | IllegalArgumentException e) {
			System.out.println("Error: " + e.getMessage());
		} catch (Exception e) {
			System.out.println("Unexpected error: " + e.getMessage());
		}
	}

	private String[] slice(String[] arr, int start) {
		String[] result = new String[arr.length - start];
		System.arraycopy(arr, start, result, 0, result.length);
		return result;
	}

	private String mapFilter(String arg) {
		switch (arg.toLowerCase()) {
			case "done": return "done";
			case "notdone": return "todo";
			case "progress": return "in-progress";
			default: return null;
		}
	}

}
