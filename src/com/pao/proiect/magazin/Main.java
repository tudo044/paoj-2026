package com.pao.proiect.magazin;

import com.pao.proiect.magazin.exception.*;
import com.pao.proiect.magazin.model.*;
import com.pao.proiect.magazin.service.*;

import java.util.*;

public class Main {

    static ProdusService produsService = ProdusService.getInstance();
    static DistribuitorService distribuitorService = DistribuitorService.getInstance();
    static Scanner scanner = new Scanner(System.in);
    static int nextProdusId = 1;
    static int nextDistribuitorId = 1;
    static Map<Integer, Categorie> categorii = new LinkedHashMap<>();
    static int nextCategorieId = 1;

    public static void main(String[] args) {
        initDateInitiale();

        boolean running = true;
        while (running) {
            printMeniu();
            System.out.print("Alegere: ");
            String input = scanner.nextLine().trim();
            System.out.println();

            switch (input) {
                case "1" -> actiunea1_adaugaProdus();
                case "2" -> actiunea2_actualizeazaStoc();
                case "3" -> actiunea3_plaseazaComanda();
                case "4" -> actiunea4_cautaDupaCategorie();
                case "5" -> actiunea5_produseSubStocMinim();
                case "6" -> actiunea6_istoricComenzi();
                case "7" -> actiunea7_stergeProdus();
                case "8" -> actiunea8_topProduse();
                case "9" -> actiunea9_cautaDistribuitor();
                case "10" -> actiunea10_genereazaAlerte();
                case "0" -> {
                    System.out.println("La revedere!");
                    running = false;
                }
                default -> System.out.println("Optiune invalida. Incearca din nou.");
            }

            if (running) {
                System.out.println("\nApasa ENTER pentru a continua...");
                scanner.nextLine();
            }
        }
    }

    static void printMeniu() {
        System.out.println("╔══════════════════════════════════════════╗");
        System.out.println("║       GESTIUNE STOCURI MAGAZIN           ║");
        System.out.println("╠══════════════════════════════════════════╣");
        System.out.println("║  1.  Adauga produs nou                   ║");
        System.out.println("║  2.  Actualizeaza stoc produs            ║");
        System.out.println("║  3.  Plaseaza comanda catre distribuitor ║");
        System.out.println("║  4.  Cauta produse dupa categorie        ║");
        System.out.println("║  5.  Produse cu stoc sub minim           ║");
        System.out.println("║  6.  Istoric comenzi distribuitor        ║");
        System.out.println("║  7.  Sterge produs                       ║");
        System.out.println("║  8.  Top produse dupa valoare stoc       ║");
        System.out.println("║  9.  Cauta distribuitor dupa nume        ║");
        System.out.println("║  10. Genereaza alerte stoc critic        ║");
        System.out.println("║  0.  Iesire                              ║");
        System.out.println("╚══════════════════════════════════════════╝");
    }

    static void actiunea1_adaugaProdus() {
        System.out.println("=== ADAUGA PRODUS NOU ===");
        listeazaCategorii();
        System.out.print("ID categorie: ");
        int catId = citestInt();
        Categorie categorie = categorii.get(catId);
        if (categorie == null) {
            System.out.println("Categorie inexistenta.");
            return;
        }
        System.out.print("Nume produs: ");
        String nume = scanner.nextLine().trim();
        System.out.print("Descriere: ");
        String descriere = scanner.nextLine().trim();
        System.out.print("Pret (RON): ");
        double pret = citestDouble();
        System.out.print("Cantitate initiala: ");
        int cantitate = citestInt();
        System.out.print("Stoc minim: ");
        int stocMinim = citestInt();

        CodProdus cod = new CodProdus(categorie.getCodCategorie(), 2024, nextProdusId++);
        Produs p = new Produs(cod, nume, descriere, pret, cantitate, stocMinim, categorie);

        listeazaDistribuitori();
        System.out.print("ID distribuitor (0 = fara distribuitor): ");
        int distId = citestInt();
        if (distId > 0) {
            try {
                p.setDistribuitor(distribuitorService.gasesteDupaId(distId));
            } catch (DistribuitorNegasitException e) {
                System.out.println("Distribuitor negasit, produs adaugat fara distribuitor.");
            }
        }

        produsService.adauga(p);
        System.out.println("Produs adaugat cu codul: " + cod);
    }

