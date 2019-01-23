package rabbitmq.api;

import com.rabbitmq.client.*;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

public class Consumer {
    private static final String QUEUE="hello";

    public static void main(String[] args) {
            ConnectionFactory connectionFactory = new ConnectionFactory();
            connectionFactory.setHost("120.79.93.16");
            connectionFactory.setPort(5672);
            connectionFactory.setUsername("guest");
            connectionFactory.setPassword("guest");
            connectionFactory.setVirtualHost("/");//rabbitmq默认虚拟机名称为“/”，虚拟机相当于一个独立的mq服务
            Connection connection = null;
            try {
                connection = connectionFactory.newConnection();
                Channel channel = connection.createChannel();
                //String queue, boolean durable, boolean exclusive, boolean autoDelete, Map<String, Object> arguments
                //声明队列
                channel.queueDeclare(QUEUE,true,false,false,null);
                //定义消费方法
                DefaultConsumer defaultConsumer = new DefaultConsumer(channel){
                    /**消费者接收消息调用此方法
                     * @param consumerTag 消费者的标签，在channel.basicConsume()去指定
                     * @param envelope 消息包的内容，可从中获取消息id，消息routingkey，交换机，消息和重传标志 (收到消息失败后是否需要重新发送)
                     * */
                    @Override
                    public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties, byte[] body) throws IOException {
                        //交换机
                        String exchange = envelope.getExchange();
                        //消息di
                        long deliveryTag = envelope.getDeliveryTag();
                        //消息类容
                        String message = new String(body, "utf-8");
                        System.out.println(message);
                    }
                };
                /**
                 * 监听队列String queue, boolean autoAck,Consumer callback
                 * 参数明细
                 * 1、队列名称
                 * 2、是否自动回复，设置为true为表示消息接收到自动向mq回复接收到了，mq接收到回复会删除消息，设置 为false则需要手动回复
                 * 3、消费消息的方法，消费者接收到消息后调用此方法
                 * */
                channel.basicConsume(QUEUE,true,defaultConsumer);
            } catch (IOException e) {
                e.printStackTrace();
            } catch (TimeoutException e) {
                e.printStackTrace();
            }
        }
}
