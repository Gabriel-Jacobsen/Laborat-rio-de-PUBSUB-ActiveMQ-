
package br.edu.amqp;

import br.edu.amqp.experiments.DLQProducer;
import br.edu.amqp.experiments.DLQConsumer;

public class AppDLQTest {

    public static void main(String[] args) throws InterruptedException {

        DLQProducer produtor = new DLQProducer();
        DLQConsumer consumidor = new DLQConsumer();

        // Envia uma única mensagem
        produtor.enviarMensagem();

        // Simula quatro tentativas de processamento
        for (int i = 1; i <= 4; i++) {

            System.out.println("\n===== Tentativa " + i + " =====");

            consumidor.consumirComFalha();

            Thread.sleep(1000);
        }

        System.out.println("\nExperimento de DLQ finalizado.");
    }
}
