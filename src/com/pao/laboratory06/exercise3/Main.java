package com.pao.laboratory06.exercise3;

import java.util.Arrays;

public class Main {
    public static void main(String[] args) {


        Inginer[] ingineri = {
                new Inginer("Popescu", "Ion", "0721000001", 8000, 15000),
                new Inginer("Andrei", "Maria", "0721000002", 12000, 25000),
                new Inginer("Ionescu", "Vlad", "0721000003", 6000, 10000),
                new Inginer("Badea", "Ana", null, 9000, 20000)
        };


        System.out.println("=== Sortare după nume (natural) ===");
        Arrays.sort(ingineri);
        for (Inginer i : ingineri) System.out.println(i);


        System.out.println("\n=== Sortare după salariu descrescător (Comparator) ===");
        Arrays.sort(ingineri, new ComparatorInginerSalariu());
        for (Inginer i : ingineri) System.out.println(i);


        System.out.println("\n=== Acces prin referință de tip PlataOnline ===");
        PlataOnline plata = new Inginer("Dumitrescu", "Radu", "0722000001", 10000, 30000);
        plata.autentificare("radu_d", "parola123");
        System.out.println("Sold: " + plata.consultareSold() + " lei");
        plata.efectuarePlata(500);
        // plata.getSalariu(); // ❌ nu se poate — PlataOnline nu are getSalariu()

        // --- 5. PersoanaJuridica prin referință PlataOnlineSMS ---
        System.out.println("\n=== PersoanaJuridica cu SMS ===");
        PlataOnlineSMS pj = new PersoanaJuridica("TechCorp", "SRL", "0733000001", 50000);
        pj.autentificare("techcorp", "secret");
        pj.efectuarePlata(1000);
        pj.trimiteSMS("Plată de 1000 lei efectuată cu succes.");
        pj.trimiteSMS("Sold disponibil: 49000 lei.");

        // --- 6. PersoanaJuridica fără telefon ---
        System.out.println("\n=== PersoanaJuridica fără telefon ===");
        PersoanaJuridica pjFaraTelefon = new PersoanaJuridica("MicroSRL", "SRL", null, 20000);
        pjFaraTelefon.trimiteSMS("Mesaj de test"); // returnează false, nu trimite

        // --- 7. SMS cu mesaj invalid ---
        System.out.println("\n=== SMS cu mesaj gol ===");
        PersoanaJuridica pjValid = new PersoanaJuridica("ValidSRL", "SRL", "0744000001", 10000);
        pjValid.trimiteSMS(""); // returnează false
        pjValid.trimiteSMS(null); // returnează false

        // --- 8. SMS-uri stocate ---
        System.out.println("\n=== SMS-uri stocate ===");
        System.out.println("SMS-uri trimise de TechCorp: " + ((PersoanaJuridica) pj).getSmsTrimise());

        // --- 9. Autentificare cu user null — IllegalArgumentException ---
        System.out.println("\n=== Autentificare cu user null ===");
        try {
            plata.autentificare(null, "parola");
        } catch (IllegalArgumentException e) {
            System.out.println("Eroare prinsă: " + e.getMessage());
        }

        // --- 10. Constante financiare din enum ---
        System.out.println("\n=== Constante financiare ===");
        System.out.printf("TVA: %.0f%%%n", ConstanteFinanciare.TVA.getValoare() * 100);
        System.out.printf("Salariu minim: %.0f lei%n", ConstanteFinanciare.SALARIU_MINIM.getValoare());
        System.out.printf("Cota impozit: %.0f%%%n", ConstanteFinanciare.COTA_IMPOZIT.getValoare() * 100);

        // --- 11. trimiteSMS pe Inginer (nu are capabilitate SMS) ---
        System.out.println("\n=== trimiteSMS pe entitate fără capabilitate SMS ===");
        try {
            PlataOnline inginer = new Inginer("Test", "Test", "0700000000", 5000, 5000);
            // Inginer nu implementează PlataOnlineSMS, deci cast-ul aruncă ClassCastException
            PlataOnlineSMS smsTest = (PlataOnlineSMS) inginer;
            smsTest.trimiteSMS("test");
        } catch (ClassCastException e) {
            System.out.println("Eroare prinsă: Inginer nu are capabilitate SMS.");
        }
    }
}