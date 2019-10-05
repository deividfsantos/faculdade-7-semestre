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
        Fila fila = new Fila("f1", filasLigadasF1, 1, 4, 5, 7, 5, 7);
        Fila fila2 = new Fila("f2", filasLigadasF2, 1, 2, 0, 0, 1, 2);
        filasLigadasF1.add(new Tupla(fila2, 1.0));

        SimuladorFila simuladorFila = new SimuladorFila(escalonador);

        Evento inicio = new Evento(3, "chegada", null, fila);
        simuladorFila.simular(inicio);

        simular(escalonador, simuladorFila, 20);
        printProbabilidades(Arrays.asList(fila, fila2), simuladorFila.getTempoGlobal());
    }

    private static void simular(PriorityQueue<Evento> escalonador, SimuladorFila simuladorFila, int unidadeTempo) {
        for (int i = 0; i < 600000; i++) {
            Evento evento = escalonador.poll();
            simuladorFila.simular(evento);
        }

//        while (true) {
//            Evento evento = escalonador.poll();
//            if (evento.getTempo() > unidadeTempo) {
//                break;
//            }
//            simuladorFila.simular(evento);
//        }
    }

    private static void printProbabilidades(List<Fila> filas, double tempoGlobal) {
        for (int j = 0; j < filas.size(); j++) {
            filas.get(j).setTempoGlobal(tempoGlobal);
            System.out.println("\nProbabilidade da fila " + filas.get(j).getIdentificador());
            double[] propabilidade = filas.get(j).getPropabilidade();
            System.out.println("Probabilidade da fila");
            for (int i = 0; i < propabilidade.length; i++) {
                System.out.println(String.format("%s: %.02f", i, propabilidade[i]));
            }
        }
    }

}
