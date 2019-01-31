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
## 三、linux安装fastdfs+nginx+fastdfs-nginx-module
### 3.1 安装nginx需要的依赖
yum -y install gcc</br>
yum -y install gcc-c++</br>
yum -y install openssl openssl-devel</br>
yum -y install zlib-devel</br>
yum -y install pcre-devel</br>
yum install -y unzip zip</br>
### 3.2 安装libevent 
FastDFS依赖libevent库，需要安装 </br>
yum -y install libevent </br>
### 3.3 安装libfastcommon 
 libfastcommon 是 FastDFS 官方提供的，libfastcommon 包含了 FastDFS 运行所需要的一些基础库。 这里演示版本是libfastcommonV1.0.7.tar.gz</br>
 下载地址：https://sourceforge.net/projects/libfastcommon/files/latest/download</br>
将libfastcommonV1.0.7.tar.gz拷贝至/root下 </br>
cd /root</br>
tar -zxvf libfastcommonV1.0.7.tar.gz </br>
cd libfastcommon-1.0.7 </br>
./make.sh </br>
./make.sh install </br>
#### 注意：libfastcommon安装好后会自动将库文件拷贝至/usr/lib64下，由于FastDFS程序引用usr/lib目录所以需要将/usr/lib64下的库文件拷贝至/usr/lib下。
cp /usr/lib64/* /usr/lib  </br>
### 3.4 安装 FastDFS
下载地址：https://github.com/happyfish100/FastDFS,</br>
然后上传到linux中，我这里放在/root目录下,这里演示用的版本是FastDFS_v5.05.tar.gz</br>
将FastDFS_v5.05.tar.gz拷贝至/usr/local/下 </br>
tar -zxvf FastDFS_v5.05.tar.gz </br>
cd FastDFS </br>
./make.sh 编译 </br>
./make.sh install  安装 </br>
装成功后，将安装目录下的conf下的文件拷贝到/etc/fdfs/下</br>
cd conf/</br>
cp * /etc/fdfs</br>

#### 目录说明
编译安装完成后</br>
cd ./FastDFS/config 目录下有一个文件，则成功了</br>
anti-steal.jpg  </br>
client.conf  </br>
http.conf  </br>
mime.types  </br>
storage.conf -->storage server启动的配置文件</br>
storage_ids.conf  </br>
tracker.conf -->tracker server启动的配置文件</br>
### 3.5 配置tracker.conf、storage.conf (这里说下：tracker与storage的)
mkdir -p /home/fastdfs/tracker
mkdir -p /home/fastdfs/storage
mkdir -p /home/fastdfs/client
mkdir -p /home/fastdfs/storage_data
mkdir -p /home/fastdfs/fastdfs-nginx-module
把这个几个目录新建起来，下面会用到

#### tracker.conf
cd /etc/fdfs/</br>
vim tracker.conf</br>
base_path=/home/fastdfs/tracker</br>

#### storage.conf 
cd /etc/fdfs/</br>
vim storage.conf </br>
group_name=group1 </br>
base_path=/home/fastdfs/storage（日志）</br>
store_path0=/home/fastdfs/storage_data（文件） </br>
#如果有多个挂载磁盘则定义多个store_path，如下 </br>
#store_path1=..... </br>
#store_path2=...... </br>
tracker_server=192.168.101.3:22122   #配置 tracker服务器:IP </br>
#如果有多个则配置多个tracker </br>
tracker_server=192.168.101.4:22122 </br>
#配置http端口 </br>
http.server_port=80（一定要是要是与niginx监听的端口号相同）</br>
等等......其他自行百度
### 3.6 启动tracker、storage-->(可是写成脚本运行，看个人喜好，演示就用命令好了)
/usr/bin/fdfs_trackerd ./tracker.conf restart</br>
/usr/bin/fdfs_storaged ./storage.conf restart</br>
### 3.7 代码测试
### 3.8 FastDFS-nginx-module
将 FastDFS-nginx-module_v1.16.tar.gz 传至 fastDFS 的 storage 服务器的</br>
/root下，执行如下命令： </br>
cd /root</br>
tar -zxvf FastDFS-nginx-module_v1.16.tar.gz </br>
cd FastDFS-nginx-module/src</br> 
修改src/config文件,将/usr/local/路径改为/usr/</br> 
修改 mod_FastDFS.conf </br>
vi /root/FastDFS-nginx-module/src/mod_FastDFS.conf </br>
base_path=/home/fastdfs/fastdfs-nginx-module（日志路径）</br>
tracker_server=192.168.101.3:22122 </br>
tracker_server=192.168.101.4:22122 </br>
url_have_group_name=true  #url中包含group名称 </br>
store_path0=/home/fastdfs/storage_data(定文件存储路径，数据data的父路径) </br>
#如果有多个 </br>
将FastDFS-nginx-module/src下的mod_FastDFS.conf拷贝至/etc/fdfs/下 </br>
cp mod_FastDFS.conf /etc/fdfs/ </br>
将libfdfsclient.so拷贝至/usr/lib下 </br>
cp /usr/lib64/libfdfsclient.so /usr/lib/ </br>
### 3.8 安装nginx
wget http://nginx.org/download/nginx-1.15.7.tar.gz</br>
解压nginx-1.15.7.tar.gz </br>
进入nginx-1.15.7目录，执行如下配置命令： </br>
测试代码看test-fastdfs这个项目</br>
./configure --prefix=/usr/local/nginx --add-module=/root/FastDFS-nginx-module/src </br>
make </br>
make install </br>
这里是把nginx源码安装到/usr/local/nginx（--prefix=/usr/local/nginx），所有配置nginx.conf是在/usr/local/nginx/conf/nginx.conf,
而不是这里的源文件，把源文件删除，防止弄糊涂
rm -rf nginx-1.15.7
### 3.9 启动nginx 这里就不说了
vim /etc/profile</br>
export PATH=$PATH:/usr/local/nginx/sbin</br>
source /etc/profile
nginx -v

## 四、另一种安装方式：docker安装fastdfs
### 4.1、说明
这里fastdfs的tracker、storage是用docker安装</br>
### 4.2、安装并启动tracker
docker run -dti --network=host --name tracker -v /var/fdfs/tracker:/var/fdfs delron/fastdfs tracker
### 4.3、安装并启动storage 
docker run -dti --network=host --name storage -e TRACKER_SERVER=10.211.55.5:22122 -v /var/fdfs/storage:/var/fdfs delron/fastdfs storage
### 4.4、安装nginx+FastDFS-nginx-module模块，看3.8-3.9即可
### 查看tracker与storage是否正常启动
docker ps
### docker 安装fastdfs需要注意的
1、需要开放防火强端口22122、23000（storage server 挂在或者配置文件中默认设置的23000端口）</br>
2、如果是阿里云。则还需要加入安全组件，放行端口22122、23000（storage server 挂在或者配置文件中默认设置的23000端口）
## 五、eureka
## 六 ribbon
## feign
### Feign工作原理如下： 
1、 启动类添加@EnableFeignClients注解，Spring会扫描标记了@FeignClient注解的接口，并生成此接口的代理 对象</br>
2、 @FeignClient(value = XcServiceList.XC_SERVICE_MANAGE_CMS)即指定了cms的服务名称，Feign会从注册中 心获取cms服务列表，并通过负载均衡算法进行服务调用</br>
3、在接口方法 中使用注解@GetMapping("/cms/page/get/{id}")，指定调用的url，Feign将根据url进行远程调用</br>
### Feign注意点 
SpringCloud对Feign进行了增强兼容了SpringMVC的注解 ，我们在使用SpringMVC的注解时需要注意： </br>
1、feignClient接口 有参数在参数必须加@PathVariable("XXX")和@RequestParam("XXX") </br>
2、feignClient返回值为复杂对象时其类型必须有无参构造函数</br>
