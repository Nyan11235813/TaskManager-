import java.util.*;
import java.io.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

// Task class to represent individual tasks
class Task {
    private String title;
    private String description;
    private boolean isCompleted;
    private LocalDateTime createdAt;
    private String priority;
    
    public Task(String title, String description, String priority) {
        this.title = title;
        this.description = description;
        this.priority = priority;
        this.isCompleted = false;
        this.createdAt = LocalDateTime.now();
    }
    
    // Getters and Setters
    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }
    
    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }
    
    public boolean isCompleted() { return isCompleted; }
    public void setCompleted(boolean completed) { this.isCompleted = completed; }
    
    public LocalDateTime getCreatedAt() { return createdAt; }
    
    public String getPriority() { return priority; }
    public void setPriority(String priority) { this.priority = priority; }
    
    @Override
    public String toString() {
        String status = isCompleted ? "✓" : "○";
        String formattedDate = createdAt.format(DateTimeFormatter.ofPattern("MM/dd/yyyy HH:mm"));
        return String.format("[%s] %s - %s (Priority: %s) - Created: %s", 
                           status, title, description, priority, formattedDate);
    }
}

// Main TaskManager class
public class TaskManager {
    private ArrayList<Task> tasks;
    private Scanner scanner;
    private final String FILENAME = "tasks.txt";
    
    public TaskManager() {
        tasks = new ArrayList<>();
        scanner = new Scanner(System.in);
        loadTasksFromFile();
    }
    
    public void run() {
        System.out.println("=== Welcome to Personal Task Manager ===");
        
        while (true) {
            displayMenu();
            int choice = getChoice();
            
            switch (choice) {
                case 1:
                    addTask();
                    break;
                case 2:
                    viewTasks();
                    break;
                case 3:
                    markTaskComplete();
                    break;
                case 4:
                    deleteTask();
                    break;
                case 5:
                    searchTasks();
                    break;
                case 6:
                    saveTasksToFile();
                    System.out.println("Tasks saved! Goodbye!");
                    return;
                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        }
    }
    
    private void displayMenu() {
        System.out.println("\n--- Task Manager Menu ---");
        System.out.println("1. Add New Task");
        System.out.println("2. View All Tasks");
        System.out.println("3. Mark Task as Complete");
        System.out.println("4. Delete Task");
        System.out.println("5. Search Tasks");
        System.out.println("6. Save and Exit");
        System.out.print("Choose an option (1-6): ");
    }
    
    private int getChoice() {
        try {
            return Integer.parseInt(scanner.nextLine());
        } catch (NumberFormatException e) {
            return -1;
        }
    }
    
    private void addTask() {
        System.out.println("\n--- Add New Task ---");
        System.out.print("Enter task title: ");
        String title = scanner.nextLine();
        
        System.out.print("Enter task description: ");
        String description = scanner.nextLine();
        
        System.out.print("Enter priority (High/Medium/Low): ");
        String priority = scanner.nextLine();
        
        if (title.trim().isEmpty()) {
            System.out.println("Task title cannot be empty!");
            return;
        }
        
        Task newTask = new Task(title, description, priority);
        tasks.add(newTask);
        System.out.println("Task added successfully!");
    }
    
    private void viewTasks() {
        System.out.println("\n--- Your Tasks ---");
        
        if (tasks.isEmpty()) {
            System.out.println("No tasks found. Add some tasks to get started!");
            return;
        }
        
        for (int i = 0; i < tasks.size(); i++) {
            System.out.println((i + 1) + ". " + tasks.get(i));
        }
        
        // Show summary
        long completedTasks = tasks.stream().mapToLong(task -> task.isCompleted() ? 1 : 0).sum();
        System.out.println("\nSummary: " + completedTasks + "/" + tasks.size() + " tasks completed");
    }
    
    private void markTaskComplete() {
        if (tasks.isEmpty()) {
            System.out.println("No tasks available to mark as complete.");
            return;
        }
        
        viewTasks();
        System.out.print("\nEnter task number to mark as complete: ");
        
        try {
            int taskIndex = Integer.parseInt(scanner.nextLine()) - 1;
            
            if (taskIndex >= 0 && taskIndex < tasks.size()) {
                Task task = tasks.get(taskIndex);
                if (task.isCompleted()) {
                    System.out.println("Task is already completed!");
                } else {
                    task.setCompleted(true);
                    System.out.println("Task marked as complete!");
                }
            } else {
                System.out.println("Invalid task number.");
            }
        } catch (NumberFormatException e) {
            System.out.println("Please enter a valid number.");
        }
    }
    
    private void deleteTask() {
        if (tasks.isEmpty()) {
            System.out.println("No tasks available to delete.");
            return;
        }
        
        viewTasks();
        System.out.print("\nEnter task number to delete: ");
        
        try {
            int taskIndex = Integer.parseInt(scanner.nextLine()) - 1;
            
            if (taskIndex >= 0 && taskIndex < tasks.size()) {
                Task removedTask = tasks.remove(taskIndex);
                System.out.println("Deleted task: " + removedTask.getTitle());
            } else {
                System.out.println("Invalid task number.");
            }
        } catch (NumberFormatException e) {
            System.out.println("Please enter a valid number.");
        }
    }
    
    private void searchTasks() {
        if (tasks.isEmpty()) {
            System.out.println("No tasks available to search.");
            return;
        }
        
        System.out.print("Enter search term: ");
        String searchTerm = scanner.nextLine().toLowerCase();
        
        System.out.println("\n--- Search Results ---");
        boolean found = false;
        
        for (int i = 0; i < tasks.size(); i++) {
            Task task = tasks.get(i);
            if (task.getTitle().toLowerCase().contains(searchTerm) ||
                task.getDescription().toLowerCase().contains(searchTerm)) {
                System.out.println((i + 1) + ". " + task);
                found = true;
            }
        }
        
        if (!found) {
            System.out.println("No tasks found containing: " + searchTerm);
        }
    }
    
    private void saveTasksToFile() {
        try (PrintWriter writer = new PrintWriter(new FileWriter(FILENAME))) {
            for (Task task : tasks) {
                writer.println(task.getTitle() + "|" + task.getDescription() + "|" + 
                             task.getPriority() + "|" + task.isCompleted());
            }
            System.out.println("Tasks saved to " + FILENAME);
        } catch (IOException e) {
            System.out.println("Error saving tasks: " + e.getMessage());
        }
    }
    
    private void loadTasksFromFile() {
        try (BufferedReader reader = new BufferedReader(new FileReader(FILENAME))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split("\\|");
                if (parts.length == 4) {
                    Task task = new Task(parts[0], parts[1], parts[2]);
                    task.setCompleted(Boolean.parseBoolean(parts[3]));
                    tasks.add(task);
                }
            }
            System.out.println("Loaded " + tasks.size() + " tasks from file.");
        } catch (FileNotFoundException e) {
            System.out.println("No previous tasks found. Starting fresh!");
        } catch (IOException e) {
            System.out.println("Error loading tasks: " + e.getMessage());
        }
    }
    
    public static void main(String[] args) {
        TaskManager manager = new TaskManager();
        manager.run();
    }
}