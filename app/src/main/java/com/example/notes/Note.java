package com.example.notes;

import java.util.UUID;

public class Note {
    private String shortcut, desc;
    private UUID id;

    public Note(String shortcut, String desc) {
        this.id = UUID.randomUUID();
        this.shortcut = shortcut;
        this.desc = desc;
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

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }
}
