package com.pao.laboratory04.collections;

import java.util.*;

/**
 * Exemplu demonstrativ — Map-uri în Java.
 * Rulează acest main pentru a vedea cum funcționează HashMap, TreeMap și Map cu liste.
 * Apoi rezolvă exercițiul din Main.java (date diferite, aceleași concepte).
 */
public class ExampleMap {
    public static void main(String[] args) {

        System.out.println("=== HashMap — baza ===");
        Map<String, Integer> population = new HashMap<>();
        population.put("București", 1_900_000);
        population.put("Cluj", 325_000);
        population.put("Timișoara", 320_000);
        population.put("Iași", 290_000);

        System.out.println("Map complet: " + population);
        System.out.println("Populația Cluj: " + population.get("Cluj"));
        System.out.println("Există Brașov? " + population.containsKey("Brașov"));

        int brasov = population.getOrDefault("Brașov", 0);
        System.out.println("Brașov (cu default): " + brasov);

        System.out.println("\nIterare cu entrySet:");
        for (Map.Entry<String, Integer> entry : population.entrySet()) {
            System.out.println("  " + entry.getKey() + " => " + entry.getValue());
        }

        System.out.println("Chei: " + population.keySet());
        System.out.println("Valori: " + population.values());

        System.out.println("\n=== TreeMap — sortat automat ===");
        TreeMap<String, Integer> sorted = new TreeMap<>(population);
        System.out.println("Sortat: " + sorted);
        System.out.println("Prima cheie: " + sorted.firstKey());
        System.out.println("Ultima cheie: " + sorted.lastKey());

        System.out.println("\n=== Pattern: numărare frecvență ===");
        String[] culori = {"roșu", "verde", "roșu", "albastru", "verde", "roșu"};
        Map<String, Integer> freq = new HashMap<>();
        for (String c : culori) {
            freq.put(c, freq.getOrDefault(c, 0) + 1);
        }
        System.out.println("Frecvență culori: " + freq);

        System.out.println("\n=== Map<String, List<String>> ===");
        Map<String, List<String>> echipe = new HashMap<>();
        echipe.put("Backend", new ArrayList<>(Arrays.asList("Ana", "Dan")));
        echipe.put("Frontend", new ArrayList<>(Arrays.asList("Maria", "Ion")));

        System.out.println("Backend: " + echipe.get("Backend"));

        echipe.get("Backend").add("Elena");
        System.out.println("Backend (actualizat): " + echipe.get("Backend"));

        echipe.computeIfAbsent("QA", k -> new ArrayList<>()).add("George");
        System.out.println("QA (creat automat): " + echipe.get("QA"));
    }
}