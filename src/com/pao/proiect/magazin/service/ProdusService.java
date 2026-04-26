package com.pao.proiect.magazin.service;

import com.pao.proiect.magazin.exception.ProdusNegasitException;
import com.pao.proiect.magazin.exception.StocInsuficientException;
import com.pao.proiect.magazin.model.*;

import java.util.*;
import java.util.stream.Collectors;

public class ProdusService {
    private static ProdusService instance;

    private final List<Produs> produse = new ArrayList<>();
    private final Map<String, List<Produs>> produsePerCategorie = new HashMap<>();

    private ProdusService() {}

    public static ProdusService getInstance() {
        if (instance == null) {
            instance = new ProdusService();
        }
        return instance;
    }

    public void adauga(Produs produs) {
        if (produs == null) throw new IllegalArgumentException("Produsul nu poate fi null");
        produse.add(produs);
        produsePerCategorie
                .computeIfAbsent(produs.getCategorie().getNume(), k -> new ArrayList<>())
                .add(produs);
        System.out.println("[ProdusService] Adaugat: " + produs);
    }

    public void sterge(String cod) throws ProdusNegasitException {
        Produs p = gasesteDupaCod(cod);
        produse.remove(p);
        produsePerCategorie.getOrDefault(p.getCategorie().getNume(), new ArrayList<>()).remove(p);
        System.out.println("[ProdusService] Sters: " + p.getNume());
    }

    public Produs gasesteDupaCod(String cod) throws ProdusNegasitException {
        return produse.stream()
                .filter(p -> p.getCodProdus().getCod().equals(cod))
                .findFirst()
                .orElseThrow(() -> new ProdusNegasitException(cod));
    }

    public List<Produs> listeazaToare() {
        return Collections.unmodifiableList(produse);
    }

    public List<Produs> cautaDupaCategorie(String numeCategorie) {
        return produsePerCategorie.getOrDefault(numeCategorie, new ArrayList<>());
    }

    public void actualizeazaStoc(String cod, int cantitateNoua) throws ProdusNegasitException {
        Produs p = gasesteDupaCod(cod);
        p.setCantitate(cantitateNoua);
        System.out.println("[ProdusService] Stoc actualizat pentru " + p.getNume() + ": " + cantitateNoua);
    }

    public void scadeStoc(String cod, int cantitate) throws ProdusNegasitException, StocInsuficientException {
        Produs p = gasesteDupaCod(cod);
        if (p.getCantitate() < cantitate) {
            throw new StocInsuficientException(p.getNume(), p.getCantitate(), cantitate);
        }
        p.setCantitate(p.getCantitate() - cantitate);
    }

    public List<Produs> produseSubStocMinim() {
        return produse.stream()
                .filter(p -> p.getCantitate() < p.getStocMinim())
                .collect(Collectors.toList());
    }

    public List<Produs> topDupaValoareStoc(int n) {
        return produse.stream()
                .sorted()
                .limit(n)
                .collect(Collectors.toList());
    }

    public List<StocAlert> genereazaAlerte() {
        List<StocAlert> alerte = new ArrayList<>();
        for (Produs p : produseSubStocMinim()) {
            alerte.add(new StocAlert(p));
        }
        return alerte;
    }

    public Map<String, List<Produs>> getProdusePerCategorie() {
        return Collections.unmodifiableMap(produsePerCategorie);
    }
}
