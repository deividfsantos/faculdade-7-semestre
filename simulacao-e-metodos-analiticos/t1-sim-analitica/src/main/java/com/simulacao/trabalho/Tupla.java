package com.simulacao.trabalho;

public class Tupla {

    private Fila fila;
    private Double probabilidade;

    public Tupla(Fila fila, Double probabilidade) {
        this.fila = fila;
        this.probabilidade = probabilidade;
    }

    public Fila getFila() {
        return fila;
    }

    public void setFila(Fila fila) {
        this.fila = fila;
    }

    public Double getProbabilidade() {
        return probabilidade;
    }

    public void setProbabilidade(Double probabilidade) {
        this.probabilidade = probabilidade;
    }
}
