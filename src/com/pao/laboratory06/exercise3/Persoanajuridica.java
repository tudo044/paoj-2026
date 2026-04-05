package com.pao.laboratory06.exercise3;

import java.util.ArrayList;
import java.util.List;

public class PersoanaJuridica extends Persoana implements PlataOnlineSMS {
    private double sold;
    private List<String> smsTrimise;

    public PersoanaJuridica(String nume, String prenume, String telefon, double sold) {
        super(nume, prenume, telefon);
        this.sold = sold;
        this.smsTrimise = new ArrayList<>();
    }

    @Override
    public void autentificare(String user, String parola) {
        if (user == null || user.isBlank() || parola == null || parola.isBlank()) {
            throw new IllegalArgumentException("User sau parola nu pot fi null/goale.");
        }
        System.out.println("Persoana juridica autentificata: " + user);
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

    @Override
    public boolean trimiteSMS(String mesaj) {
        if (mesaj == null || mesaj.isBlank()) {
            System.out.println("Mesaj invalid — SMS netrimis.");
            return false;
        }
        if (telefon == null || telefon.isBlank()) {
            System.out.println("Număr de telefon lipsă — SMS netrimis.");
            return false;
        }
        smsTrimise.add(mesaj);
        System.out.println("SMS trimis către " + telefon + ": " + mesaj);
        return true;
    }

    public List<String> getSmsTrimise() {
        return smsTrimise;
    }

    @Override
    public String toString() {
        return String.format("PersoanaJuridica{%s %s, telefon=%s, sold=%.2f}", nume, prenume, telefon, sold);
    }
}