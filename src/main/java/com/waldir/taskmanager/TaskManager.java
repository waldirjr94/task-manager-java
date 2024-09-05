package com.waldir.taskmanager;

import java.util.ArrayList;
import java.time.LocalDate;
import java.util.List;

public class TaskManager {
    private List<Task> tasks;

    public TaskManager() {
        this.tasks = new ArrayList<>();
    }

    public void addTask(Task task) {
        tasks.add(task);
    }

    public boolean removeTask(int id) {
        return tasks.removeIf(task -> task.getId() == id);
    }

    public List<Task> getAllTasks() {
        return tasks;
    }

    public List<Task> getCompletedTasks() {
        List<Task> completedTasks = new ArrayList<>();
        for (Task task : tasks) {
            if (task.isCompleted()) {
                completedTasks.add(task);
            }
        }
        return completedTasks;
    }

    public List<Task> getNotCompletedTasks() {
        List<Task> notCompletedTasks = new ArrayList<>();
        for (Task task : tasks) {
            if (!task.isCompleted()) {
                notCompletedTasks.add(task);
            }
        }
        return notCompletedTasks;
    }

 // TaskManager.java
    public boolean markTaskAsCompleted(int id, boolean completed) {
        Task task = findTaskById(id);
        if (task != null) {
            // Verifica se a data da tarefa é anterior ao dia atual
            if (task.getDueDate().isBefore(LocalDate.now())) {
                return false;  // Não permite marcar como completada se a data for passada
            }
            task.setCompleted(completed);  // Atualiza o status de completada
            return true;
        }
        return false;
    }



    private Task findTaskById(int id) {
        return tasks.stream().filter(task -> task.getId() == id).findFirst().orElse(null);
    }
}
