package com.pao.proiect.magazin.model;

public class DetaliiComanda {
    private Produs produs;
    private int cantitateComandată;
    private double pretUnitar;

    public DetaliiComanda(Produs produs, int cantitateComandată) {
        this.produs = produs;
        this.cantitateComandată = cantitateComandată;
        this.pretUnitar = produs.getPret();
    }

    public Produs getProdus() { return produs; }
    public int getCantitateComandată() { return cantitateComandată; }
    public double getPretUnitar() { return pretUnitar; }
    public double getSubtotal() { return pretUnitar * cantitateComandată; }

    @Override
    public String toString() {
        return String.format("  - %s x%d @ %.2f RON = %.2f RON",
                produs.getNume(), cantitateComandată, pretUnitar, getSubtotal());
    }
}
