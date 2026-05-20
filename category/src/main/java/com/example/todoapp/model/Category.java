package com.example.todoapp.model;

public enum Category {
    WORK("Work", "#FF6B6B"),
    STUDY("Study", "#4ECDC4"),
    PERSONAL("Personal", "#FFE66D");
    
    private final String label;
    private final String color;
    
    Category(String label, String color) {
        this.label = label;
        this.color = color;
    }
    
    public String getLabel() {
        return label;
    }
    
    public String getColor() {
        return color;
    }
}
