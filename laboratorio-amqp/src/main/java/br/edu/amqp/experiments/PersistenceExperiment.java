
package br.edu.amqp.experiments;

import jakarta.jms.Connection;
import jakarta.jms.DeliveryMode;
import jakarta.jms.Destination;
import jakarta.jms.JMSException;
import jakarta.jms.MessageProducer;
import jakarta.jms.Session;
import jakarta.jms.TextMessage;

import br.edu.amqp.config.BrokerConfig;

public class PersistenceExperiment {

    private static final String FILA = "fila.persistente";

    public void publicarPersistente(String conteudo) {

        try {
            Connection conexao = BrokerConfig.criarConexao();

            Session sessao = conexao.createSession(
                false,
                Session.AUTO_ACKNOWLEDGE
            );

            Destination destino = sessao.createQueue(FILA);

            MessageProducer produtor = sessao.createProducer(destino);

            produtor.setDeliveryMode(DeliveryMode.PERSISTENT);

            TextMessage mensagem = sessao.createTextMessage(conteudo);

            produtor.send(mensagem);

            System.out.println(
                "Mensagem persistente enviada: " + conteudo
            );

            produtor.close();
            sessao.close();
            conexao.close();

        } catch (JMSException e) {
            System.err.println(
                "Erro ao enviar mensagem persistente: " + e.getMessage()
            );
            e.printStackTrace();
        }
    }
}
