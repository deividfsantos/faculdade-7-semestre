package com.simulacao.trabalho;

import java.util.Comparator;
import java.util.List;
import java.util.PriorityQueue;

public class SimuladorFila {

    private PriorityQueue<Evento> escalonador;
    private double tempoGlobal;

    public SimuladorFila(PriorityQueue<Evento> escalonador) {
        this.escalonador = escalonador;
        this.tempoGlobal = 0;
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
        double delta = evento.getTempo() - tempoGlobal;
        chegada.getTimes()[chegada.getTamFila()] = chegada.getTimes()[chegada.getTamFila()] + delta;
        tempoGlobal = evento.getTempo();
        if (chegada.getTamFila() < chegada.getKapacidade()) {
            chegada.setTamFila(chegada.getTamFila() + 1);
            if (chegada.getTamFila() <= chegada.getCervidor()) {
                double rnd = rnd(0, 1);
                Fila proximaFila = buscaRandomicamenteProximaFila(chegada.getFilasLigadas(), rnd);
                if (proximaFila != null) {
                    escalonador.add(new Evento(tempoGlobal + rnd(chegada.getTsmin(), chegada.getTsmax()), "passagem", chegada, proximaFila));
                } else {
                    escalonador.add(new Evento(tempoGlobal + rnd(chegada.getTsmin(), chegada.getTsmax()), "saida", chegada, null));
                }
            }
        } else chegada.setPerda(chegada.getPerda() + 1);
        escalonador.add(new Evento(tempoGlobal + rnd(chegada.getTcmin(), chegada.getTcmax()), "chegada", null, chegada));
    }

    public void passagem(Evento evento) {
        Fila origem = evento.getOrigem();
        Fila destino = evento.getDestino();
        saidaPassagem(evento, origem);
        chegadaPassagem(destino);
    }

    private void saidaPassagem(Evento evento, Fila origem) {
        double delta = evento.getTempo() - tempoGlobal;
        origem.getTimes()[origem.getTamFila()] = origem.getTimes()[origem.getTamFila()] + delta;
        tempoGlobal = evento.getTempo();

        origem.setTamFila(origem.getTamFila() - 1);
        if (origem.getTamFila() >= origem.getCervidor()) {
            double rnd = rnd(0, 1);
            Fila proxFila = buscaRandomicamenteProximaFila(origem.getFilasLigadas(), rnd);
            if (proxFila != null) {
                escalonador.add(new Evento(tempoGlobal + rnd(origem.getTsmin(), origem.getTsmax()), "passagem", origem, proxFila));
            } else {
                escalonador.add(new Evento(tempoGlobal + rnd(origem.getTsmin(), origem.getTsmax()), "saida", origem, null));
            }
        }
    }

    private void chegadaPassagem(Fila destino) {
        if (destino.getTamFila() <= destino.getKapacidade()) {
            destino.setTamFila(destino.getTamFila() + 1);
            if (destino.getTamFila() <= destino.getCervidor()) {
                escalonador.add(new Evento(tempoGlobal + rnd(destino.getTsmin(), destino.getTsmax()), "saida", destino, null));
            }
        } else {
            destino.setPerda(destino.getPerda() + 1);
        }
    }

    public void saida(Evento evento) {
        Fila origem = evento.getOrigem();
        double delta = evento.getTempo() - tempoGlobal;
        origem.getTimes()[origem.getTamFila()] = origem.getTimes()[origem.getTamFila()] + delta;
        tempoGlobal = evento.getTempo();
        origem.setTamFila(origem.getTamFila() - 1);
        if (origem.getTamFila() >= origem.getCervidor()) {
            if (origem.getTamFila() >= origem.getCervidor()) {
                double rnd = rnd(0, 1);
                Fila proximaFila = buscaRandomicamenteProximaFila(origem.getFilasLigadas(), rnd);
                if (proximaFila != null) {
                    escalonador.add(new Evento(tempoGlobal + rnd(origem.getTsmin(), origem.getTsmax()), "passagem", origem, proximaFila));
                } else {
                    escalonador.add(new Evento(tempoGlobal + rnd(origem.getTsmin(), origem.getTsmax()), "saida", origem, null));
                }
            }
            escalonador.add(new Evento(tempoGlobal + rnd(origem.getTsmin(), origem.getTsmax()), "saida", evento.getOrigem(), null));
        }
    }

    private double rnd(int b, int a) {
        return (b - a) * Gerador.generateRandomValue() + a;
    }

    public double getTempoGlobal() {
        return tempoGlobal;
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

//    public static void main(String[] args) {
//        List<Tuple> filasLigadasF1 = new ArrayList<>();
//        List<Tuple> filasLigadasF2 = new ArrayList<>();
//        Fila fila = new Fila("f1", filasLigadasF1, 1, 4, 5, 7, 5, 7);
//        Fila fila2 = new Fila("f2", filasLigadasF2, 1, 2, 0, 0, 1, 2);
//        Fila fila3 = new Fila("f3", new ArrayList<>(), 1, 3, 0, 0, 4, 6);
//        filasLigadasF1.add(new Tuple(fila2, 1.0));
//        filasLigadasF2.add(new Tuple(fila3, 1.0));
//
//        final Fila teste = new SimuladorFila(null).teste(filasLigadasF1, 0.5);
//        System.out.println(teste);
//    }
}
