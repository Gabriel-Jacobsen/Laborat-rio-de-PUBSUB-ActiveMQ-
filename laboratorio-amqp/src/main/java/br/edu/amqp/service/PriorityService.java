
package br.edu.amqp.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import br.edu.amqp.experiments.PriorityProducer;
import br.edu.amqp.experiments.PriorityConsumer;

@Service
public class PriorityService {

    private List<String> mensagensEnviadas = new ArrayList<>();
    private List<String> mensagensRecebidas = new ArrayList<>();

    public List<String> executarProdutor() {

        PriorityProducer produtor = new PriorityProducer();

        mensagensEnviadas = produtor.enviarMensagens();

        return mensagensEnviadas;
    }

    public List<String> executarConsumidor() {

        PriorityConsumer consumidor = new PriorityConsumer();

        mensagensRecebidas = consumidor.consumirMensagens();

        return mensagensRecebidas;
    }

    public List<String> getMensagensEnviadas() {
        return mensagensEnviadas;
    }

    public List<String> getMensagensRecebidas() {
        return mensagensRecebidas;
    }
}
