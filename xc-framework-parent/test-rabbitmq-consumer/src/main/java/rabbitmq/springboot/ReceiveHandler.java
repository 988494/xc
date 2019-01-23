package rabbitmq.springboot;

import com.rabbitmq.client.AMQP;
import com.rabbitmq.client.Channel;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;
import rabbitmq.config.RabbitmqConfig;


@Component
public class ReceiveHandler {

    //监听QUEUE_EMAIL
    @RabbitListener(queues ={RabbitmqConfig.QUEUE_EMAIL})
    public void receiveEmail(String msg, Message message1, Channel channel){
        //msg、message都是解释消息内容 ，channel为通道
        //msg获取的内容为email and sms
        //message1获取的内容为(Body:'email and sms' MessageProperties [headers={}, contentType=text/plain, contentEncoding=UTF-8, contentLength=0, receivedDeliveryMode=PERSISTENT, priority=0, redelivered=false, receivedExchange=exchange_topics_inform, receivedRoutingKey=email.sms, deliveryTag=1, consumerTag=amq.ctag-7eLYxeZxhQdsCxEWJlGyZA, consumerQueue=email])
        System.out.println(msg);
        System.out.println(message1);
        System.out.println(channel);
    }
}
