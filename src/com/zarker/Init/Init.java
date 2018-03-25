package com.zarker.Init;

import com.zarker.util.HtmlUtil;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import resources.ResoucesUtil;
import java.io.File;
import java.io.RandomAccessFile;
import java.util.ArrayList;
import java.util.List;

/**
 * @author xuzhi
 * @createTime 2018-03-25 17:03:44
 * @description
 */
public class Init {


    public static void main(String[] args) throws InterruptedException {
        String keyword="佐佐木明希";
        List<String> alllist = new ArrayList<>();
        for(int i=1;i<=100;i++){
            List<String> list=read(keyword,i);
            if(list!=null&&list.size()>0){
                alllist.addAll(list);
            }
            System.out.print(i+"**");
//            Thread.sleep(5);
        }
        flushFile("D:\\abc.txt",alllist);


    }

    public static List<String> read(String keyword, int page){
        List<String> lists=new ArrayList<>();
        Document doc= HtmlUtil.getHtmlTextByUrl(ResoucesUtil.getResouces("url")+"thread0806.php?fid=26&search=&page="+page,1);
        Elements elements;
        elements = doc.select("table#ajaxtable").select("tr.t_one");
        String temp=null;
        for(Element element:elements){
            temp=element.select("td.tal").select("a").text();
            if(null!=temp&&temp.indexOf(keyword)!=-1){
                lists.add(ResoucesUtil.getResouces("url")+element.select("td.tal").select("a").attr("href")+"   ****    "+element.select("td.tal").select("a").text());
            }
        }
        return lists;
    }

    public static void flushFile(String fileName,List<String> list){
        try {
            File file=new File(fileName);
            if (!file.exists()) {
                if (!file.getParentFile().exists()) {
                    file.getParentFile().mkdirs();
                }
                file.createNewFile();
            }
//            打开一个随机访问文件流，按读写方式
            RandomAccessFile randomFile = new RandomAccessFile(fileName, "rw");
            // 文件长度，字节数
            long fileLength = randomFile.length();
            // 将写文件指针移到文件尾。
            randomFile.seek(fileLength);
            randomFile.write(("******************************************"+"\r\n").getBytes());
            if(list!=null&&list.size()>0){
                for(String str:list){
                    randomFile.write((str+"\r\n").getBytes());
                }
            }
            randomFile.close();
        }catch (Exception e){
            e.printStackTrace();
        }
    }
}
