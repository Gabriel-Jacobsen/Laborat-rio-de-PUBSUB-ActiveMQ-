
package br.edu.amqp.experiments;

import jakarta.jms.Connection;
import jakarta.jms.JMSException;
import jakarta.jms.MessageProducer;
import jakarta.jms.Session;
import jakarta.jms.Topic;
import jakarta.jms.TextMessage;

import br.edu.amqp.config.BrokerConfig;

public class FanoutExperiment {

    private static final String TOPICO = "topico.fanout";

    public void publicar(String conteudo) {

        try {
            Connection conexao = BrokerConfig.criarConexao();

            Session sessao = conexao.createSession(
                false,
                Session.AUTO_ACKNOWLEDGE
            );

            Topic topico = sessao.createTopic(TOPICO);

            MessageProducer produtor = sessao.createProducer(topico);

            TextMessage mensagem = sessao.createTextMessage(conteudo);

            produtor.send(mensagem);

            System.out.println("Publicada no tópico: " + conteudo);

            produtor.close();
            sessao.close();
            conexao.close();

        } catch (JMSException e) {
            System.err.println("Erro no Fan-out: " + e.getMessage());
            e.printStackTrace();
        }
    }
}
