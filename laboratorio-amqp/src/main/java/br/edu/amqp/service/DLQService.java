
package br.edu.amqp.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import br.edu.amqp.experiments.DLQProducer;
import br.edu.amqp.experiments.DLQConsumer;
import br.edu.amqp.experiments.DLQReader;

@Service
public class DLQService {

    public List<String> executarExperimento() {

        List<String> resultados = new ArrayList<>();

        DLQProducer produtor = new DLQProducer();
        DLQConsumer consumidor = new DLQConsumer();
        DLQReader leitor = new DLQReader();

        try {
            // 1. Envia a mensagem
            produtor.enviarMensagem();

            resultados.add("Mensagem enviada para fila.dlq.");

            // 2. Simula quatro tentativas com falha
            for (int i = 1; i <= 4; i++) {

                resultados.add("Tentativa de processamento " + i);

                consumidor.consumirComFalha();

                Thread.sleep(1000);
            }

            // 3. Recupera mensagens da DLQ
            List<String> mensagens = leitor.lerMensagens();

            resultados.add("Total de mensagens recuperadas: "
                    + mensagens.size());

            for (String mensagem : mensagens) {
                resultados.add("DLQ: " + mensagem);
            }

        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            resultados.add("Experimento interrompido.");

        } catch (Exception e) {
            resultados.add("Erro no experimento: " + e.getMessage());
        }

        return resultados;
    }
}
