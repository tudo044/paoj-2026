package com.pao.laboratory04.collections;

import java.util.*;

public class Main {
    public static void main(String[] args) {
        String[] languages = {"java", "python", "c++", "java", "java", "python", "c++", "rust", "go"};

        System.out.println("=== PARTEA A: HashMap — frecvența cuvintelor ===");
        Map<String, Integer> freq = new HashMap<>();
        for (String lang : languages) {
            freq.put(lang, freq.getOrDefault(lang, 0) + 1);
        }
        System.out.println("Frecvență: " + freq);
        System.out.println("Conține 'rust'? " + freq.containsKey("rust"));
        System.out.println("Chei: " + freq.keySet());
        System.out.println("Valori: " + freq.values());

        for (Map.Entry<String, Integer> entry : freq.entrySet()) {
            System.out.println(entry.getKey() + " -> " + entry.getValue());
        }

        System.out.println("\n=== PARTEA B: TreeMap — sortare automată ===");
        Map<String, Integer> sortedFreq = new TreeMap<>(freq);
        System.out.println("Sortat: " + sortedFreq);
        if (sortedFreq instanceof TreeMap<String, Integer> treeMap) {
            System.out.println("Prima cheie: " + treeMap.firstKey());
            System.out.println("Ultima cheie: " + treeMap.lastKey());
        }

        System.out.println("\n=== PARTEA C: Map cu obiecte ===");
        Map<String, List<String>> cursuri = new HashMap<>();

        List<String> paojStudents = new ArrayList<>(Arrays.asList("Ana", "Mihai", "Ion"));
        cursuri.put("PAOJ", paojStudents);
        System.out.println("Studenți la PAOJ: " + cursuri.get("PAOJ"));

        cursuri.computeIfAbsent("BD", k -> new ArrayList<>()).addAll(Arrays.asList("Ana", "Elena", "George"));
        System.out.println("Studenți la BD (actualizat): " + cursuri.get("BD"));
    }
}