package com.simulacao.trabalho;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.PriorityQueue;

public class Main {

    public static void main(String[] args) {
        PriorityQueue<Evento> escalonador = new PriorityQueue<>();

        List<Tupla> filasLigadasF1 = new ArrayList<>();
        List<Tupla> filasLigadasF2 = new ArrayList<>();
        Fila fila = new Fila("f1", filasLigadasF1, 2, 4, 2, 3, 4, 7);
        Fila fila2 = new Fila("f2", filasLigadasF2, 1, 100, 0, 0, 4, 8);
        filasLigadasF1.add(new Tupla(fila2, 0.7));
        final List<Fila> filas = Arrays.asList(fila, fila2);

        SimuladorFila simuladorFila = new SimuladorFila(escalonador, filas);

        Evento inicio = new Evento(3, "chegada", null, fila);
        simuladorFila.simular(inicio);

        simular(escalonador, simuladorFila);
        printProbabilidades(filas, simuladorFila.getTempoGlobal());
    }

    private static void simular(PriorityQueue<Evento> escalonador, SimuladorFila simuladorFila) {
        for (int i = 0; i < 1000; i++) {
            Evento evento = escalonador.poll();
            simuladorFila.simular(evento);
        }
    }

    private static void printProbabilidades(List<Fila> filas, double tempoGlobal) {
        for (int j = 0; j < filas.size(); j++) {
            final Fila fila = filas.get(j);
            fila.setTempoGlobal(tempoGlobal);
            System.out.println("\nProbabilidade da fila " + fila.getIdentificador());
            double[] propabilidade = fila.getPropabilidade();
            System.out.println("Probabilidade da fila");
            for (int i = 0; i < propabilidade.length && i < 20; i++) {
                System.out.println(String.format("%s: %.02f", i, propabilidade[i]));
            }
        }
    }

}
