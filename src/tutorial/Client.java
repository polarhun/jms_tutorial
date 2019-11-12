package tutorial;

import org.apache.activemq.ActiveMQConnectionFactory;
import org.apache.activemq.command.ActiveMQQueue;
import org.apache.activemq.command.ActiveMQTextMessage;

import javax.jms.*;


public class Client {
    public static void main(String[] args) throws Exception {
        EmbeddedBroker broker = new EmbeddedBroker();
        broker.start();
//        Create and start connection to broker
        String url = "tcp://localhost:61616";
        ConnectionFactory connectionFactory = new ActiveMQConnectionFactory(url);
        Connection connection = connectionFactory.createConnection();
        connection.start();

        WriteTo(connection, "TUTORIAL.PRESENTER");

        ReadFrom(connection, "TUTORIAL.PRESENTER");

        connection.close();
        broker.stop();
    }

    private static void ReadFrom(Connection connection, String name) throws JMSException {
        Destination destination = new ActiveMQQueue(name);
        Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);

        MessageConsumer consumer = session.createConsumer(destination);
        while(true) {
            System.out.println("Waiting for message.");
            ActiveMQTextMessage message = (ActiveMQTextMessage)consumer.receive(5000);
            if (message == null) {
                break;
            }
            System.out.println("Got message: " + message.getText());
        }
    }

    private static void WriteTo(Connection connection, String name) throws JMSException, InterruptedException {
        Destination destination = new ActiveMQQueue(name);
        Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);

        MessageProducer producer = session.createProducer(destination);
        TextMessage message = session.createTextMessage();
        for (int i = 0; i < 10; i++) {
            message.setText("This is message " + (i + 1));
            producer.send(message);
            Thread.sleep(500);
        }
    }

}
