import java.io.IOException;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.ConsumerCancelledException;
import com.rabbitmq.client.QueueingConsumer;
import com.rabbitmq.client.ShutdownSignalException;

public class ReceiveGeosDirectWorkQueue {

    private static final String EXCHANGE_NAME = "sg.insta";
    private static final String GEO_ROUTING_KEY = "geo";
    
    public static void main(String[] args) throws IOException, ShutdownSignalException, ConsumerCancelledException, InterruptedException {

        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost("192.168.0.20");
        Connection connection = factory.newConnection();
        Channel channel = connection.createChannel();
        
        String QUEUE_NAME = "geo_queue";
        if(args.length > 0) {
            QUEUE_NAME = args[0];
        }

        channel.exchangeDeclare(EXCHANGE_NAME, "direct");
        channel.queueDeclare(QUEUE_NAME, true, false, false, null);
        channel.queueBind(QUEUE_NAME, EXCHANGE_NAME, GEO_ROUTING_KEY);
        
        channel.basicQos(1);
        
        System.out
                .printf(" [*] Waiting for messages, queue: %s, routing: %s. To exit press CTRL+C.%n",
                        QUEUE_NAME, GEO_ROUTING_KEY);
        
        QueueingConsumer consumer = new QueueingConsumer(channel);
        channel.basicConsume(QUEUE_NAME, false, consumer);

        while (true) {
          QueueingConsumer.Delivery delivery = consumer.nextDelivery();
          String message = new String(delivery.getBody());

          System.out.println(" [x] Received '" + message + "'");   
          doWork(message);
          System.out.println(" [x] Done");

          channel.basicAck(delivery.getEnvelope().getDeliveryTag(), false);
        }
    }
    
    private static void doWork(String task) throws InterruptedException {
        for (char ch: task.toCharArray()) {
          if (ch == '.') Thread.sleep(1000);
        }
      }

}
