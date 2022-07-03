package com.example.notes;

import android.os.Build;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.UUID;

public class Task {
    private String id;
    private String taskAdded;
    private String taskDue;
    private String shortcut;
    private String desc;

    public Task(String taskDue, String shortcut, String desc) {
        this.id = UUID.randomUUID().toString();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            this.taskAdded = LocalDate.now().toString();
        }
        this.taskDue = taskDue;
        this.shortcut = shortcut;
        this.desc = desc;
    }
    public Task() {
        this.id = null;
        this.taskAdded = null;
        this.taskDue = null;
        this.shortcut = null;
        this.desc = null;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTaskAdded() {
        return taskAdded;
    }

    public void setTaskAdded(String taskAdded) {
        this.taskAdded = taskAdded;
    }

    public String getTaskDue() {
        return taskDue;
    }

    public void setTaskDue(String taskDue) {
        this.taskDue = taskDue;
    }

    public String getShortcut() {
        return shortcut;
    }

    public void setShortcut(String shortcut) {
        this.shortcut = shortcut;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }


}
