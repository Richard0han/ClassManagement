package com.classmanagement.client.utils;


import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.net.InetAddress;
import java.net.UnknownHostException;

/**
 * ClassManager
 *
 * @author Richard-J
 * @description 获取当地天气
 * @date 2019.03
 */

public class GetWeather {
    private static String weatherUrl = "http://www.baidu.com/baidu?tn=monline_3_dg&ie=utf-8&wd=济南天气";

    /**
     * description getWeather
     *
     * @param day 0代表今天，1为明天，2为后天，依次。。。
     * @return void
     */
    public static String[] getWeather(int day) {
        String[] weather = null;
        try {
            Document doc = Jsoup.connect(weatherUrl).timeout(5000).get();
            Elements a = doc.getElementsByClass("op_weather4_twoicon").get(0).getElementsByTag("a");

            int count = 0;
            for (Element element : a) {
                String quality = "";
                String current = "";
                String today = "";

                //只有当天才有实时温度
                if (!element.getElementsByClass("op_weather4_twoicon_shishi_title").isEmpty()) {
                    current = element.getElementsByClass("op_weather4_twoicon_shishi_title").text();
                }
                //空气质量
                if (!element.getElementsByClass("op_weather4_twoicon_aqi_text_today").isEmpty()) {
                    quality = element.getElementsByClass("op_weather4_twoicon_aqi_text_today").text();
                } else {
                    quality = element.getElementsByClass("op_weather4_twoicon_aqi_text").text();
                }
                //日期
                if (!element.getElementsByClass("op_weather4_twoicon_date").isEmpty()) {
                    today = element.getElementsByClass("op_weather4_twoicon_date").text();
                } else {
                    today = element.getElementsByClass("op_weather4_twoicon_date_day").text();
                }
                //风
                String wind = element.getElementsByClass("op_weather4_twoicon_wind").text();
                //天气
                String weath = element.getElementsByClass("op_weather4_twoicon_weath").text();
                //气温
                String temp = element.getElementsByClass("op_weather4_twoicon_temp").text();

                weather = new String[]{quality, current, today, wind, weath, temp};
                if (count == day) {
                    break;
                } else {
                    count++;
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return weather;
    }
}