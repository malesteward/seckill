package com.bjpowernode.tool;/*
 *ClassName:FastFDS
 *Package:${PACKAGE_BANE}
 *Descripion
 *@date:2018/12/8 21:51
 *@author:tang@qq.com
 */

import org.csource.common.MyException;
import org.csource.fastdfs.*;

import java.io.IOException;

public class FastFDS {
    public static final String CONF_FILE = "fastdfs-client.conf";
    public static TrackerServer trackerServer = null;
    public static StorageServer storageServer = null;



    public static StorageClient getStroageClient() {
        StorageClient storageClient = null;
        try {
            ClientGlobal.init(CONF_FILE);
            TrackerClient trackerClient = new TrackerClient();
            trackerServer = trackerClient.getConnection();
            storageServer = trackerClient.getStoreStorage(trackerServer);
            storageClient = new StorageClient(trackerServer, storageServer);

        } catch (IOException e) {
            e.printStackTrace();
        } catch (MyException e) {
            e.printStackTrace();
        }
        return storageClient;
    }

    // 正常关闭流
    public static void closeFastDfs() {
        if (trackerServer != null) {
            try {
                trackerServer.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        if (storageServer != null) {
            try {
                storageServer.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    //byte[] fileBytes, String suffix
    /*上传文件*/
    public static  String[] uploadFile(byte[] fileBytes, String extFileName){
        String[] arry = null;
        try {
            arry = getStroageClient().upload_file(fileBytes,extFileName,null);
        } catch (IOException e) {
            e.printStackTrace();
        } catch (MyException e) {
            e.printStackTrace();
        }finally {
            closeFastDfs();
        }
        return arry;
    }
    /*文件下载
    * */
    public static  byte[] downloadFile(String group, String remoteFile) {
        byte[] fileBytes = null;
        try {
            fileBytes = getStroageClient().download_file(group,remoteFile);
        } catch (IOException e) {
            e.printStackTrace();
        } catch (MyException e) {
            e.printStackTrace();
        }finally {
            closeFastDfs();
        }
        return fileBytes;
    }

    /**
     * 文件删除
     *
     */
    public static int deleteFile(String group, String remoteFile) {
        int delete = -1;
        try {
            //返回值为0表示删除成功，其他值都是失败 -23 -9这些都是失败
            delete = getStroageClient().delete_file(group, remoteFile);
            System.out.println("文件删除：" + delete);
        } catch (IOException e) {
            e.printStackTrace();
        } catch (MyException e) {
            e.printStackTrace();
        } finally {
            closeFastDfs();
        }
        return delete;
    }
}
