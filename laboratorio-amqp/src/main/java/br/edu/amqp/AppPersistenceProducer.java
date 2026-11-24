
package br.edu.amqp;

import br.edu.amqp.experiments.PersistenceExperiment;

public class AppPersistenceProducer {

    public static void main(String[] args) {

        PersistenceExperiment experimento =
            new PersistenceExperiment();

        experimento.publicarPersistente(
            "Mensagem que deve sobreviver ao reinicio do broker!"
        );
    }
}
