
package br.edu.amqp.producer;

import jakarta.jms.Connection;
import jakarta.jms.Destination;
import jakarta.jms.JMSException;
import jakarta.jms.Session;
import jakarta.jms.TextMessage;

import br.edu.amqp.config.BrokerConfig;

public class MessageProducer {

    private static final String FILA = "fila.experimento";

    public void enviarMensagem(String conteudo) {

        try {
            Connection conexao = BrokerConfig.criarConexao();

            Session sessao = conexao.createSession(
                false,
                Session.AUTO_ACKNOWLEDGE
            );

            Destination destino = sessao.createQueue(FILA);

            jakarta.jms.MessageProducer produtor =
                sessao.createProducer(destino);

            TextMessage mensagem = sessao.createTextMessage(conteudo);

            produtor.send(mensagem);

            System.out.println("Mensagem enviada: " + conteudo);

            produtor.close();
            sessao.close();
            conexao.close();

        } catch (JMSException e) {
            System.err.println("Erro ao enviar mensagem: " + e.getMessage());
            e.printStackTrace();
        }
    }
}
