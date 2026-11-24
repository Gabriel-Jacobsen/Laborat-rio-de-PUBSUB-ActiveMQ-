
package br.edu.amqp.experiments;

import java.util.ArrayList;
import java.util.List;

import jakarta.jms.Connection;
import jakarta.jms.JMSException;
import java.util.concurrent.CountDownLatch;
import jakarta.jms.Message;
import jakarta.jms.MessageConsumer;
import jakarta.jms.Session;
import jakarta.jms.TextMessage;
import jakarta.jms.Topic;

import br.edu.amqp.config.BrokerConfig;

public class FanoutConsumer {

    private static final String TOPICO = "topico.fanout";

    public List<String> receberMensagem(
        int numero,
        CountDownLatch assinantesProntos) {

        List<String> resultados = new ArrayList<>();

        Connection conexao = null;
        Session sessao = null;
        MessageConsumer consumidor = null;

        try {
            conexao = BrokerConfig.criarConexao();

            sessao = conexao.createSession(
                false,
                Session.AUTO_ACKNOWLEDGE
            );

            Topic topico = sessao.createTopic(TOPICO);

            consumidor = sessao.createConsumer(topico);

            conexao.start();

            assinantesProntos.countDown();

            System.out.println(
                "Assinante " + numero + " aguardando mensagem..."
            );

            Message mensagem = consumidor.receive(300_000);

            if (mensagem instanceof TextMessage texto) {

                String resultado =
                    "Assinante " + numero + " recebeu: " + texto.getText();

                resultados.add(resultado);

                System.out.println(resultado);

            } else {
                resultados.add(
                    "Assinante " + numero + " não recebeu mensagem."
                );
            }

        } catch (JMSException e) {

            assinantesProntos.countDown();

            System.err.println(
                "Erro no assinante " + numero + ": " + e.getMessage()
            );

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

        return resultados;
    }
}
