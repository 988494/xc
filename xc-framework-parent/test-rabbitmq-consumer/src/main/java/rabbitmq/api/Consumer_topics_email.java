package rabbitmq.api;

import com.rabbitmq.client.*;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

public class Consumer_topics_email {
    /**
     * *（星号）：可以（只能）匹配一个单词
     * #（井号）：可以匹配多个单词（或者零个）
     */
    private static final String QUEUE_EMAIL="email";
    private static final String TOPICS_EXCHANGE="exchange_topics_inform";
    private static final String TOPICS_KEY_EMAIL="#.email.#";;

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
            //交换机与队列进行绑定
            //String queue, String exchange, String routingKey
            /**
             * queue:队列名称
             * exchange:交换机的名称
             * routingKey:路由key,在发布订阅模式设置为空字符串
             */
            channel.queueBind(QUEUE_EMAIL,TOPICS_EXCHANGE,TOPICS_KEY_EMAIL);//email

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
            channel.basicConsume(QUEUE_EMAIL,true,defaultConsumer);
        } catch (IOException e) {
            e.printStackTrace();
        } catch (TimeoutException e) {
            e.printStackTrace();
        }
    }
}
