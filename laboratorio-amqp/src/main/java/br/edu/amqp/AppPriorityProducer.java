package br.edu.amqp;

import br.edu.amqp.experiments.PriorityProducer;

public class AppPriorityProducer {

    public static void main(String[] args) {

        PriorityProducer produtor = new PriorityProducer();

        produtor.enviarMensagens();
    }
}