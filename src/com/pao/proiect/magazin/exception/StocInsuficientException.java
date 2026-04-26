package com.pao.proiect.magazin.exception;

public class StocInsuficientException extends Exception {
    public StocInsuficientException(String numeProdus, int disponibil, int cerut) {
        super(String.format("Stoc insuficient pentru '%s': disponibil %d, cerut %d", numeProdus, disponibil, cerut));
    }
}
