package com.simulacao.trabalho.simulacao;

public class Queue {
    private int servers;
    private int capacity;
    private double minArrival;
    private double maxArrival;
    private double minService;
    private double maxService;

    public Integer getServers() {
        return servers;
    }

    public void setServers(Integer servers) {
        this.servers = servers;
    }

    public Integer getCapacity() {
        return capacity;
    }

    public void setCapacity(Integer capacity) {
        this.capacity = capacity;
    }

    public Double getMinArrival() {
        return minArrival;
    }

    public void setMinArrival(Double minArrival) {
        this.minArrival = minArrival;
    }

    public Double getMaxArrival() {
        return maxArrival;
    }

    public void setMaxArrival(Double maxArrival) {
        this.maxArrival = maxArrival;
    }

    public Double getMinService() {
        return minService;
    }

    public void setMinService(Double minService) {
        this.minService = minService;
    }

    public Double getMaxService() {
        return maxService;
    }

    public void setMaxService(Double maxService) {
        this.maxService = maxService;
    }

    @Override
    public String toString() {
        return "Queue{" +
                "servers=" + servers +
                ", capacity=" + capacity +
                ", minArrival=" + minArrival +
                ", maxArrival=" + maxArrival +
                ", minService=" + minService +
                ", maxService=" + maxService +
                '}';
    }
}
