
package br.edu.amqp;

import br.edu.amqp.producer.MessageProducer;

public class AppProducer {

    public static void main(String[] args) {

        MessageProducer produtor = new MessageProducer();

        for (int i = 1; i <= 5; i++) {

            String conteudo = "Mensagem numero " + i;

            produtor.enviarMensagem(conteudo);

            System.out.println("Envio " + i + " de 5 concluido.");

            if (i < 5) {
                try {
                    Thread.sleep(60_000);
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                    System.err.println("Envio interrompido.");
                    break;
                }
            }
        }

        System.out.println("Processo de envio finalizado.");
    }
}
