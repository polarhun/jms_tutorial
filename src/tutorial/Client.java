package tutorial;

import org.apache.activemq.ActiveMQConnectionFactory;
import org.apache.activemq.command.ActiveMQQueue;
import org.apache.activemq.command.ActiveMQTextMessage;

import javax.jms.*;


public class Client {
    public static void main(String[] args) throws JMSException, InterruptedException {
//        Create and start connection to broker
        String url = "tcp://localhost:61616";
        ConnectionFactory connectionFactory = new ActiveMQConnectionFactory(url);
        Connection connection = connectionFactory.createConnection();
        connection.start();

//        Create a consumer, that receives and prints messages
        Destination destination = new ActiveMQQueue("TUTORIAL.PRESENTER");
        Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);

        MessageProducer producer = session.createProducer(destination);
        TextMessage message = session.createTextMessage();
        for (int i = 0; i < 10; i++) {
            message.setText("This is message " + (i + 1));
            producer.send(message);
            Thread.sleep(500);
        }

//        MessageConsumer consumer = session.createConsumer(destination);
//        while(true) {
//            System.out.println("Waiting for message.");
//            ActiveMQTextMessage message = (ActiveMQTextMessage)consumer.receive(5000);
//            if (message == null) {
//                break;
//            }
//            System.out.println("Got message: " + message.getText());
//        }

        connection.close();
    }
}
