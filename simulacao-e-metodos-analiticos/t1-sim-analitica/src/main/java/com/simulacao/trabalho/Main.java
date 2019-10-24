package com.simulacao.trabalho;

import com.simulacao.trabalho.simulacao.Simulacao;

import java.util.List;
import java.util.PriorityQueue;

public class Main {

    public static void main(String[] args) {
        Simulacao simulacao = new ReadFile().readYmlResource();
        List<Double> rndnumbers = simulacao.getRndnumbers();
        new Gerador(getSemente(simulacao), rndnumbers, getQuantidadeEventos(simulacao, rndnumbers));
        PriorityQueue<Evento> escalonador = new PriorityQueue<>();
        List<Fila> filas = FilaBuilder.buildQueues(simulacao);
        SimuladorFila simuladorFila = new SimuladorFila(escalonador, filas);
        executarEventosIniciais(simulacao, filas, simuladorFila);
        simular(escalonador, simuladorFila);
        printProbabilidades(filas, simuladorFila.getTempoGlobal());
    }

    private static int getQuantidadeEventos(Simulacao simulacao, List<Double> rndnumbers) {
        return simulacao.getRndnumbersPerSeed() == null ? rndnumbers.size() : simulacao.getRndnumbersPerSeed();
    }

    private static int getSemente(Simulacao simulacao) {
        if (simulacao.getSeeds() != null) {
            return simulacao.getSeeds().isEmpty() ? 1 : Integer.parseInt(simulacao.getSeeds().get(0));
        }
        return 0;
    }

    private static void executarEventosIniciais(Simulacao simulacao, List<Fila> filas, SimuladorFila simuladorFila) {
        List<Evento> eventosIniciais = FilaBuilder.buscarEventosIniciais(simulacao, filas);
        for (int i = 0; i < eventosIniciais.size(); i++) {
            simuladorFila.simular(eventosIniciais.get(i));
        }
    }

    private static void simular(PriorityQueue<Evento> escalonador, SimuladorFila simuladorFila) {
        while (Gerador.getLimite() > 0) {
            Evento evento = escalonador.poll();
            simuladorFila.simular(evento);
        }
    }

    private static void printProbabilidades(List<Fila> filas, double tempoGlobal) {
        for (int j = 0; j < filas.size(); j++) {
            final Fila fila = filas.get(j);
            fila.setTempoGlobal(tempoGlobal);
            System.out.println("\nProbabilidades da fila " + fila.getIdentificador());
            double[] propabilidade = fila.getPropabilidade();
            System.out.println(String.format("Estado   Probabilidade   Tempo"));
            for (int i = 0; i < propabilidade.length && i < 20; i++) {
                System.out.println(String.format("%s: %12.02f%% %18.04f", i, propabilidade[i], fila.getTimes()[i]));
            }
        }
        System.out.println("Tempo global: " + tempoGlobal);
    }

}
