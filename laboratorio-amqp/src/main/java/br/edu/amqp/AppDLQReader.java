
package br.edu.amqp;

import br.edu.amqp.experiments.DLQReader;

public class AppDLQReader {

    public static void main(String[] args) {

        DLQReader leitor = new DLQReader();

        leitor.lerMensagens();
    }
}
