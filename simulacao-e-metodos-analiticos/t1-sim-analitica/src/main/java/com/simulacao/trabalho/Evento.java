package com.simulacao.trabalho;

public class Evento implements Comparable {
    private double tempo;
    private String tipo;
    private Fila origem;
    private Fila destino;

    public Evento(double tempo, String tipo, Fila origem, Fila destino) {
        this.tempo = tempo;
        this.tipo = tipo;
        this.origem = origem;
        this.destino = destino;
    }

    public double getTempo() {
        return tempo;
    }

    public String getTipo() {
        return tipo;
    }

    public Fila getOrigem() {
        return origem;
    }

    public Fila getDestino() {
        return destino;
    }

    @Override
    public int compareTo(Object o) {
        Evento evento = (Evento) o;
        if (tempo > evento.tempo) {
            return 1;
        } else if (tempo == evento.tempo) {
            return 0;
        }
        return -1;
    }

    @Override
    public String toString() {
        return "Evento{" +
                "tempo=" + tempo +
                ", tipo='" + tipo + '\'' +
                ", origem=" + origem +
                ", destino=" + destino +
                '}';
    }
}
