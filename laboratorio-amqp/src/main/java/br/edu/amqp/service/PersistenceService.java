
package br.edu.amqp.service;

import java.util.List;

import org.springframework.stereotype.Service;

import br.edu.amqp.experiments.PersistenceExperiment;
import br.edu.amqp.experiments.PersistenceConsumer;

@Service
public class PersistenceService {

    private final PersistenceExperiment experimento =
            new PersistenceExperiment();

    private final PersistenceConsumer consumidor =
            new PersistenceConsumer();

    public void enviarMensagem(String conteudo) {
        experimento.publicarPersistente(conteudo);
    }

    public List<String> receberMensagem() {
        return consumidor.receberMensagem();
    }
}
