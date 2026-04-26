package com.pao.proiect.magazin.model;

import java.util.ArrayList;
import java.util.List;

public class Manager extends Angajat {
    private List<Angajat> echipa;
    private String nivelAutorizare;

    public Manager(int id, String nume, String email, String telefon, String departament, double salariu, String nivelAutorizare) {
        super(id, nume, email, telefon, departament, salariu);
        this.nivelAutorizare = nivelAutorizare;
        this.echipa = new ArrayList<>();
    }

    @Override
    public String getRol() { return "Manager"; }

    public void adaugaInEchipa(Angajat angajat) {
        echipa.add(angajat);
    }

    public List<Angajat> getEchipa() { return echipa; }
    public String getNivelAutorizare() { return nivelAutorizare; }

    @Override
    public String toString() {
        return super.toString() + " | Autorizare: " + nivelAutorizare + " | Echipa: " + echipa.size() + " angajati";
    }
}
