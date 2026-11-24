
package br.edu.amqp.experiments;

import java.util.ArrayList;
import java.util.List;

import jakarta.jms.Connection;
import jakarta.jms.DeliveryMode;
import jakarta.jms.JMSException;
import jakarta.jms.MessageProducer;
import jakarta.jms.Session;
import jakarta.jms.TextMessage;

import br.edu.amqp.config.BrokerConfig;

public class PriorityProducer {

    private static final String FILA = "fila.prioridade";

    public List<String> enviarMensagens() {

        List<String> enviadas = new ArrayList<>();

        try {
            Connection conexao = BrokerConfig.criarConexao();

            Session sessao = conexao.createSession(
                false, Session.AUTO_ACKNOWLEDGE
            );

            MessageProducer produtor =
                sessao.createProducer(sessao.createQueue(FILA));

            produtor.setDeliveryMode(DeliveryMode.NON_PERSISTENT);

            enviar(produtor, sessao, enviadas, "Prioridade baixa", 1);
            enviar(produtor, sessao, enviadas, "Prioridade normal", 4);
            enviar(produtor, sessao, enviadas, "Prioridade alta", 9);
            enviar(produtor, sessao, enviadas, "Prioridade média", 6);
            enviar(produtor, sessao, enviadas, "Prioridade máxima", 9);

            produtor.close();
            sessao.close();
            conexao.close();

        } catch (JMSException e) {
            System.err.println("Erro ao enviar mensagens: " + e.getMessage());
            e.printStackTrace();
        }

        return enviadas;
    }

    private void enviar(
        MessageProducer produtor,
        Session sessao,
        List<String> enviadas,
        String conteudo,
        int prioridade
    ) throws JMSException {

        TextMessage mensagem = sessao.createTextMessage(conteudo);

        produtor.send(
            mensagem,
            DeliveryMode.NON_PERSISTENT,
            prioridade,
            0
        );

        String resultado =
            conteudo + " | Prioridade: " + prioridade;

        enviadas.add(resultado);

        System.out.println("Enviada: " + resultado);
    }
}
