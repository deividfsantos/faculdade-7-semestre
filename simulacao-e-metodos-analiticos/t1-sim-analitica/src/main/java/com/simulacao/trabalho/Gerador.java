package com.simulacao.trabalho;

import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

public class Gerador {

    private static double a = 3425345;
    private static double c = 11;
    private static long M = 8745172233L;
    private static double proximaSemente;
    private static Queue<Double> numerosAleatorios;
    private static int limite;

    public Gerador(int semente, List<Double> numerosAleatorios, int limite) {
        proximaSemente = semente;
        Gerador.limite = limite;
        if (numerosAleatorios != null) {
            Gerador.numerosAleatorios = new LinkedList<>(numerosAleatorios);
            Gerador.numerosAleatorios.addAll(numerosAleatorios);
        }
    }

    public double generateRandomValue(double semente) {
        return (semente * a + c) % M / M;
    }

    public static double generateRandomValue() {
        limite--;
        if (numerosAleatorios != null) {
            return numerosAleatorios.remove();
        }
        proximaSemente = (proximaSemente * a + c) % M;
        return proximaSemente / M;
    }

    public static int getLimite() {
        return limite;
    }
}
