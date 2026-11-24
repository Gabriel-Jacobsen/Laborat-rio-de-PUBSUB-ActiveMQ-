
package br.edu.amqp;

import java.util.concurrent.CountDownLatch;

import br.edu.amqp.experiments.FanoutConsumer;

public class AppFanoutConsumer {

    public static void main(String[] args) {

        CountDownLatch assinantesProntos = new CountDownLatch(3);

        for (int i = 1; i <= 3; i++) {

            final int numero = i;

            new Thread(() -> {

                FanoutConsumer consumidor = new FanoutConsumer();

                consumidor.receberMensagem(
                    numero,
                    assinantesProntos
                );

            }).start();
        }
    }
}
