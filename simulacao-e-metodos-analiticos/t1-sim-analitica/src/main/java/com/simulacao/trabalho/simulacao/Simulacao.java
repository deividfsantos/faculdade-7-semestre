package com.simulacao.trabalho.simulacao;

import java.util.HashMap;
import java.util.List;

public class Simulacao {
    private HashMap<String, Double> arrivals;
    private HashMap<String, Queue> queues;
    private List<Network> network;

    public HashMap<String, Double> getArrivals() {
        return arrivals;
    }

    public void setArrivals(HashMap<String, Double> arrivals) {
        this.arrivals = arrivals;
    }

    public HashMap<String, Queue> getQueues() {
        return queues;
    }

    public void setQueues(HashMap<String, Queue> queues) {
        this.queues = queues;
    }

    public List<Network> getNetwork() {
        return network;
    }

    public void setNetwork(List<Network> network) {
        this.network = network;
    }

    @Override
    public String toString() {
        return "Simulacao{" +
                "arrivals=" + arrivals +
                ", queues=" + queues +
                ", network=" + network +
                '}';
    }
}
