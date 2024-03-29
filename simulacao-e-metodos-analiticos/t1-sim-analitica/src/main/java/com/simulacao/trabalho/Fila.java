package com.simulacao.trabalho;

import java.util.List;

public class Fila {

    private String identificador;
    private int cervidor;
    private int kapacidade;
    private double[] times;
    private int tamFila;
    private double tcmin;
    private double tcmax;
    private double tsmin;
    private double tsmax;
    private double tempoGlobal;
    private int perda;
    private List<Tupla> filasLigadas;

    public Fila(String identificador, List<Tupla> filasLigadas, int cervidor, int kapacidade, double tcmin, double tcmax, double tsmin, double tsmax) {
        this.identificador = identificador;
        this.filasLigadas = filasLigadas;

        this.times = new double[kapacidade + 1];
        this.tamFila = 0;

        this.tempoGlobal = 0;
        this.perda = 0;
        this.cervidor = cervidor;
        this.kapacidade = kapacidade;
        this.tcmin = tcmin;
        this.tcmax = tcmax;
        this.tsmin = tsmin;
        this.tsmax = tsmax;
    }

    public double[] getPropabilidade() {
        System.out.println("Total de perdas " + perda);
        double[] probabilidades = new double[times.length];
        for (int i = 0; i < probabilidades.length; i++) {
            probabilidades[i] = times[i] / tempoGlobal * 100;
        }
        return probabilidades;
    }

    public double[] getTimes() {
        return times;
    }

    public int getCervidor() {
        return cervidor;
    }

    public String getIdentificador() {
        return identificador;
    }

    public int getKapacidade() {
        return kapacidade;
    }

    public int getTamFila() {
        return tamFila;
    }

    public double getTcmin() {
        return tcmin;
    }

    public double getTcmax() {
        return tcmax;
    }

    public double getTsmin() {
        return tsmin;
    }

    public double getTsmax() {
        return tsmax;
    }

    public List<Tupla> getFilasLigadas() {
        return filasLigadas;
    }

    public void setFilasLigadas(List<Tupla> filasLigadas) {
        this.filasLigadas = filasLigadas;
    }

    public void setTamFila(int tamFila) {
        this.tamFila = tamFila;
    }

    public void setTempoGlobal(double tempoGlobal) {
        this.tempoGlobal = tempoGlobal;
    }

    public void setPerda(int perda) {
        this.perda = perda;
    }

    public int getPerda() {
        return perda;
    }
}
