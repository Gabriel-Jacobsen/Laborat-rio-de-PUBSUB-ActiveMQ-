
package br.edu.amqp;

import br.edu.amqp.consumer.MessageConsumerWorker;

public class AppConsumer {

    public static void main(String[] args) {

        for (int i = 1; i <= 5; i++) {

            final int numero = i;

            Thread thread = new Thread(() -> {
                System.out.println(
                    "Consumidor " + numero + " iniciado."
                );

                MessageConsumerWorker consumidor =
                    new MessageConsumerWorker();

                consumidor.receberMensagem();

                System.out.println(
                    "Consumidor " + numero + " finalizado."
                );

            });

            thread.start();
        }
    }
}
