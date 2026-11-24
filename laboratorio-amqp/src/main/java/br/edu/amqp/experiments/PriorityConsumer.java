
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

public class PriorityConsumer {

    private static final String FILA = "fila.prioridade";

    public List<String> consumirMensagens() {

        List<String> resultados = new ArrayList<>();

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

            System.out.println("Lendo mensagens por prioridade...");

            Message mensagem;

            while ((mensagem = consumidor.receive(3000)) != null) {

                if (mensagem instanceof TextMessage texto) {

                    String resultado =
                        texto.getText() +
                        " | Prioridade: " +
                        mensagem.getJMSPriority();

                    resultados.add(resultado);

                    System.out.println("Recebida: " + resultado);
                }
            }

            System.out.println("Leitura finalizada.");

        } catch (JMSException e) {

            System.err.println(
                "Erro ao consumir mensagens: " + e.getMessage()
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
