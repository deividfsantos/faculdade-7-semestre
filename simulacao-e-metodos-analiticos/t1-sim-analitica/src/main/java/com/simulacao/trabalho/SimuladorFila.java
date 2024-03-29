package com.simulacao.trabalho;

import java.util.Comparator;
import java.util.List;
import java.util.PriorityQueue;

public class SimuladorFila {

    private PriorityQueue<Evento> escalonador;
    private double tempoGlobal;
    private List<Fila> filas;

    public SimuladorFila(PriorityQueue<Evento> escalonador, List<Fila> filas) {
        this.escalonador = escalonador;
        this.tempoGlobal = 0;
        this.filas = filas;
    }

    public void simular(Evento evento) {
        if (evento.getTipo().equalsIgnoreCase("chegada")) {
            chegada(evento);
        } else if (evento.getTipo().equalsIgnoreCase("saida")) {
            saida(evento);
        } else {
            passagem(evento);
        }
    }

    public void chegada(Evento evento) {
        Fila chegada = evento.getDestino();
        contabilizaTempos(evento);
        if (chegada.getTamFila() < chegada.getKapacidade()) {
            chegada.setTamFila(chegada.getTamFila() + 1);
            if (chegada.getTamFila() <= chegada.getCervidor()) {
                gerarProximoEventoDeSaida(chegada);
            }
        } else chegada.setPerda(chegada.getPerda() + 1);
        escalonador.add(new Evento(tempoGlobal + rnd(chegada.getTcmin(), chegada.getTcmax()), "chegada", null, chegada));
    }

    public void passagem(Evento evento) {
        Fila origem = evento.getOrigem();
        Fila destino = evento.getDestino();
        contabilizaTempos(evento);
        saidaPassagem(origem);
        chegadaPassagem(destino);
    }

    private void saidaPassagem(Fila origem) {
        origem.setTamFila(origem.getTamFila() - 1);
        if (origem.getTamFila() >= origem.getCervidor()) {
            gerarProximoEventoDeSaida(origem);
        }
    }

    private void chegadaPassagem(Fila destino) {
        if (destino.getTamFila() < destino.getKapacidade()) {
            destino.setTamFila(destino.getTamFila() + 1);
            if (destino.getTamFila() <= destino.getCervidor() && destino.getTamFila() != 0) {
                escalonador.add(new Evento(tempoGlobal + rnd(destino.getTsmin(), destino.getTsmax()), "saida", destino, null));
            }
        } else {
            destino.setPerda(destino.getPerda() + 1);
        }
    }

    public void saida(Evento evento) {
        Fila origem = evento.getOrigem();
        contabilizaTempos(evento);
        origem.setTamFila(origem.getTamFila() - 1);
        if (origem.getTamFila() >= origem.getCervidor()) {
            gerarProximoEventoDeSaida(origem);
        }
    }

    private void gerarProximoEventoDeSaida(Fila chegada) {
        double rnd = rnd(0, 1);
        Fila proximaFila = buscaRandomicamenteProximaFila(chegada.getFilasLigadas(), rnd);
        if (proximaFila != null) {
            escalonador.add(new Evento(tempoGlobal + rnd(chegada.getTsmin(), chegada.getTsmax()), "passagem", chegada, proximaFila));
        } else {
            escalonador.add(new Evento(tempoGlobal + rnd(chegada.getTsmin(), chegada.getTsmax()), "saida", chegada, null));
        }
    }

    public Fila buscaRandomicamenteProximaFila(List<Tupla> tuples, double number) {
        tuples.sort(Comparator.comparing(Tupla::getProbabilidade));
        double aux = 0;
        for (int i = 0; i < tuples.size(); i++) {
            aux += tuples.get(i).getProbabilidade();
            if (number < aux) {
                return tuples.get(i).getFila();
            }
        }
        return null;
    }

    private void contabilizaTempos(Evento evento) {
        double delta = evento.getTempo() - tempoGlobal;
        for (Fila fila : filas) {
            int tamFila = fila.getTamFila();
            fila.getTimes()[tamFila] = fila.getTimes()[tamFila] + delta;
        }
        tempoGlobal = evento.getTempo();
    }

    private double rnd(double a, double b) {
        final double rnd = Gerador.generateRandomValue();
        return (b - a) * rnd + a;
    }

    public double getTempoGlobal() {
        return tempoGlobal;
    }
}
