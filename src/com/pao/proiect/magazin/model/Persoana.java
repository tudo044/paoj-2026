package com.pao.proiect.magazin.model;

public abstract class Persoana {
    private String nume;
    private String email;
    private String telefon;

    public Persoana(String nume, String email, String telefon) {
        this.nume = nume;
        this.email = email;
        this.telefon = telefon;
    }

    public abstract String getRol();

    public String getNume() { return nume; }
    public String getEmail() { return email; }
    public String getTelefon() { return telefon; }

    public void setNume(String nume) { this.nume = nume; }
    public void setEmail(String email) { this.email = email; }
    public void setTelefon(String telefon) { this.telefon = telefon; }

    @Override
    public String toString() {
        return getRol() + ": " + nume + " | " + email + " | " + telefon;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Persoana)) return false;
        Persoana p = (Persoana) o;
        return email.equals(p.email);
    }

    @Override
    public int hashCode() {
        return email.hashCode();
    }
}
