package com.pao.laboratory04.bonus;

public class InvalidTransitionException extends RuntimeException {
    private final Status fromStatus;
    private final Status toStatus;

    public InvalidTransitionException(Status from, Status to) {
        super("Tranziție invalidă de la " + from + " la " + to);
        this.fromStatus = from;
        this.toStatus = to;
    }

    public Status getFromStatus() { return fromStatus; }
    public Status getToStatus() { return toStatus; }
}