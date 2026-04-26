package com.pao.proiect.magazin.model;

public interface Stocabil {
    int getCantitate();
    void setCantitate(int cantitate);
    boolean esteDisponibil();
    int getStocMinim();
}
