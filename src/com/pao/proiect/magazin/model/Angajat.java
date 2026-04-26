package com.pao.proiect.magazin.model;

public class Angajat extends Persoana {
    private int id;
    private String departament;
    private double salariu;

    public Angajat(int id, String nume, String email, String telefon, String departament, double salariu) {
        super(nume, email, telefon);
        this.id = id;
        this.departament = departament;
        this.salariu = salariu;
    }

    @Override
    public String getRol() { return "Angajat"; }

    public int getId() { return id; }
    public String getDepartament() { return departament; }
    public double getSalariu() { return salariu; }
    public void setSalariu(double salariu) { this.salariu = salariu; }
    public void setDepartament(String departament) { this.departament = departament; }
}
