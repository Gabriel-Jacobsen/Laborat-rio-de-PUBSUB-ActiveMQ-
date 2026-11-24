
package br.edu.amqp.experiments;

import jakarta.jms.Connection;
import jakarta.jms.JMSException;
import jakarta.jms.Message;
import jakarta.jms.MessageConsumer;
import jakarta.jms.Session;
import br.edu.amqp.config.BrokerConfig;

public class DLQConsumer {

    private static final String FILA = "fila.dlq";

    public void consumirComFalha() {
        Connection conexao = null;
        Session sessao = null;
        MessageConsumer consumidor = null;

        try {
            conexao = BrokerConfig.criarConexao();

            // Sessão transacionada
            sessao = conexao.createSession(
                true, Session.SESSION_TRANSACTED
            );

            consumidor = sessao.createConsumer(
                sessao.createQueue(FILA)
            );

            conexao.start();

            System.out.println("Aguardando mensagem...");

            Message mensagem = consumidor.receive(10_000);

            if (mensagem != null) {
                System.out.println("Mensagem recebida.");
                System.out.println("Simulando falha no processamento...");

                // Desfaz o recebimento e solicita reentrega
                sessao.rollback();

                System.out.println("Rollback executado.");
            } else {
                System.out.println("Nenhuma mensagem encontrada.");
            }

        } catch (JMSException e) {
            System.err.println("Erro no consumidor: " + e.getMessage());
            e.printStackTrace();

        } finally {
            try {
                if (consumidor != null) consumidor.close();
                if (sessao != null) sessao.close();
                if (conexao != null) conexao.close();
            } catch (JMSException e) {
                e.printStackTrace();
            }
        }
    }
}
