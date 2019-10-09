package com.simulacao.trabalho;

public class Gerador {

    private static double a = 3425345;
    private static double c = 11; //Maior variacao dos numeros gerados
    private static long M = 8745172233L; //max value
    private static double proximaSemente = 1;

    public double generateRandomValue(double semente) {
        return (semente * a + c) % M / M;
    }

    public static double generateRandomValue() {
        proximaSemente = (proximaSemente * a + c) % M;
        return proximaSemente / M;
    }

}
