package com.pao.laboratory02.exercise;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        CarService service = CarService.getInstance();

        while (true) {
            System.out.println("\n=== Meniu ===");
            System.out.println("1. Adaugă mașină");
            System.out.println("2. Listează mașini");
            System.out.println("3. Adaugă review");
            System.out.println("0. Ieșire");
            System.out.print("Alege o opțiune: ");

            int option = scanner.nextInt();
            scanner.nextLine();

            switch (option) {
                case 1:
                    System.out.print("Nume mașină: ");
                    String name = scanner.nextLine();
                    System.out.print("Culoare mașină: ");
                    String color = scanner.nextLine();
                    service.addCar(new Car(name, color));
                    break;
                case 2:
                    service.listAllCars();
                    break;
                case 3:
                    System.out.print("Introdu numele mașinii pentru care lași review: ");
                    String carName = scanner.nextLine();
                    System.out.print("Scrie review-ul: ");
                    String review = scanner.nextLine();
                    service.addReview(carName, review);
                    break;
                case 0:
                    System.out.println("Ieșire din program...");
                    scanner.close();
                    return;
                default:
                    System.out.println("Opțiune invalidă. Încearcă din nou.");
            }
        }
    }
}