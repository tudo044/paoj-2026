package com.pao.proiect.magazin.exception;

public class DistribuitorNegasitException extends Exception {
    public DistribuitorNegasitException(String nume) {
        super("Distribuitorul nu a fost gasit: " + nume);
    }
}
