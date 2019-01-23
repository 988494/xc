package rabbitmq.api;

import com.rabbitmq.client.AMQP;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

public class Product {
    private static final String QUEUE="hello";


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
            //String queue, boolean durable, boolean exclusive, boolean autoDelete, Map<String, Object
            /**
             * 声明队列，如果Rabbit中没有此队列将自动创建
             * param1:队列名称queue
             * param2:是否持久化durable
             * param3:队列是否独占此连接exclusive
             * param4:队列不再使用时是否自动删除此队列autoDelete
             * param5:队列参数Map             
             **/
            channel.queueDeclare(QUEUE,true,false,false,null);
            //String exchange, String routingKey, BasicProperties props, byte[] body
            /**
             * 消息发布方法
             * param1：Exchange的名称，如果没有指定，则使用Default Exchange
             * param2:routingKey,消息的路由Key，是用于Exchange（交换机）将消息转发到指定的消息队列
             * param3:消息包含的属性
             * param4：消息体
             * */
            channel.basicPublish("", QUEUE,null,"haha".getBytes());
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
