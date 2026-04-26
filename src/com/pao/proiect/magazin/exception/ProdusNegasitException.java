package com.pao.proiect.magazin.exception;

public class ProdusNegasitException extends Exception {
    public ProdusNegasitException(String codSauNume) {
        super("Produsul nu a fost gasit: " + codSauNume);
    }
}