    static void actiunea2_actualizeazaStoc() {
        System.out.println("=== ACTUALIZEAZA STOC ===");
        listeazaProduse();
        System.out.print("Cod produs: ");
        String cod = scanner.nextLine().trim();
        System.out.print("Cantitate noua: ");
        int cantitate = citestInt();
        try {
            produsService.actualizeazaStoc(cod, cantitate);
            System.out.println("Stoc actualizat cu succes.");
        } catch (ProdusNegasitException e) {
            System.out.println("Eroare: " + e.getMessage());
        }
    }

    static void actiunea3_plaseazaComanda() {
        System.out.println("=== PLASEAZA COMANDA ===");
        listeazaDistribuitori();
        System.out.print("ID distribuitor: ");
        int distId = citestInt();

        Map<Produs, Integer> cos = new LinkedHashMap<>();
        while (true) {
            listeazaProduse();
            System.out.print("Cod produs (sau 'gata' pentru finalizare): ");
            String cod = scanner.nextLine().trim();
            if (cod.equalsIgnoreCase("gata")) break;
            try {
                Produs p = produsService.gasesteDupaCod(cod);
                System.out.print("Cantitate: ");
                int cant = citestInt();
                cos.put(p, cant);
                System.out.println("Adaugat: " + p.getNume() + " x" + cant);
            } catch (ProdusNegasitException e) {
                System.out.println("Eroare: " + e.getMessage());
            }
        }

        if (cos.isEmpty()) {
            System.out.println("Comanda goala, nu a fost plasata.");
            return;
        }

        try {
            Comanda comanda = distribuitorService.plaseazaComanda(distId, cos);
            System.out.println("\nComanda plasata cu succes!");
            System.out.println(comanda);
        } catch (DistribuitorNegasitException e) {
            System.out.println("Eroare: " + e.getMessage());
        }
    }

    static void actiunea4_cautaDupaCategorie() {
        System.out.println("=== CAUTA PRODUSE DUPA CATEGORIE ===");
        listeazaCategorii();
        System.out.print("Nume categorie: ");
        String numeCategorie = scanner.nextLine().trim();
        List<Produs> rezultate = produsService.cautaDupaCategorie(numeCategorie);
        if (rezultate.isEmpty()) {
            System.out.println("Niciun produs gasit in categoria '" + numeCategorie + "'.");
        } else {
            System.out.println("Produse in categoria '" + numeCategorie + "':");
            rezultate.forEach(p -> System.out.println("  " + p));
        }
    }

    static void actiunea5_produseSubStocMinim() {
        System.out.println("=== PRODUSE CU STOC SUB MINIM ===");
        List<Produs> lista = produsService.produseSubStocMinim();
        if (lista.isEmpty()) {
            System.out.println("Toate produsele au stoc suficient.");
        } else {
            lista.forEach(p -> System.out.printf("  %-20s stoc: %d  (minim: %d)%n",
                    p.getNume(), p.getCantitate(), p.getStocMinim()));
        }
    }

    static void actiunea6_istoricComenzi() {
        System.out.println("=== ISTORIC COMENZI DISTRIBUITOR ===");
        listeazaDistribuitori();
        System.out.print("ID distribuitor: ");
        int id = citestInt();
        List<Comanda> comenzi = distribuitorService.getComenziPentruDistribuitor(id);
        if (comenzi.isEmpty()) {
            System.out.println("Nicio comanda gasita pentru acest distribuitor.");
        } else {
            comenzi.forEach(System.out::println);
        }
    }

    static void actiunea7_stergeProdus() {
        System.out.println("=== STERGE PRODUS ===");
        listeazaProduse();
        System.out.print("Cod produs de sters: ");
        String cod = scanner.nextLine().trim();
        try {
            produsService.sterge(cod);
            System.out.println("Produs sters cu succes.");
        } catch (ProdusNegasitException e) {
            System.out.println("Eroare: " + e.getMessage());
        }
    }

    static void actiunea8_topProduse() {
        System.out.println("=== TOP PRODUSE DUPA VALOARE STOC ===");
        System.out.print("Cate produse (ex: 5): ");
        int n = citestInt();
        List<Produs> top = produsService.topDupaValoareStoc(n);
        for (int i = 0; i < top.size(); i++) {
            Produs p = top.get(i);
            System.out.printf("  %d. %-20s  valoare stoc: %.2f RON%n",
                    i + 1, p.getNume(), p.getValoareStoc());
        }
    }

