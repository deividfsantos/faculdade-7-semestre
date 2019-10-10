package com.simulacao.trabalho.simulacao;

import java.util.HashMap;
import java.util.List;

public class Simulacao {
    private HashMap<String, Double> arrivals;
    private HashMap<String, Queue> queues;
    private List<Network> network;
    private List<String> seeds;
    private Integer rndnumbersPerSeed;

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

    public List<String> getSeeds() {
        return seeds;
    }

    public void setSeeds(List<String> seeds) {
        this.seeds = seeds;
    }

    public Integer getRndnumbersPerSeed() {
        return rndnumbersPerSeed;
    }

    public void setRndnumbersPerSeed(Integer rndnumbersPerSeed) {
        this.rndnumbersPerSeed = rndnumbersPerSeed;
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
