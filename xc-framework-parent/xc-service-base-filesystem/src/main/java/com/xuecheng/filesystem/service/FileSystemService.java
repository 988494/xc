package com.xuecheng.filesystem.service;

import com.alibaba.fastjson.JSON;
import com.xuecheng.filesystem.dao.FileSystemRepository;
import com.xuecheng.framework.domain.filesystem.FileSystem;
import com.xuecheng.framework.domain.filesystem.response.FileSystemCode;
import com.xuecheng.framework.domain.filesystem.response.UploadFileResult;
import com.xuecheng.framework.exception.ExceptionCast;
import com.xuecheng.framework.model.response.CommonCode;
import org.csource.fastdfs.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.util.Map;

@Service
public class FileSystemService {
    @Autowired
    private FileSystemRepository fileSystemRepository;

    @Value("${xuecheng.fastdfs.connect_timeout_in_seconds}")
    private int connect_timeout_in_seconds;
    @Value("${xuecheng.fastdfs.network_timeout_in_seconds}")
    private int network_timeout_in_seconds;
    @Value("${xuecheng.fastdfs.charset}")
    private String charset;
    @Value("${xuecheng.fastdfs.tracker_servers}")
    private String tracker_servers;


    //上传文件
    public UploadFileResult upload(MultipartFile file, String filetag, String businesskey, String metadata) {
        if(StringUtils.isEmpty(file)){
            //上传文件为null
            ExceptionCast.cast(FileSystemCode.FS_UPLOADFILE_FILEISNULL);
        }
        //上传到fastdfs
        String fileId = this.fastdfs_upload(file);
        //group1/M00/00/00/wKgB8lxQH9KAFaXkAAAMQ-jB2Tc79.file
        //group1/M00/00/00/wKgB8lxQIXeAc4x0AAAMQ-jB2Tc940.jpg
        //group1/M00/00/00/wKgB8lxQI8SAWndRAAAMQ-jB2Tc023.jpg
        if(StringUtils.isEmpty(fileId)){
            //上传文件失败
            ExceptionCast.cast(FileSystemCode.FS_UPLOADFILE_SERVERFAIL);
        }
        //把返回得fileId和其他信息储存到mongodb中
        FileSystem fileSystem = new FileSystem();
        fileSystem.setFileId(fileId);//文件id
        fileSystem.setFilePath(fileId);//文件在文件系统中的路径
        fileSystem.setFiletag(filetag);//标签
        fileSystem.setBusinesskey(businesskey);//业务标识
        if(!StringUtils.isEmpty(metadata)){
            try {
                Map map=JSON.parseObject(metadata,Map.class);
                fileSystem.setMetadata(map);//元数据
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        //名称
        fileSystem.setFileName(file.getName());
        //大小
        fileSystem.setFileSize(file.getSize());
        //文件类型
        fileSystem.setFileType(file.getContentType());
        fileSystemRepository.save(fileSystem);
        return new UploadFileResult(CommonCode.SUCCESS, fileSystem);
    }

    //上传到fastdfs
    private String fastdfs_upload(MultipartFile file){
        try {
            //初始化fastdfs环境
            initFastdfsConfig();
            //创建tracker client             
            TrackerClient trackerClient = new TrackerClient();
            //获取trackerServer             
            TrackerServer trackerServer = trackerClient.getConnection();
            //获取storage
            StorageServer storeStorage = trackerClient.getStoreStorage(trackerServer);
            //创建storage client            
            StorageClient1 storageClient1 =new StorageClient1(trackerServer,storeStorage);
//            //创建trackerClient
//            TrackerClient trackerClient = new TrackerClient();
//            TrackerServer trackerServer = trackerClient.getConnection();
//            //得到storage服务器
//            StorageServer storeStorage = trackerClient.getStoreStorage(trackerServer);
//            //创建storageClient上传文件
//            StorageClient1 storageClient1 = new StorageClient1(trackerServer, storeStorage);
            //得到文件字节
            byte[] bytes = file.getBytes();
            //得到文件的原始名称
            String name =file.getOriginalFilename();
            //得到文件扩展名
            String exe = name.substring(name.lastIndexOf(".") + 1);
            //上传文件
            //文件字节、文件扩展名、文件信息这里填写null
            String fileId = storageClient1.upload_file1(bytes, exe, null);
            return fileId;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    //初始化fastdfs环境
    private void initFastdfsConfig(){
            try {
                  ClientGlobal.initByProperties("fastdfs-client.properties");
//                ClientGlobal.initByTrackers(tracker_servers);
//                ClientGlobal.setG_charset(charset);
//                ClientGlobal.setG_network_timeout(network_timeout_in_seconds);
//                ClientGlobal.setG_connect_timeout(connect_timeout_in_seconds);
            } catch (Exception e) {
                e.printStackTrace();
                //抛出异常
                ExceptionCast.cast(FileSystemCode.FS_INIT_FASTDFS_ERROR);
            }
    }

}
