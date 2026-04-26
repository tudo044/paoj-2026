package com.pao.laboratory06.exercise3;

public class Inginer extends Angajat implements PlataOnline, Comparable<Inginer> {
    private double sold;
    private boolean autentificat;

    public Inginer(String nume, String prenume, String telefon, double salariu, double sold) {
        super(nume, prenume, telefon, salariu);
        this.sold = sold;
        this.autentificat = false;
    }

    @Override
    public void autentificare(String user, String parola) {
        if (user == null || user.isBlank() || parola == null || parola.isBlank()) {
            throw new IllegalArgumentException("User sau parola nu pot fi null/goale.");
        }
        this.autentificat = true;
        System.out.println("Autentificare reușită pentru: " + user);
    }

    @Override
    public double consultareSold() {
        return sold;
    }

    @Override
    public boolean efectuarePlata(double suma) {
        if (suma <= 0) return false;
        if (suma > sold) {
            System.out.println("Fonduri insuficiente.");
            return false;
        }
        sold -= suma;
        System.out.printf("Plată de %.2f lei efectuată. Sold rămas: %.2f lei%n", suma, sold);
        return true;
    }

    // Sortare naturală după nume alfabetic
    @Override
    public int compareTo(Inginer other) {
        return this.nume.compareTo(other.nume);
    }

    @Override
    public String toString() {
        return String.format("Inginer{%s %s, salariu=%.2f, sold=%.2f}", nume, prenume, salariu, sold);
    }
}