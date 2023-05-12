package com.fast.core.common.util;


import cn.hutool.core.util.IdUtil;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;

public class DownLoadFileUtil {

    /**
     * 从网络Url中下载文件
     *
     * @param urlStr   url的路径
     * @param filename
     * @throws IOException
     */
    public static void downLoadByUrl(String urlStr, String savePath, String filename, String suffix) {

        try {
            if (SUtil.isEmpty(filename)) {
                filename = IdUtil.simpleUUID();
            }
            System.out.println("urlStr = " + urlStr);
            URL url = new URL(urlStr);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            //设置超时间为5秒
            conn.setConnectTimeout(5 * 1000);
            //防止屏蔽程序抓取而返回403错误
            conn.setRequestProperty("User-Agent", "Mozilla/4.0 (compatible; MSIE 5.0; Windows NT; DigExt)");
            //得到输入流 
            InputStream inputStream = conn.getInputStream();
            //获取自己数组
            byte[] getData = readInputStream(inputStream);
            //文件保存位置
            File saveDir = new File(savePath);
            if (!saveDir.exists()) { // 没有就创建该文件
                saveDir.mkdir();
            }
            File file = new File(saveDir + File.separator + filename + suffix);
            FileOutputStream fos = new FileOutputStream(file);
            fos.write(getData);

            fos.close();
            inputStream.close();
            System.out.println("the file: " + url + " download success");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 从输入流中获取字节数组
     *
     * @param inputStream
     * @return
     * @throws IOException
     */
    private static byte[] readInputStream(InputStream inputStream) throws IOException {
        byte[] buffer = new byte[4 * 1024];
        int len = 0;
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        while ((len = inputStream.read(buffer)) != -1) {
            bos.write(buffer, 0, len);
        }
        bos.close();
        return bos.toByteArray();
    }

    public static void main(String[] args) {
        try {
            String filePath = "https://fs-im-kefu.7moor-fs2.com/im/bcc5fb00-089a-11ec-8928-039370d982aa/2022-06-07_22:27:04/1654612024552/69591916.m3u8";
//            filePath = "https://docs.spring.io/spring-framework/docs/1.0.0/license.txt";
//            filePath = "https://www.w3school.com.cn/i/eg_tulip.jpg";
//            filePath = "https://www.w3school.com.cn/example/xmle/note.xml";
            downLoadByUrl(filePath,"D:\\111\\222\\","111",".mp4");

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}