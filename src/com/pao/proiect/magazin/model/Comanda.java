package com.pao.proiect.magazin.model;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class Comanda {
    public enum Status { PLASATA, CONFIRMATA, LIVRATA, ANULATA }

    private int id;
    private Distribuitor distribuitor;
    private LocalDate dataComanda;
    private Status status;
    private List<DetaliiComanda> detalii;

    public Comanda(int id, Distribuitor distribuitor) {
        this.id = id;
        this.distribuitor = distribuitor;
        this.dataComanda = LocalDate.now();
        this.status = Status.PLASATA;
        this.detalii = new ArrayList<>();
    }

    public void adaugaProdus(Produs produs, int cantitate) {
        detalii.add(new DetaliiComanda(produs, cantitate));
    }

    public double getTotalComanda() {
        return detalii.stream().mapToDouble(DetaliiComanda::getSubtotal).sum();
    }

    public int getId() { return id; }
    public Distribuitor getDistribuitor() { return distribuitor; }
    public LocalDate getDataComanda() { return dataComanda; }
    public Status getStatus() { return status; }
    public List<DetaliiComanda> getDetalii() { return detalii; }

    public void setStatus(Status status) { this.status = status; }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(String.format("Comanda #%d | Distribuitor: %s | Data: %s | Status: %s | Total: %.2f RON\n",
                id, distribuitor.getNume(), dataComanda, status, getTotalComanda()));
        for (DetaliiComanda d : detalii) sb.append(d.toString()).append("\n");
        return sb.toString();
    }
}
