package com.pao.laboratory04.enums;

public enum Priority {
    LOW(1, "green"),
    MEDIUM(2, "yellow"),
    HIGH(3, "orange"),
    CRITICAL(4, "red");

    private final int level;
    private final String color;

    Priority(int level, String color) {
        this.level = level;
        this.color = color;
    }

    public int getLevel() { return level; }
    public String getColor() { return color; }

    @Override
    public String toString() {
        return name() + " (level=" + level + ", color=" + color + ")";
    }
}