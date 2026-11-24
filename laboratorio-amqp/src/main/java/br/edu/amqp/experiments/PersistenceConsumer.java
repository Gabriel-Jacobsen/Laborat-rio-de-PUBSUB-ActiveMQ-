
package br.edu.amqp.experiments;

import jakarta.jms.Connection;
import jakarta.jms.Destination;
import jakarta.jms.JMSException;
import jakarta.jms.Message;
import jakarta.jms.MessageConsumer;
import jakarta.jms.Session;
import jakarta.jms.TextMessage;

import java.util.ArrayList;
import java.util.List;

import br.edu.amqp.config.BrokerConfig;

public class PersistenceConsumer {

    private static final String FILA = "fila.persistente";

    public List<String> receberMensagem() {

        List<String> resultados = new ArrayList<>();

        Connection conexao = null;
        Session sessao = null;
        MessageConsumer consumidor = null;

        try {
            conexao = BrokerConfig.criarConexao();

            sessao = conexao.createSession(
                false, Session.AUTO_ACKNOWLEDGE
            );

            Destination destino = sessao.createQueue(FILA);
            consumidor = sessao.createConsumer(destino);

            conexao.start();

            System.out.println("Aguardando mensagem persistente...");

            Message mensagem = consumidor.receive(10_000);

            if (mensagem instanceof TextMessage texto) {

                String conteudo = texto.getText();

                resultados.add(conteudo);

                System.out.println(
                    "Mensagem recuperada: " + conteudo
                );

            } else {
                System.out.println("Nenhuma mensagem encontrada.");
            }

        } catch (JMSException e) {

            System.err.println(
                "Erro ao receber mensagem: " + e.getMessage()
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
