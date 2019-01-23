package rabbitmq.config;

import org.springframework.amqp.core.*;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


@Configuration
public class RabbitmqConfig {
    /**
     * *（星号）：可以（只能）匹配一个单词
     * #（井号）：可以匹配多个单词（或者零个）
     */
    public static final String QUEUE_EMAIL="email";
    public static final String QUEUE_SMS="sms";
    public static final String TOPICS_EXCHANGE="exchange_topics_inform";
    public static final String TOPICS_KEY_EMAIL="#.email.#";
    public static final String TOPICS_KEY_SMS="#.sms.#";
    //声明交换机
    @Bean(TOPICS_EXCHANGE)
    public Exchange topicsExchange(){
        //durable(true)持久化，mq重启之后交换机还在
        Exchange build = ExchangeBuilder.topicExchange(TOPICS_EXCHANGE).durable(true).build();
        return build;
    }
    //声明队列
    @Bean(QUEUE_EMAIL)
    public Queue queueEmail(){
        Queue queue = new Queue(QUEUE_EMAIL);//email;
        return queue;
    }
    @Bean(QUEUE_SMS)
    public Queue queueSms(){
        Queue queue = new Queue(QUEUE_SMS);//sms;
        return queue;
    }
    //QUEUE_EMAIL队列绑定交换机
    @Bean
    public Binding exchangeBindingEmailQueue(@Qualifier(TOPICS_EXCHANGE) Exchange exchange,@Qualifier(QUEUE_EMAIL) Queue queueEmail){
        //with(TOPICS_KEY_EMAIL)表示绑定的那个roting key
        //noargs()表示绑定时候不需要指定参数
        Binding binding = BindingBuilder.bind(queueEmail).to(exchange).with(TOPICS_KEY_EMAIL).noargs();
        return binding;
    }
    //QUEUE_SMS队列绑定交换机
    @Bean
    public Binding exchangeBindingSmsQueue(@Qualifier(TOPICS_EXCHANGE) Exchange exchange,@Qualifier(QUEUE_SMS) Queue queueSms){
        //with(TOPICS_KEY_EMAIL)表示绑定的那个roting key
        //noargs()表示绑定时候不需要指定参数
        Binding binding = BindingBuilder.bind(queueSms).to(exchange).with(TOPICS_KEY_EMAIL).noargs();
        return binding;
    }
}
