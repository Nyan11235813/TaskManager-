# Personal Task Manager

A simple command-line task management application written in Java that helps you organize and track your tasks efficiently.

## Features

- **Add new tasks** with title, description, and priority level (High/Medium/Low)
- **View all tasks** with completion status and creation timestamp
- **Mark tasks as complete** with a simple interface
- **Delete tasks** you no longer need
- **Search tasks** by title or description
- **Persistent storage** - tasks are automatically saved to and loaded from a file
- **Task summary** showing completed vs total tasks

## How to Use

1. Clone this repository
2. Compile the Java file:
   ```
   javac TaskManager.java
   ```
3. Run the application:
   ```
   java TaskManager
   ```
4. Follow the on-screen menu to manage your tasks

## Menu Options

1. **Add New Task** - Create a new task with details
2. **View All Tasks** - See all your tasks with their status
3. **Mark Task as Complete** - Update task completion status
4. **Delete Task** - Remove tasks you don't need
5. **Search Tasks** - Find tasks by keywords
6. **Save and Exit** - Save all changes and quit the application

## Data Storage

Tasks are automatically saved to `tasks.txt` in the same directory when you exit the program. The file is loaded automatically when the program starts.

## Requirements

- Java 8 or higher

## Future Enhancements

- Add due dates for tasks
- Implement task categories or tags
- Add sorting/filtering options
- Export tasks to different formats
- Graphical user interface version

## License

This project is open-source and available under the MIT License.
