package com.simulacao.trabalho;

public class Gerador {

    private static double a = 3425345;
    private static double c = 11;
    private static long M = 8745172233L;
    private static double proximaSemente;

    public Gerador(int semente) {
        proximaSemente = semente;
    }

    public double generateRandomValue(double semente) {
        return (semente * a + c) % M / M;
    }

    public static double generateRandomValue() {
        proximaSemente = (proximaSemente * a + c) % M;
        return proximaSemente / M;
    }

}
