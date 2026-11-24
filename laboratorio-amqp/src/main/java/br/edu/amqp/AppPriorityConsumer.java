package br.edu.amqp;

import br.edu.amqp.experiments.PriorityConsumer;

public class AppPriorityConsumer {

    public static void main(String[] args) {

        PriorityConsumer consumidor = new PriorityConsumer();

        consumidor.consumirMensagens();
    }
}