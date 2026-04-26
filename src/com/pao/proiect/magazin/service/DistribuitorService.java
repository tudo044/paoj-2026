package com.pao.proiect.magazin.service;

import com.pao.proiect.magazin.exception.DistribuitorNegasitException;
import com.pao.proiect.magazin.exception.ProdusNegasitException;
import com.pao.proiect.magazin.model.*;

import java.util.*;
import java.util.stream.Collectors;

public class DistribuitorService {
    private static DistribuitorService instance;

    private final Map<Integer, Distribuitor> distribuitori = new HashMap<>();
    private final List<Comanda> comenzi = new ArrayList<>();
    private int nextComanId = 1;

    private DistribuitorService() {}

    public static DistribuitorService getInstance() {
        if (instance == null) {
            instance = new DistribuitorService();
        }
        return instance;
    }

    public void adauga(Distribuitor distribuitor) {
        if (distribuitor == null) throw new IllegalArgumentException("Distribuitorul nu poate fi null");
        distribuitori.put(distribuitor.getId(), distribuitor);
        System.out.println("[DistribuitorService] Adaugat: " + distribuitor);
    }

    public void sterge(int id) throws DistribuitorNegasitException {
        if (!distribuitori.containsKey(id)) {
            throw new DistribuitorNegasitException("id=" + id);
        }
        distribuitori.remove(id);
    }

    public Distribuitor gasesteDupaId(int id) throws DistribuitorNegasitException {
        Distribuitor d = distribuitori.get(id);
        if (d == null) throw new DistribuitorNegasitException("id=" + id);
        return d;
    }

    public Distribuitor gasesteDupaNume(String nume) throws DistribuitorNegasitException {
        return distribuitori.values().stream()
                .filter(d -> d.getNume().equalsIgnoreCase(nume))
                .findFirst()
                .orElseThrow(() -> new DistribuitorNegasitException(nume));
    }

    public List<Distribuitor> listeazaToti() {
        return new ArrayList<>(distribuitori.values());
    }

    public Comanda plaseazaComanda(int distribuitorId, Map<Produs, Integer> produseCantitate)
            throws DistribuitorNegasitException {
        Distribuitor dist = gasesteDupaId(distribuitorId);
        Comanda comanda = new Comanda(nextComanId++, dist);
        for (Map.Entry<Produs, Integer> entry : produseCantitate.entrySet()) {
            comanda.adaugaProdus(entry.getKey(), entry.getValue());
        }
        comenzi.add(comanda);
        System.out.println("[DistribuitorService] Comanda plasata: #" + comanda.getId());
        return comanda;
    }

    public List<Comanda> getComenziPentruDistribuitor(int distribuitorId) {
        return comenzi.stream()
                .filter(c -> c.getDistribuitor().getId() == distribuitorId)
                .collect(Collectors.toList());
    }

    public List<Comanda> toateComenzi() {
        return Collections.unmodifiableList(comenzi);
    }
}
