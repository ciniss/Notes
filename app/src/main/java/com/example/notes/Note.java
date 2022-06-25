package com.example.notes;

public class Note {
    private String shortcut, desc;

    public Note(String shortcut, String desc) {
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
}