    static void actiunea9_cautaDistribuitor() {
        System.out.println("=== CAUTA DISTRIBUITOR DUPA NUME ===");
        System.out.print("Nume distribuitor: ");
        String nume = scanner.nextLine().trim();
        try {
            Distribuitor d = distribuitorService.gasesteDupaNume(nume);
            System.out.println("Gasit: " + d);
        } catch (DistribuitorNegasitException e) {
            System.out.println("Eroare: " + e.getMessage());
        }
    }

    static void actiunea10_genereazaAlerte() {
        System.out.println("=== ALERTE STOC CRITIC ===");
        List<StocAlert> alerte = produsService.genereazaAlerte();
        if (alerte.isEmpty()) {
            System.out.println("Nicio alerta. Toate produsele au stoc suficient.");
        } else {
            alerte.forEach(System.out::println);
        }
    }

    static void listeazaProduse() {
        System.out.println("Produse disponibile:");
        produsService.listeazaToare().forEach(p ->
                System.out.printf("  [%s] %-20s stoc: %d%n",
                        p.getCodProdus().getCod(), p.getNume(), p.getCantitate()));
    }

    static void listeazaDistribuitori() {
        System.out.println("Distribuitori:");
        distribuitorService.listeazaToti().forEach(d ->
                System.out.printf("  [%d] %s%n", d.getId(), d.getNume()));
    }

    static void listeazaCategorii() {
        System.out.println("Categorii:");
        categorii.forEach((id, cat) ->
                System.out.printf("  [%d] %s%n", id, cat.getNume()));
    }

    static int citestInt() {
        while (true) {
            try {
                return Integer.parseInt(scanner.nextLine().trim());
            } catch (NumberFormatException e) {
                System.out.print("Introdu un numar intreg valid: ");
            }
        }
    }

    static double citestDouble() {
        while (true) {
            try {
                return Double.parseDouble(scanner.nextLine().trim().replace(',', '.'));
            } catch (NumberFormatException e) {
                System.out.print("Introdu un numar valid: ");
            }
        }
    }

    static void initDateInitiale() {
        Categorie electronice = new Categorie(nextCategorieId, "Electronice", "Gadgeturi si aparatura", "EL");
        categorii.put(nextCategorieId++, electronice);
        Categorie alimente = new Categorie(nextCategorieId, "Alimente", "Produse alimentare", "AL");
        categorii.put(nextCategorieId++, alimente);

        Distribuitor techDist = new Distribuitor(nextDistribuitorId++, "TechWorld SRL", "contact@techworld.ro", "0721000001", "Str. Mihai Viteazu 5, Bucuresti", "RO12345678");
        Distribuitor foodDist = new Distribuitor(nextDistribuitorId++, "ProFood SA", "office@profood.ro", "0722000002", "Str. Industriilor 22, Ploiesti", "RO87654321");
        distribuitorService.adauga(techDist);
        distribuitorService.adauga(foodDist);

        Produs laptop = new Produs(new CodProdus("EL", 2024, nextProdusId++), "Laptop Dell", "Laptop 15 inch, i7", 3500.0, 10, 3, electronice);
        Produs phone = new Produs(new CodProdus("EL", 2024, nextProdusId++), "Samsung S24", "Smartphone flagship", 4200.0, 5, 2, electronice);
        Produs paine = new Produs(new CodProdus("AL", 2024, nextProdusId++), "Paine alba", "Paine 800g", 5.5, 2, 10, alimente);
        Produs lapte = new Produs(new CodProdus("AL", 2024, nextProdusId++), "Lapte 3.5%", "Lapte integral 1L", 8.0, 50, 20, alimente);

        laptop.setDistribuitor(techDist);
        phone.setDistribuitor(techDist);
        paine.setDistribuitor(foodDist);
        lapte.setDistribuitor(foodDist);

        produsService.adauga(laptop);
        produsService.adauga(phone);
        produsService.adauga(paine);
        produsService.adauga(lapte);

        System.out.println("Date initiale incarcate: 4 produse, 2 distribuitori, 2 categorii.\n");
    }
}