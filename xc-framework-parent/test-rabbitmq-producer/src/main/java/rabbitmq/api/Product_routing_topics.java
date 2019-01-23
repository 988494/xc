package rabbitmq.api;

import com.rabbitmq.client.BuiltinExchangeType;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

public class Product_routing_topics {
    /**
     * *（星号）：可以（只能）匹配一个单词
     * #（井号）：可以匹配多个单词（或者零个）
     */
    private static final String QUEUE_EMAIL="email";
    private static final String QUEUE_SMS="sms";
    private static final String TOPICS_EXCHANGE="exchange_topics_inform";
    private static final String TOPICS_KEY_EMAIL="#.email.#";
    private static final String TOPICS_KEY_SMS="#.sms.#";

    public static void main(String[] args) {
        ConnectionFactory connectionFactory = new ConnectionFactory();
        connectionFactory.setHost("120.79.93.16");
        connectionFactory.setPort(5672);
        connectionFactory.setUsername("guest");
        connectionFactory.setPassword("guest");
        connectionFactory.setVirtualHost("/");//rabbitmq默认虚拟机名称为“/”，虚拟机相当于一个独立的mq服务
        Connection connection = null;
        Channel channel =null;
        try {
            connection = connectionFactory.newConnection();
            channel = connection.createChannel();
            //交换机
            //String exchange, String type
            /**参数明细
             * 1、交换机名称
             * 2、交换机类型，fanout(发布订阅)、topic、direct、headers
             * */
            channel.exchangeDeclare(TOPICS_EXCHANGE, BuiltinExchangeType.TOPIC);
            //String queue, boolean durable, boolean exclusive, boolean autoDelete, Map<String, Object
            /**
             * 声明队列，如果Rabbit中没有此队列将自动创建
             * param1:队列名称queue
             * param2:是否持久化durable
             * param3:队列是否独占此连接exclusive
             * param4:队列不再使用时是否自动删除此队列autoDelete
             * param5:队列参数Map             
             **/
             channel.queueDeclare(QUEUE_EMAIL,true,false,false,null);
             channel.queueDeclare(QUEUE_SMS,true,false,false,null);
            //交换机与队列进行绑定
            //String queue, String exchange, String routingKey
            /**
             * queue:队列名称
             * exchange:交换机的名称
             * routingKey:路由key,在发布订阅模式设置为空字符串
             */
            //要绑定多个routing key 绑定多次即可
            channel.queueBind(QUEUE_EMAIL,TOPICS_EXCHANGE,TOPICS_KEY_EMAIL);//email-->email
            channel.queueBind(QUEUE_SMS,TOPICS_EXCHANGE,TOPICS_KEY_SMS);//email-->sms
            //String exchange, String routingKey, BasicProperties props, byte[] body
            /**
             * 消息发布方法i
             * param1：Exchange的名称，如果没有指定，则使用Default Exchange
             * param2:routingKey,消息的路由Key，是用于Exchange（交换机）将消息转发到指定的消息队列,发布订阅路key设置为空字符串
             * param3:消息包含的属性
             * param4：消息体
             * */
            for(int i=0;i<5;i++){
                String message= "email"+i;
                // channel.basicPublish("", QUEUE_SMS,null,"haha".getBytes());
                channel.basicPublish(TOPICS_EXCHANGE,"email",null,message.getBytes());
                System.out.println("email is:'"+message+"'");
            }
            for(int i=0;i<5;i++){
                String message= "sms"+i;
                // channel.basicPublish("", QUEUE_SMS,null,"haha".getBytes());
                channel.basicPublish(TOPICS_EXCHANGE,"sms",null,message.getBytes());
                System.out.println("sms is:'"+message+"'");
            }
            for(int i=0;i<5;i++){
                String message= "email and sms"+i;
                // channel.basicPublish("", QUEUE_SMS,null,"haha".getBytes());
                channel.basicPublish(TOPICS_EXCHANGE,"email.sms",null,message.getBytes());
                System.out.println("email and sms is:'"+message+"'");
            }
        } catch (IOException e) {
            e.printStackTrace();
        } catch (TimeoutException e) {
            e.printStackTrace();
        } finally {
            if(channel !=null){
                try {
                    channel.close();
                } catch (IOException e) {
                    e.printStackTrace();
                } catch (TimeoutException e) {
                    e.printStackTrace();
                }
            }
            if(connection!=null){
                try {
                    connection.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
