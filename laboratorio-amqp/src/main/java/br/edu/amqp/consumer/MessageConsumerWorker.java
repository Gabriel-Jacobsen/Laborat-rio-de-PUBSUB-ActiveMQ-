
package br.edu.amqp.consumer;

import jakarta.jms.Connection;
import jakarta.jms.Destination;
import jakarta.jms.JMSException;
import jakarta.jms.Message;
import jakarta.jms.MessageConsumer;
import jakarta.jms.Session;

import br.edu.amqp.config.BrokerConfig;

public class MessageConsumerWorker {

    private static final String FILA = "fila.experimento";

    public void receberMensagem() {

        try {
            Connection conexao = BrokerConfig.criarConexao();

            Session sessao = conexao.createSession(
                false,
                Session.AUTO_ACKNOWLEDGE
            );

            Destination destino = sessao.createQueue(FILA);

            MessageConsumer consumidor = sessao.createConsumer(destino);

            conexao.start();

            System.out.println("Aguardando mensagem...");

            Message mensagem = consumidor.receive(300_000);

            if (mensagem != null) {
                if (mensagem instanceof jakarta.jms.TextMessage texto) {
                    System.out.println(
                        "Mensagem recebida: " + texto.getText()
                    );
                } else {
                    System.out.println("Mensagem recebida, mas não é texto.");
                }
            } else {
                System.out.println("Nenhuma mensagem recebida no tempo limite.");
            }

            consumidor.close();
            sessao.close();
            conexao.close();

        } catch (JMSException e) {
            System.err.println("Erro ao receber mensagem: " + e.getMessage());
            e.printStackTrace();
        }
    }
}
