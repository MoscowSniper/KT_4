import java.io.*;
import java.util.*;

class Task implements Serializable, Comparable<Task> {
    private String title;
    private String description;
    private boolean isCompleted;

    public Task(String title, String description) {
        this.title = title;
        this.description = description;
        this.isCompleted = false;
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    public boolean isCompleted() {
        return isCompleted;
    }

    public void setCompleted(boolean isCompleted) {
        this.isCompleted = isCompleted;
    }

    @Override
    public int compareTo(Task other) {
        return this.title.compareTo(other.title);
    }

    @Override
    public String toString() {
        return "Task{" +
                "title='" + title + '\'' +
                ", description='" + description + '\'' +
                ", isCompleted=" + isCompleted +
                '}';
    }
}

class PriorityTask extends Task {
    private String priority;

    public PriorityTask(String title, String description, String priority) {
        super(title, description);
        this.priority = priority;
    }

    public String getPriority() {
        return priority;
    }

    public void setPriority(String priority) {
        this.priority = priority;
    }

    @Override
    public String toString() {
        return super.toString() + ", priority='" + priority + '\'';
    }
}


class TaskManager {
    private List<Task> tasks;

    public TaskManager() {
        tasks = new ArrayList<>();
    }

    public void addTask(Task task) {
        tasks.add(task);
    }

    public void removeTask(String title) {
        tasks.removeIf(task -> task.getTitle().equals(title));
    }

    public void markTaskAsCompleted(String title) {
        for (Task task : tasks) {
            if (task.getTitle().equals(title)) {
                task.setCompleted(true);
                break;
            }
        }
    }

    public List<Task> getTasks() {
        return tasks;
    }

    public void saveToFile(String filename) {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(filename))) {
            oos.writeObject(tasks);
        } catch (IOException e) {
            System.err.println("ОШИБКА: " + e.getMessage());
        }
    }

    @SuppressWarnings("unchecked")
    public void loadFromFile(String filename) {
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(filename))) {
            tasks = (List<Task>) ois.readObject();
        } catch (IOException | ClassNotFoundException e) {
            System.err.println("ОШИБКА: " + e.getMessage());
        }
    }
}

final class ImmutableTask {
    private final String title;
    private final String description;
    private final boolean isCompleted;

    public ImmutableTask(String title, String description, boolean isCompleted) {
        this.title = title;
        this.description = description;
        this.isCompleted = isCompleted;
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    public boolean isCompleted() {
        return isCompleted;
    }

    @Override
    public String toString() {
        return "ImmutableTask{" +
                "title='" + title + '\'' +
                ", description='" + description + '\'' +
                ", isCompleted=" + isCompleted +
                '}';
    }
}

public class TaskManagerApp {
    public static void main(String[] args) {
        TaskManager taskManager = new TaskManager();

        taskManager.addTask(new Task("Первое правило бойцовского клуба", "не упоминать о Бойцовском клубе"));
        taskManager.addTask(new PriorityTask("Второе правило Бойцовского клуба", "не упоминать нигде о Бойцовском клубе", "High"));
        taskManager.markTaskAsCompleted("Первое правило бойцовского клуба");
        taskManager.saveToFile("tasks.dat");
        taskManager.loadFromFile("tasks.dat");

        for (Task task : taskManager.getTasks()) {
            System.out.println(task);
        }
    }
}
