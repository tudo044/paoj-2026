package com.pao.proiect.magazin.model;

public class Categorie {
    private int id;
    private String nume;
    private String descriere;
    private String codCategorie;

    public Categorie(int id, String nume, String descriere, String codCategorie) {
        this.id = id;
        this.nume = nume;
        this.descriere = descriere;
        this.codCategorie = codCategorie;
    }

    public int getId() { return id; }
    public String getNume() { return nume; }
    public String getDescriere() { return descriere; }
    public String getCodCategorie() { return codCategorie; }

    public void setNume(String nume) { this.nume = nume; }
    public void setDescriere(String descriere) { this.descriere = descriere; }

    @Override
    public String toString() {
        return "[" + codCategorie + "] " + nume + " - " + descriere;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Categorie)) return false;
        Categorie c = (Categorie) o;
        return id == c.id;
    }

    @Override
    public int hashCode() { return Integer.hashCode(id); }
}
