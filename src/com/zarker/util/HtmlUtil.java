package com.zarker.util;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import resources.ResoucesUtil;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * @author xuzhi
 * @createTime 2018-03-04 20:25:31
 * @description
 */
public class HtmlUtil {

    static {
        //设置代理
        System.getProperties().setProperty("http.proxyHost", "127.0.0.1");
        System.getProperties().setProperty("http.proxyPort", "1080");
    }

    public static void main(String[] args){

    }

    /**
     * 通过URL获取网页源代码
     * 1：使用cookies 0：不使用cookies
     * @param url
     * @param type
     * @return
     */
    public static Document getHtmlTextByUrl(String url, int type) {
        Document doc = null;
        try {
            int i = (int) (Math.random() * 1000); //做一个随机延时，防止网站屏蔽
            while (i != 0) {
                i--;
            }
            Map<String, String> inmap = new HashMap<>();
            inmap.put("Accept-Language", "zh-CN,zh;q=0.9");
            inmap.put("User-Agent", "Mozilla/5.0 (Windows NT 10.0; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/51.0.2704.106 Safari/537.36");
            inmap.put("X-Forwarded-For", IpUtils.getRandomIp());
            inmap.put("referer", "http://t66y.com");
            if (type == 1) {
                inmap.put("Cookie", ResoucesUtil.getResouces("cookies"));
            }
            inmap.put("Content-Type", "multipart/form-data; session_language=cn_CN");
            doc = Jsoup.connect(url).headers(inmap)
                    .timeout(300000).post();
            return doc;
        } catch (IOException e) {
            e.printStackTrace();
            try {
                doc = Jsoup.connect(url).timeout(5000000).get();
            } catch (IOException e1) {
                return doc;
            }
        }
        return null;
    }
}
