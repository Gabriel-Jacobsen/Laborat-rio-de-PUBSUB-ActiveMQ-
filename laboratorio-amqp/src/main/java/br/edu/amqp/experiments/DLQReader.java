
package br.edu.amqp.experiments;

import java.util.ArrayList;
import java.util.List;

import jakarta.jms.Connection;
import jakarta.jms.JMSException;
import jakarta.jms.Message;
import jakarta.jms.MessageConsumer;
import jakarta.jms.Session;
import jakarta.jms.TextMessage;

import br.edu.amqp.config.BrokerConfig;

public class DLQReader {

    private static final String FILA = "DLQ";

    public List<String> lerMensagens() {

        List<String> mensagens = new ArrayList<>();

        Connection conexao = null;
        Session sessao = null;
        MessageConsumer consumidor = null;

        try {
            conexao = BrokerConfig.criarConexao();

            sessao = conexao.createSession(
                false, Session.AUTO_ACKNOWLEDGE
            );

            consumidor = sessao.createConsumer(
                sessao.createQueue(FILA)
            );

            conexao.start();

            Message mensagem;

            while ((mensagem = consumidor.receive(3000)) != null) {

                if (mensagem instanceof TextMessage texto) {
                    mensagens.add(texto.getText());
                }
            }

        } catch (JMSException e) {
            throw new RuntimeException("Erro ao ler DLQ: " + e.getMessage(), e);

        } finally {
            try {
                if (consumidor != null) consumidor.close();
                if (sessao != null) sessao.close();
                if (conexao != null) conexao.close();
            } catch (JMSException e) {
                e.printStackTrace();
            }
        }

        return mensagens;
    }
}
