package rabbitmq.springboot;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import rabbitmq.config.RabbitmqConfig;

import static org.junit.Assert.*;

@SpringBootTest
@RunWith(SpringRunner.class)
public class ProductEmailTopicsSpringbootTest {

    @Autowired
    private RabbitTemplate rabbitTemplate;

    @Test
    public void sendEmail(){
        rabbitTemplate.convertAndSend(RabbitmqConfig.TOPICS_EXCHANGE,"email.sms","email and sms***************************");
    }
}