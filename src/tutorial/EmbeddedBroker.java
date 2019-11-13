package tutorial;

import org.apache.activemq.broker.BrokerFactory;
import org.apache.activemq.broker.BrokerService;
import org.apache.activemq.command.ActiveMQDestination;
import org.apache.activemq.command.ActiveMQQueue;
import org.apache.activemq.command.ActiveMQTopic;

public class EmbeddedBroker {
    BrokerService broker;

    public EmbeddedBroker() throws Exception {
        broker = BrokerFactory.createBroker("xbean:activemq.xml");
    }

    public void start() throws Exception {
        this.broker.start();
    }

    public void stop() throws Exception {
        this.broker.stop();
    }
}