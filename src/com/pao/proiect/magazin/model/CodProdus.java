package com.pao.proiect.magazin.model;

public final class CodProdus {
    private final String cod;
    private final String categorieCod;
    private final int an;

    public CodProdus(String categorieCod, int an, int secventa) {
        this.categorieCod = categorieCod;
        this.an = an;
        this.cod = categorieCod + "-" + an + "-" + String.format("%04d", secventa);
    }

    public String getCod() { return cod; }
    public String getCategorieCod() { return categorieCod; }
    public int getAn() { return an; }

    @Override
    public String toString() { return cod; }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof CodProdus)) return false;
        CodProdus c = (CodProdus) o;
        return cod.equals(c.cod);
    }

    @Override
    public int hashCode() { return cod.hashCode(); }
}
