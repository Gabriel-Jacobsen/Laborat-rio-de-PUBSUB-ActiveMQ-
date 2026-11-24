
package br.edu.amqp;

import br.edu.amqp.experiments.FanoutExperiment;

public class AppFanoutProducer {

    public static void main(String[] args) {

        FanoutExperiment produtor = new FanoutExperiment();

        try {
            // Aguarda um pouco para permitir que os assinantes se conectem.
            Thread.sleep(5000);

            produtor.publicar("Mensagem de teste Fan-out!");

        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            System.err.println("Publicação interrompida.");
        }
    }
}
