package com.simulacao.trabalho.simulacao;

public class Queues {
    private Queue f1;
    private Queue f2;

    public Queue getF1() {
        return f1;
    }

    public void setF1(Queue f1) {
        this.f1 = f1;
    }

    public Queue getF2() {
        return f2;
    }

    public void setF2(Queue f2) {
        this.f2 = f2;
    }

    @Override
    public String toString() {
        return "Queues{" +
                "f1=" + f1 +
                ", f2=" + f2 +
                '}';
    }
}
