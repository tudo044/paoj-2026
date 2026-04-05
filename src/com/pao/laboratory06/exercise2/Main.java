package com.pao.laboratory06.exercise2;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);
        int n = in.nextInt();
        List<Colaborator> colaboratori = new ArrayList<>();

        for (int i = 0; i < n; i++) {
            String tip = in.next();
            Colaborator c = switch (tip) {
                case "CIM" -> new CIMColaborator();
                case "PFA" -> new PFAColaborator();
                case "SRL" -> new SRLColaborator();
                default -> throw new IllegalArgumentException("Tip necunoscut: " + tip);
            };
            c.citeste(in);
            colaboratori.add(c);
        }

        // Sortează și afișează pe tip, fiecare descrescător după venit net anual
        for (TipColaborator tipColab : TipColaborator.values()) {
            colaboratori.stream()
                    .filter(c -> c.getTip() == tipColab)
                    .sorted((a, b) -> Double.compare(b.calculeazaVenitNetAnual(), a.calculeazaVenitNetAnual()))
                    .forEach(Colaborator::afiseaza);
        }

        // Colaborator cu venit net maxim
        Colaborator max = colaboratori.stream()
                .max(Comparator.comparingDouble(Colaborator::calculeazaVenitNetAnual))
                .orElse(null);
        System.out.println();
        System.out.print("Colaborator cu venit net maxim: ");
        if (max != null) max.afiseaza();

        // Colaboratori persoane juridice (SRL)
        System.out.println("\nColaboratori persoane juridice:");
        colaboratori.stream()
                .filter(c -> c instanceof PersoanaJuridica)
                .sorted((a, b) -> Double.compare(b.calculeazaVenitNetAnual(), a.calculeazaVenitNetAnual()))
                .forEach(Colaborator::afiseaza);


        System.out.println("\nSume și număr colaboratori pe tip:");
        for (TipColaborator tipColab : TipColaborator.values()) {
            double suma = colaboratori.stream()
                    .filter(c -> c.getTip() == tipColab)
                    .mapToDouble(Colaborator::calculeazaVenitNetAnual)
                    .sum();
            long numar = colaboratori.stream()
                    .filter(c -> c.getTip() == tipColab)
                    .count();
            if (numar > 0) {
                System.out.printf("%s: suma = %.2f lei, număr = %d%n", tipColab, suma, numar);
            }
        }
    }
}