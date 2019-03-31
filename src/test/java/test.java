import com.classmanagement.client.bean.User;
import com.classmanagement.client.ui.MainPanel;
import com.classmanagement.client.utils.JsonParser;
import com.classmanagement.client.utils.LoginVerification;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;

/**
 * ClassManager
 *
 * @author Richard-J
 * @description 测试
 * @date 2019.03
 */

public class test {
    public static void main(String[] args) throws Exception {
//        String cookie = LoginVerification.simulateLogin("201800301323", "a131104708");
//        BasicData basicData = new BasicData(cookie);

        User user = new User();
//        = JsonParser.getUser(cookie);
        user.setSignature("wokidhakhshddksjhvkjdkvbkdavdfhbvkhkjddjkjdkvjkdsbjhdkfhdsafoihkfsdklfbdsjahfkjdbskgsdkli");
        user.setNickname("文化的口水都快三十大家看法和深刻搭街坊");
        user.setPortrait(0);
        TestWeather.getWeather();
//        MainPanel mainPanel = new MainPanel(user);
//        System.out.println(11);
    }
}

class TestWeather {
    private static String weatherUrl = "http://www.baidu.com/baidu?tn=monline_3_dg&ie=utf-8&wd=%E5%8C%97%E4%BA%AC%E5%A4%A9%E6%B0%94";

    public static void getWeather() {
//            String userAgent = UserAgentUti.getUserAgents();
        try {
            Document doc = Jsoup.connect(weatherUrl).timeout(5000).get();
            Elements a = doc.getElementsByClass("op_weather4_twoicon").get(0).getElementsByTag("a");

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

                System.out.println(quality);
                System.out.println(current);
                System.out.println(today);
                System.out.println(wind);
                System.out.println(weath);
                System.out.println(temp);
                System.out.println("=============================");
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
