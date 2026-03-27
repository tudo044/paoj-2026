package com.pao.laboratory04.enums;

/**
 * Exemplu demonstrativ — Enum-uri în Java.
 * Rulează acest main pentru a vedea cum funcționează enum-urile cu câmpuri și metode.
 * Apoi rezolvă exercițiul din Main.java (creezi Priority.java de la zero).
 */
public class ExampleEnum {

    private enum Season {
        SPRING, SUMMER, AUTUMN, WINTER
    }

    private enum Planet {
        MERCURY(3.303e+23, 2.4397e6) {
            @Override public String describe() { return "Cea mai apropiată de Soare"; }
        },
        EARTH(5.976e+24, 6.37814e6) {
            @Override public String describe() { return "Planeta noastră"; }
        },
        MARS(6.421e+23, 3.3972e6) {
            @Override public String describe() { return "Planeta roșie"; }
        };

        private final double mass;
        private final double radius;

        Planet(double mass, double radius) {
            this.mass = mass;
            this.radius = radius;
        }

        public double getMass() { return mass; }
        public double getRadius() { return radius; }

        public abstract String describe();

        public double surfaceGravity() {
            final double G = 6.67300E-11;
            return G * mass / (radius * radius);
        }
    }

    public static void main(String[] args) {

        System.out.println("=== Enum simplu ===");
        Season current = Season.SUMMER;
        System.out.println("Sezonul: " + current);
        System.out.println("name(): " + current.name());
        System.out.println("ordinal(): " + current.ordinal());

        System.out.println("\nToate sezoanele:");
        for (Season s : Season.values()) {
            System.out.println("  " + s.name() + " (ordinal=" + s.ordinal() + ")");
        }

        Season fromString = Season.valueOf("WINTER");
        System.out.println("\nvalueOf(\"WINTER\") = " + fromString);

        System.out.println("WINTER == WINTER? " + (fromString == Season.WINTER));
        System.out.println("WINTER == SUMMER? " + (fromString == Season.SUMMER));

        System.out.println("\nSwitch:");
        switch (current) {
            case SPRING: System.out.println("🌸 Primăvară!"); break;
            case SUMMER: System.out.println("☀️ Vară!"); break;
            case AUTUMN: System.out.println("🍂 Toamnă!"); break;
            case WINTER: System.out.println("❄️ Iarnă!"); break;
        }

        System.out.println("\n=== Enum cu câmpuri și metode ===");
        for (Planet p : Planet.values()) {
            System.out.printf("%s: %s (gravitate=%.2f m/s²)%n",
                    p.name(), p.describe(), p.surfaceGravity());
        }

        Planet mars = Planet.valueOf("MARS");
        System.out.println("\nPlanet.valueOf(\"MARS\").describe() = " + mars.describe());
    }
}