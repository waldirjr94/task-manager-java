package com.waldir.taskmanager;

import java.time.LocalDate;

public class Task {
    private int id;
    private String description;
    private LocalDate dueDate;
    private String category;
    private boolean completed;

    public Task(int id, String description, LocalDate dueDate, String category, boolean completed) {
        this.id = id;
        this.description = description;
        this.dueDate = dueDate;
        this.category = category;
        this.completed = completed;
    }

    public int getId() {
        return id;
    }

    public String getDescription() {
        return description;
    }

    public LocalDate getDueDate() {
        return dueDate;
    }

    public String getCategory() {
        return category;
    }

    public boolean isCompleted() {
        return completed;
    }

    public void setCompleted(boolean completed) {
        this.completed = completed;
    }
}
