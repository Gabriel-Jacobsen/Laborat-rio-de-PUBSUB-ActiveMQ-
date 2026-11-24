
package br.edu.amqp;

import jakarta.jms.Connection;
import jakarta.jms.JMSException;

import br.edu.amqp.config.BrokerConfig;

public class App {

    public static void main(String[] args) {

        try {
            Connection conexao =
                    BrokerConfig.criarConexao();

            conexao.start();

            System.out.println(
                    "Conectado ao ActiveMQ via AMQP!"
            );

            conexao.close();

            System.out.println(
                    "Conexao encerrada."
            );

        } catch (JMSException e) {
            System.err.println(
                    "Erro ao conectar: " + e.getMessage()
            );

            e.printStackTrace();
        }
    }
}
