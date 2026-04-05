package com.pao.laboratory06.exercise2;

import java.util.Scanner;

public class CIMColaborator extends PersoanaFizica {
    private boolean bonus;

    @Override
    public void citeste(Scanner in) {
        nume = in.next();
        prenume = in.next();
        venitBrutLunar = in.nextDouble();
        if (in.hasNext("DA|NU")) {
            String b = in.next();
            bonus = b.equals("DA");
        } else {
            bonus = false;
        }
    }

    @Override
    public void afiseaza() {
        System.out.printf("CIM: %s %s, venit net anual: %.2f lei%n", nume, prenume, calculeazaVenitNetAnual());
    }

    @Override
    public String tipContract() {
        return "CIM";
    }

    @Override
    public boolean areBonus() {
        return bonus;
    }

    @Override
    public double calculeazaVenitNetAnual() {
        double net = venitBrutLunar * 12 * 0.55;
        if (bonus) net *= 1.10;
        return net;
    }

    @Override
    public TipColaborator getTip() {
        return TipColaborator.CIM;
    }
}