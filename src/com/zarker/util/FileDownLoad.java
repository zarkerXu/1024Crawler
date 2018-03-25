package com.zarker.util;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * @author xuzhi
 * @createTime 2018-03-04 17:27:26
 * @description
 */
public class FileDownLoad {
    static {
        //设置代理
        System.getProperties().setProperty("http.proxyHost", "127.0.0.1");
        System.getProperties().setProperty("http.proxyPort", "1080");
    }

    public static void main(String[] args){
        String imgUrl="http://185.38.13.159//mp43/255772.mp4?st=AQ6ZLXSL1vY0olKG6J-uzw&e=1520064596";
//        String imgUrl="https://img3.doubanio.com/view/note/l/public/p48658804.webp";
        downloadFile(imgUrl, imgUrl, UUID.randomUUID().toString());
    }


    public static boolean downloadFile(String url,String path,String fileName){
        try {
            File file = new File(path + fileName);
            List<Object> objects = getInputStreamByGet(url);
            if (!file.exists()) {
                if (!file.getParentFile().exists()) {
                    file.getParentFile().mkdirs();
                }
                file.createNewFile();
            }
            if (objects != null&&objects.size()==2) {
                int length= (int) objects.get(0);
                InputStream inputStream= (InputStream) objects.get(1);
                saveData(inputStream, file,length);
                return true;
            }
        }catch (IOException e){
            e.printStackTrace();
        }
        return false;

    }


    public static List<Object> getInputStreamByGet(String url) {
        try {
            List<Object> obj = new ArrayList<>();
            HttpURLConnection conn = (HttpURLConnection) new URL(url).openConnection();
            conn.setReadTimeout(20000);
            conn.setConnectTimeout(20000);
            conn.setRequestProperty("User-Agent", "Mozilla/31.0 (compatible; MSIE 10.0; Windows NT; DigExt)"); //防止报403错误。
            conn.setRequestProperty("X-Forwarded-For", IpUtils.getRandomIp());
            conn.setRequestMethod("GET");
            System.out.println(conn.getResponseCode());
            if (conn.getResponseCode() == HttpURLConnection.HTTP_OK) {
                InputStream inputStream = conn.getInputStream();
                obj.add(conn.getContentLength());
                obj.add(inputStream);
                return obj;
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    // 将服务器响应的数据流存到本地文件
    public static void saveData(InputStream is, File file,int length) {
        BufferedInputStream bis = null;
        BufferedOutputStream bos = null;
        try  {
            bis = new BufferedInputStream(is);
            bos = new BufferedOutputStream(new FileOutputStream(file));
            byte[] buffer = new byte[1024];
            int len = -1;
            int count=0;
            int flag=0;
            while ((len = bis.read(buffer)) != -1) {
                bos.write(buffer, 0, len);
                bos.flush();
                count+=len;
                flag++;
                if(flag%1000==0||count==length)
                    System.out.println(file.getName()+"---->"+(count*1.0)/length*100+"%");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }finally {
            try {
                if (bis != null) {
                    bis.close();
                }
                if (bos != null) {
                    bos.close();
                }
            }catch (IOException e){
                e.printStackTrace();
            }
        }
    }
}
