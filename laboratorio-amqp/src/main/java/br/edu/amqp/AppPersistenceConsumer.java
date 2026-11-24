
package br.edu.amqp;

import br.edu.amqp.experiments.PersistenceConsumer;

public class AppPersistenceConsumer {

    public static void main(String[] args) {
        PersistenceConsumer consumidor = new PersistenceConsumer();
        consumidor.receberMensagem();
    }
}
