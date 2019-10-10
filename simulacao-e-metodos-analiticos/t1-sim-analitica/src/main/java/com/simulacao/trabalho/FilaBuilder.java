package com.simulacao.trabalho;

import com.simulacao.trabalho.simulacao.Network;
import com.simulacao.trabalho.simulacao.Queue;
import com.simulacao.trabalho.simulacao.Simulacao;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class FilaBuilder {

    public static List<Fila> buildQueues(Simulacao simulacao) {
        List<Fila> filas = new ArrayList<>();
        montarFilas(simulacao, filas);
        definirFilasConectadas(simulacao, filas);
        return filas;
    }

    private static void montarFilas(Simulacao simulacao, List<Fila> filas) {
        for (Map.Entry<String, Queue> hashQueue : simulacao.getQueues().entrySet()) {
            Queue queue = hashQueue.getValue();
            String idFila = hashQueue.getKey();

            Fila fila = new Fila(idFila,
                    null,
                    queue.getServers(),
                    queue.getCapacity() == 0 ? 9999999 : queue.getCapacity(),
                    queue.getMinArrival(),
                    queue.getMaxArrival(),
                    queue.getMinService(),
                    queue.getMaxService());
            filas.add(fila);
        }
    }

    private static void definirFilasConectadas(Simulacao simulacao, List<Fila> filas) {
        for (Fila f : filas) {
            List<Tupla> filasLigadas = new ArrayList<>();
            List<Network> networks = simulacao.getNetwork();
            for (Network network : networks) {
                if (f.getIdentificador().equalsIgnoreCase(network.getSource())) {
                    Fila queueById1 = findQueueById(filas, network.getTarget());
                    filasLigadas.add(new Tupla(queueById1, network.getProbability()));
                }
            }
            f.setFilasLigadas(filasLigadas);
        }
    }

    public static List<Evento> buscarEventosIniciais(Simulacao simulacao, List<Fila> filas) {
        List<Evento> eventos = new ArrayList<>();
        for (Map.Entry<String, Double> hashArrival : simulacao.getArrivals().entrySet()) {
            Fila queue = findQueueById(filas, hashArrival.getKey());
            eventos.add(new Evento(hashArrival.getValue(), "chegada", null, queue));
        }
        return eventos;
    }

    private static Fila findQueueById(List<Fila> filas, String id) {
        for (int j = 0; j < filas.size(); j++) {
            if (filas.get(j).getIdentificador().equalsIgnoreCase(id)) {
                return filas.get(j);
            }
        }
        return null;
    }
}
