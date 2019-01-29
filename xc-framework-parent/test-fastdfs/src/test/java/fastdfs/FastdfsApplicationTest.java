package fastdfs;

import org.csource.common.MyException;
import org.csource.fastdfs.*;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

@SpringBootTest()
@RunWith(SpringRunner.class)
public class FastdfsApplicationTest {

    //上传测试
    @Test
    public void upload() {
        try {
            //防火强何安全组件 开放22122与23000端口
            //加载fastdfs-client.properties配置文件
            ClientGlobal.initByProperties("fastdfs-client.properties");
            //定义TrackerClient，用于请求TrackerServer
            TrackerClient trackerClient = new TrackerClient();
            //连接tracker
            TrackerServer trackerServer = trackerClient.getConnection();
            //获取Stroage
            StorageServer storeStorage = trackerClient.getStoreStorage(trackerServer);
            //创建stroageClient
            StorageClient1 storageClient1 = new StorageClient1(trackerServer,storeStorage);
            //向stroage服务器上传文件
            //本地文件的路径
            String filePath = "C:\\Users\\yang\\Desktop\\b.jpg";
            //上传成功后拿到文件Id
            String fileId = storageClient1.upload_file1(filePath, "jpg", null);
            System.out.println(fileId);
            //group1/M00/00/00/wKgB8lxPDWyAD74HAALq7rovt38293.jpg

        } catch (Exception e) {
            e.printStackTrace();
}

    }

    //下载测试
    @Test
    public void down() {
        try {
            //加载fastdfs-client.properties配置文件
            ClientGlobal.initByProperties("fastdfs-client.properties");
            //定义TrackerClient，用于请求TrackerServer
            TrackerClient trackerClient = new TrackerClient();
            //连接tracker
            TrackerServer trackerServer = trackerClient.getConnection();
            //获取Stroage
            StorageServer storeStorage = trackerClient.getStoreStorage(trackerServer);
            //创建stroageClient
            StorageClient1 storageClient1 = new StorageClient1(trackerServer,storeStorage);
            //下载文件
            //文件id
            String fileId = "group1/M00/00/00/wKgB8lxO7GyAUXyaAALq7rovt38299.jpg";
            byte[] bytes = storageClient1.download_file1(fileId);
            //使用输出流保存文件
            FileOutputStream fileOutputStream = new FileOutputStream(new File("C:\\Users\\yang\\Desktop\\ff.jpg"));
            fileOutputStream.write(bytes);
        } catch (IOException e) {
            e.printStackTrace();
        } catch (MyException e) {
            e.printStackTrace();
        }
    }

    //查询文件
    @Test
    public void find() {
        try {
            //加载fastdfs-client.properties配置文件
            ClientGlobal.initByProperties("fastdfs-client.properties");
            //定义TrackerClient，用于请求TrackerServer
            TrackerClient trackerClient = new TrackerClient();
            //连接tracker
            TrackerServer trackerServer = trackerClient.getConnection();
            //获取Stroage
            StorageServer storeStorage = trackerClient.getStoreStorage(trackerServer);
            //创建stroageClient
            StorageClient1 storageClient1 = new StorageClient1(trackerServer,storeStorage);
            FileInfo fileInfo = storageClient1.query_file_info1("group1/M00/00/00/wKgB8lxMziKALVE4AALq7rovt38612.jpg");
            System.out.println(fileInfo);
            //source_ip_addr = 192.168.1.242, file_size = 191214, create_timestamp = 2019-01-27 05:16:18, crc32 = -1171277953
        } catch (IOException e) {
            e.printStackTrace();
        } catch (MyException e) {
            e.printStackTrace();
        }
    }
}