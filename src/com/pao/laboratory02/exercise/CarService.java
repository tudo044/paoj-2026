package com.pao.laboratory02.exercise;

public class CarService {
    private Car[] cars;

    private CarService() {
        this.cars = new Car[0];
    }

    private static class Holder {
        private static final CarService INSTANCE = new CarService();
    }

    public static CarService getInstance() {
        return Holder.INSTANCE;
    }

    public void listAllCars() {
        if (cars.length == 0) {
            System.out.println("Nu există mașini în sistem.");
            return;
        }
        for (int i = 0; i < cars.length; i++) {
            System.out.println((i + 1) + ". " + cars[i]);
        }
    }

    public void addCar(Car car) {
        Car[] tmp = new Car[cars.length + 1];
        System.arraycopy(cars, 0, tmp, 0, cars.length);
        tmp[tmp.length - 1] = car;
        cars = tmp;
        System.out.println("Mașina \"" + car.getName() + "\" a fost adăugată!");
    }

    public void addReview(String carName, String review) {
        for (int i = 0; i < cars.length; i++) {
            if (cars[i].getName().equals(carName)) {
                String[] currentReviews = cars[i].getReviews();
                String[] newReviews = new String[currentReviews.length + 1];

                System.arraycopy(currentReviews, 0, newReviews, 0, currentReviews.length);
                newReviews[newReviews.length - 1] = review;

                cars[i].setReviews(newReviews);
                System.out.println("Review adăugat cu succes pentru mașina: " + carName);
                return;
            }
        }
        System.out.println("Mașina nu a fost găsită.");
    }
}