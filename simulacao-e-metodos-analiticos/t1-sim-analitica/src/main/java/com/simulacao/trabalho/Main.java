package com.simulacao.trabalho;

import com.simulacao.trabalho.simulacao.Simulacao;

import java.util.List;
import java.util.PriorityQueue;

public class Main {

    public static void main(String[] args) {
        Simulacao simulacao = new ReadFile().readYmlResource();
        new Gerador(simulacao.getSeeds().isEmpty() ? 1 : Integer.parseInt(simulacao.getSeeds().get(0)));
        PriorityQueue<Evento> escalonador = new PriorityQueue<>();

        List<Fila> filas = FilaBuilder.buildQueues(simulacao);
        SimuladorFila simuladorFila = new SimuladorFila(escalonador, filas);
        executarEventosIniciais(simulacao, filas, simuladorFila);
        simular(escalonador, simuladorFila, simulacao.getRndnumbersPerSeed());
        printProbabilidades(filas, simuladorFila.getTempoGlobal());
    }

    private static void executarEventosIniciais(Simulacao simulacao, List<Fila> filas, SimuladorFila simuladorFila) {
        List<Evento> eventosIniciais = FilaBuilder.buscarEventosIniciais(simulacao, filas);
        for (int i = 0; i < eventosIniciais.size(); i++) {
            simuladorFila.simular(eventosIniciais.get(i));
        }
    }

    private static void simular(PriorityQueue<Evento> escalonador, SimuladorFila simuladorFila, int quantidadeEventos) {
        for (int i = 0; i < quantidadeEventos; i++) {
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
