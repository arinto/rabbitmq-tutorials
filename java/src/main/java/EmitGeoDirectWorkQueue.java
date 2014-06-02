import java.io.IOException;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

public class EmitGeoDirectWorkQueue {
    
    private static final String EXCHANGE_NAME = "sg.insta";
//    private static final String QUEUE_NAME = "geo_queue";
    private static final String GEO_ROUTING_KEY = "geo";
    
    public static void main (String[] args) throws IOException {
        
        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost("192.168.0.20");
        Connection connection = factory.newConnection();
        Channel channel = connection.createChannel();

        channel.exchangeDeclare(EXCHANGE_NAME, "direct");
        
        String message = getMessage(args);
        channel.basicPublish(EXCHANGE_NAME, GEO_ROUTING_KEY, null, message.getBytes());
        System.out.println(" [x] Sent '" + GEO_ROUTING_KEY + "':'" + message + "'");

        channel.close();
        connection.close();
    }
    
    private static String getMessage(String[] strings){
        if (strings.length < 1)
          return "Hello World!";
        return joinStrings(strings, " ");
      }  
      
      private static String joinStrings(String[] strings, String delimiter) {
        int length = strings.length;
        if (length == 0) return "";
        StringBuilder words = new StringBuilder(strings[0]);
        for (int i = 1; i < length; i++) {
          words.append(delimiter).append(strings[i]);
        }
        return words.toString();
      }

}
