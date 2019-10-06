package com.simulacao.trabalho;

import java.util.Stack;

public class Gerador {

    private static double a = 3425345;
    private static double c = 11; //Maior variacao dos numeros gerados
    private static long M = 8745172233L; //max value
    private static double proximaSemente = 5;

    private static Stack<Double> stack;

    public Gerador() {
        stack = new Stack<>();
        stack.push(0.2931);
        stack.push(0.5011);
        stack.push(0.8324);
        stack.push(0.9922);
        stack.push(0.9172);
        stack.push(0.7208);
        stack.push(0.5131);
        stack.push(0.1211);
        stack.push(0.0322);
        stack.push(0.9211);
        stack.push(0.2323);
        stack.push(0.9910);
        stack.push(0.3456);
        stack.push(0.1109);
        stack.push(0.0103);
        stack.push(0.2176);
    }

    public double generateRandomValue(double semente) {
        return (semente * a + c) % M / M;
    }

    public static double generateRandomValue() {
        return stack.pop();
    }
}
