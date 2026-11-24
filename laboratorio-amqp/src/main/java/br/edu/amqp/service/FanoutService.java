
package br.edu.amqp.service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

import org.springframework.stereotype.Service;

import br.edu.amqp.experiments.FanoutConsumer;
import br.edu.amqp.experiments.FanoutExperiment;

@Service
public class FanoutService {

    public List<String> executarExperimento(String conteudo) {

        List<String> resultados =
                Collections.synchronizedList(new ArrayList<>());

        CountDownLatch assinantesProntos = new CountDownLatch(3);

        List<Thread> threads = new ArrayList<>();

        for (int i = 1; i <= 3; i++) {

            final int numero = i;

            Thread thread = new Thread(() -> {

                FanoutConsumer consumidor = new FanoutConsumer();

                resultados.addAll(
                    consumidor.receberMensagem(
                        numero,
                        assinantesProntos
                    )
                );
            });

            threads.add(thread);
            thread.start();
        }

        try {
            // Aguarda os três consumidores ficarem prontos.
            if (!assinantesProntos.await(10, TimeUnit.SECONDS)) {
                throw new RuntimeException(
                    "Os consumidores não foram inicializados."
                );
            }

            // Publica somente depois que os três estão inscritos.
            Thread.sleep(1000);

            FanoutExperiment produtor = new FanoutExperiment();
            produtor.publicar(conteudo);

            // Aguarda os três consumidores terminarem.
            for (Thread thread : threads) {
                thread.join();
            }

        } catch (InterruptedException e) {

            Thread.currentThread().interrupt();

            throw new RuntimeException(
                "Erro durante o experimento Fan-out.", e
            );
        }

        return resultados;
    }
}
