package com.pao.proiect.magazin.model;

public class Distribuitor {
    private int id;
    private String nume;
    private String email;
    private String telefon;
    private String adresa;
    private String cui;

    public Distribuitor(int id, String nume, String email, String telefon, String adresa, String cui) {
        this.id = id;
        this.nume = nume;
        this.email = email;
        this.telefon = telefon;
        this.adresa = adresa;
        this.cui = cui;
    }

    public int getId() { return id; }
    public String getNume() { return nume; }
    public String getEmail() { return email; }
    public String getTelefon() { return telefon; }
    public String getAdresa() { return adresa; }
    public String getCui() { return cui; }

    public void setNume(String nume) { this.nume = nume; }
    public void setEmail(String email) { this.email = email; }
    public void setTelefon(String telefon) { this.telefon = telefon; }
    public void setAdresa(String adresa) { this.adresa = adresa; }

    @Override
    public String toString() {
        return String.format("Distribuitor{id=%d, nume='%s', cui='%s', email='%s'}", id, nume, cui, email);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Distribuitor)) return false;
        Distribuitor d = (Distribuitor) o;
        return cui.equals(d.cui);
    }

    @Override
    public int hashCode() { return cui.hashCode(); }
}
