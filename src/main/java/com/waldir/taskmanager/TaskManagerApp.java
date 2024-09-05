package com.waldir.taskmanager;

import java.time.LocalDate;

public class TaskManagerApp {
    public static void main(String[] args) {
        TaskManager taskManager = new TaskManager();

        // Exemplo de adição de tarefas
        taskManager.addTask(new Task(1, "Tarefa 1", LocalDate.now(), "Trabalho", false));
        taskManager.addTask(new Task(2, "Tarefa 2", LocalDate.now().plusDays(1), "Pessoal", false));

        // Exemplo de listagem de tarefas
        taskManager.getAllTasks().forEach(task -> {
            System.out.println("ID: " + task.getId() + ", Descrição: " + task.getDescription() + ", Data: " + task.getDueDate() + ", Categoria: " + task.getCategory() + ", Completada: " + task.isCompleted());
        });
    }
}
