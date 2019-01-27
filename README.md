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
## 二、test-rabbitmq-consumer与test-rabbitmq-producer为RabbitMQ测试
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
## 三、docker安装fastdfs+nginx+fastdfs-nginx-module
### 3.1、说明
这里fastdfs的tracker、storage是用docker安装nginx+fastdfs-nginx-module是直接源码安装（为了后期改配置方便）
### 3.2、安装并启动tracker
docker run -dti --network=host --name tracker -v /var/fdfs/tracker:/var/fdfs delron/fastdfs tracker 
### 3.3、安装并启动storage
docker run -dti --network=host --name storage -e TRACKER_SERVER=10.211.55.5:22122 -v /var/fdfs/storage:/var/fdfs delron/fastdfs storage
### 3.4、安装nginx+fastdfs-nginx-module
第一步：上传nginx的源码文件并解压，很简单的，这里不说了
### 查看tracker与storage是否正常启动
docker ps
### docker 安装fastdfs需要注意的
1、需要开放防火强端口22122、23000（storage server 挂在或者配置文件中默认设置的23000端口）</br>
2、如果是阿里云。则还需要加入安全组件，放行端口22122、23000（storage server 挂在或者配置文件中默认设置的23000端口）

http://bbs.chinaunix.net/thread-4162818-1-1.html
