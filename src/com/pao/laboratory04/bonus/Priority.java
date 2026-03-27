package com.pao.laboratory04.bonus;

public enum Priority {
    LOW(1), MEDIUM(2), HIGH(3), URGENT(5);

    private final int multiplier;

    Priority(int multiplier) {
        this.multiplier = multiplier;
    }

    public int calculateScore(int baseDays) {
        return baseDays * multiplier;
    }
}