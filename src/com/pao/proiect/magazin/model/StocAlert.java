package com.pao.proiect.magazin.model;

import java.time.LocalDateTime;

public class StocAlert {
    private Produs produs;
    private int cantitateActuala;
    private int stocMinim;
    private LocalDateTime momentGenerare;

    public StocAlert(Produs produs) {
        this.produs = produs;
        this.cantitateActuala = produs.getCantitate();
        this.stocMinim = produs.getStocMinim();
        this.momentGenerare = LocalDateTime.now();
    }

    public Produs getProdus() { return produs; }
    public int getCantitateActuala() { return cantitateActuala; }
    public int getStocMinim() { return stocMinim; }
    public LocalDateTime getMomentGenerare() { return momentGenerare; }

    @Override
    public String toString() {
        return String.format("⚠ ALERT STOC: '%s' are %d unitati (minim: %d) — generat la %s",
                produs.getNume(), cantitateActuala, stocMinim, momentGenerare);
    }
}
