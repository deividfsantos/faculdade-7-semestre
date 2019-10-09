package com.simulacao.trabalho;

import com.simulacao.trabalho.simulacao.Simulacao;

import java.util.List;
import java.util.PriorityQueue;

public class Main {

    public static void main(String[] args) {
        PriorityQueue<Evento> escalonador = new PriorityQueue<>();
        Simulacao simulacao = new ReadFile().readYmlResource();
        List<Fila> filas = FilaBuilder.buildQueues(simulacao);
        List<Evento> eventosIniciais = FilaBuilder.buscarEventosIniciais(simulacao, filas);

        SimuladorFila simuladorFila = new SimuladorFila(escalonador, filas);
        for (int i = 0; i < eventosIniciais.size(); i++) {
            simuladorFila.simular(eventosIniciais.get(i));
        }

        simular(escalonador, simuladorFila);
        printProbabilidades(filas, simuladorFila.getTempoGlobal());
    }

    private static void simular(PriorityQueue<Evento> escalonador, SimuladorFila simuladorFila) {
        for (int i = 0; i < 100000; i++) {
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
