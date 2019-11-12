package tutorial;

import org.apache.activemq.broker.BrokerService;
import org.apache.activemq.command.ActiveMQDestination;
import org.apache.activemq.command.ActiveMQQueue;
import org.apache.activemq.command.ActiveMQTopic;

public class EmbeddedBroker {
    BrokerService broker;

    public EmbeddedBroker() throws Exception {
        broker = new BrokerService();

        broker.setPersistent(false);
        broker.setBrokerName("localBroker");
        broker.addConnector("tcp://localhost:61616");
        broker.addConnector("stomp://localhost:61613");
        ActiveMQQueue slides = new ActiveMQQueue("TUTORIAL.SLIDES");
        ActiveMQQueue presenter = new ActiveMQQueue("TUTORIAL.PRESENTER");
        ActiveMQTopic audience = new ActiveMQTopic("TUTORIAL.AUDIENCE");
        ActiveMQDestination[] destinations = {slides, presenter, audience};
        broker.setDestinations(destinations);
    }

    public void start() throws Exception {
        this.broker.start();
    }

    public void stop() throws Exception {
        this.broker.stop();
    }
}