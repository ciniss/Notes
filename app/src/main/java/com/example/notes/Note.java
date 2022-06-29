package com.example.notes;

import java.util.UUID;

public class Note {
    private String shortcut, desc;
    private String id;

    public Note(String shortcut, String desc) {
        this.id = UUID.randomUUID().toString();
        this.shortcut = shortcut;
        this.desc = desc;
    }
    public Note(){
        shortcut=null;
        desc=null;
        id=null;
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

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
