
package br.edu.amqp.experiments;

import jakarta.jms.Connection;
import jakarta.jms.JMSException;
import jakarta.jms.MessageProducer;
import jakarta.jms.Session;
import jakarta.jms.TextMessage;
import br.edu.amqp.config.BrokerConfig;

public class DLQProducer {

    private static final String FILA = "fila.dlq";

    public void enviarMensagem() {
        try {
            Connection conexao = BrokerConfig.criarConexao();

            Session sessao = conexao.createSession(
                false, Session.AUTO_ACKNOWLEDGE
            );

            MessageProducer produtor =
                sessao.createProducer(sessao.createQueue(FILA));

            TextMessage mensagem = sessao.createTextMessage(
                "Mensagem para testar a Dead Letter Queue!"
            );

            produtor.send(mensagem);

            System.out.println("Mensagem enviada para fila.dlq!");

            produtor.close();
            sessao.close();
            conexao.close();

        } catch (JMSException e) {
            System.err.println("Erro ao enviar mensagem: " + e.getMessage());
            e.printStackTrace();
        }
    }
}
