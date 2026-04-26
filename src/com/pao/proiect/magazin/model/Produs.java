package com.pao.proiect.magazin.model;

public class Produs implements Stocabil, Comparable<Produs> {
    private CodProdus codProdus;
    private String nume;
    private String descriere;
    private double pret;
    private int cantitate;
    private int stocMinim;
    private Categorie categorie;
    private Distribuitor distribuitor;

    public Produs(CodProdus codProdus, String nume, String descriere, double pret, int cantitate, int stocMinim, Categorie categorie) {
        this.codProdus = codProdus;
        this.nume = nume;
        this.descriere = descriere;
        this.pret = pret;
        this.cantitate = cantitate;
        this.stocMinim = stocMinim;
        this.categorie = categorie;
    }

    public CodProdus getCodProdus() { return codProdus; }
    public String getNume() { return nume; }
    public String getDescriere() { return descriere; }
    public double getPret() { return pret; }
    public Categorie getCategorie() { return categorie; }
    public Distribuitor getDistribuitor() { return distribuitor; }

    public void setNume(String nume) { this.nume = nume; }
    public void setDescriere(String descriere) { this.descriere = descriere; }
    public void setPret(double pret) { this.pret = pret; }
    public void setDistribuitor(Distribuitor distribuitor) { this.distribuitor = distribuitor; }

    @Override
    public int getCantitate() { return cantitate; }

    @Override
    public void setCantitate(int cantitate) {
        if (cantitate < 0) throw new IllegalArgumentException("Cantitatea nu poate fi negativa");
        this.cantitate = cantitate;
    }

    @Override
    public boolean esteDisponibil() { return cantitate > 0; }

    @Override
    public int getStocMinim() { return stocMinim; }

    public double getValoareStoc() { return pret * cantitate; }

    @Override
    public int compareTo(Produs other) {
        return Double.compare(other.getValoareStoc(), this.getValoareStoc());
    }

    @Override
    public String toString() {
        return String.format("Produs{cod=%s, nume='%s', pret=%.2f, stoc=%d, categorie=%s}",
                codProdus, nume, pret, cantitate, categorie.getNume());
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Produs)) return false;
        Produs p = (Produs) o;
        return codProdus.equals(p.codProdus);
    }

    @Override
    public int hashCode() { return codProdus.hashCode(); }
}
