# Task-Tracker
CRUD operation through CLI

Name: Task Tracker (CLI-based)

Purpose:
A command-line task manager that allows you to:

Add tasks with a description.

Update tasks (change description).

Delete tasks.

Mark tasks as todo, in-progress, or done.

List tasks with optional filters (done, notdone, in-progress).

Features (Industry Standard):

Layered Architecture:

Model → Task.java (data structure)

Repository → TaskRepository.java (handles JSON storage)

Service → TaskService.java (business logic)

CLI Runner → TaskManagerApplication.java (handles user input)

JSON Storage:

All tasks are stored in ./data/tasks.json (auto-created).

Timestamps (createdAt and updatedAt) are in ISO-8601 format.

Logging:

Uses SLF4J for standardized logging.

Exception Handling:

Custom exceptions (e.g., TaskNotFoundException) for graceful error handling.

Scalable and Testable:

Service layer is separate from storage and CLI → can be unit-tested easily.

How to Run the App (Windows / Gradle)
1. Open Terminal in Project Root

Make sure your Spring Boot project contains:

build.gradle

gradlew.bat (Gradle wrapper)

src/ folder

2. Run Commands
Add a Task
.\gradlew.bat bootRun --args="add Buy groceries"

Update a Task
.\gradlew.bat bootRun --args="update 1 Buy groceries and cook dinner"

Mark a Task
.\gradlew.bat bootRun --args="mark 1 in-progress"

Delete a Task
.\gradlew.bat bootRun --args="delete 1"

List Tasks

All tasks:

.\gradlew.bat bootRun --args="list"


Done tasks:

.\gradlew.bat bootRun --args="list done"


In-progress tasks:

.\gradlew.bat bootRun --args="list progress"


Not done (todo) tasks:

.\gradlew.bat bootRun --args="list notdone"
