package com.example.todoapp.entity;

public enum Category {
    WORK("Work", "text-red-600 bg-red-50 border-red-100"),
    STUDY("Study", "text-teal-600 bg-teal-50 border-teal-100"),
    PERSONAL("Personal", "text-amber-600 bg-amber-50 border-amber-100");
    
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

    public static Category fromString(String text) {
        if (text == null) return null;
        for (Category c : Category.values()) {
            if (c.label.equalsIgnoreCase(text) || c.name().equalsIgnoreCase(text)) {
                return c;
            }
        }
        return null;
    }
}
