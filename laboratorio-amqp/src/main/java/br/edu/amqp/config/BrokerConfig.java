
package br.edu.amqp.config;

import jakarta.jms.Connection;
import jakarta.jms.ConnectionFactory;
import jakarta.jms.JMSException;

import org.apache.qpid.jms.JmsConnectionFactory;

public class BrokerConfig {

    private static final String URL =
            "amqp://localhost:5672";

    private static final String USER =
            "admin";

    private static final String PASSWORD =
            "admin";

    public static Connection criarConexao()
            throws JMSException {

        ConnectionFactory factory =
                new JmsConnectionFactory(
                        USER,
                        PASSWORD,
                        URL
                );

        return factory.createConnection();
    }
}
