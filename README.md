# xc
## 一、vue中传参
### 发起方
edit(pageId){
        this.$router.push({ path: '/cms/page/edit/'+pageId,query:{
            page: this.params.page,
            templateId: this.params.templateId,
            siteId: this.params.siteId}})
      },
### 返回发起方
go_back(){
        this.$router.push({
          path: '/cms/page/list', query: {
            page: this.$route.query.page,
            templateId: this.$route.query.templateId,
            siteId:this.$route.query.siteId
          }
        })
## 五、test-rabbitmq-consumer与test-rabbitmq-producer为RabbitMQ测试
api目录下为原始api操作RabbitMQ</br>
config与springboot目录下为springboot整合RabbitMQ，其中springboot整合RabbitMQ
的生产者代码在test-rabbitmq-producer项目下的测试代码中的rabbitmq.springboot.ProductEmailTopicsSpringbootTest测试类
,消费者启动test-rabbitmq-consumer项目中的ConsumerApplication类即可
### 1、RabbitMQ原始api
#### 生产者方
1.创建链接 ConnectionFactory
2.创建通道 Channel
3.创建交换机 channel.exchangeDeclare()
4.创建队列  channel.queueDeclare()
5.队列与交换机绑定 channel.queueBind
6.发送消息
#### 消费者方
1.创建链接
2.创建通道
3.创建交换机
4.创建队列
5.队列与交换机绑定
6.监听并接受消息
### 2、springboot整合RabbitMQ
配置RabbitMQ连接配置
#### 生产者方
1.创建交换机
2.创建队列
3.队列与交换机绑定
4.监听并接受消息(rabbitTemplate.convertAndSend())
#### 消费者方
由于springboot整合了RabbitMQ,消费者只需要执行一步即可
1.监听并接受消息(@RabbitListener(queues ={}))
