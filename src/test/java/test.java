import com.classmanagement.client.bean.User;
import com.classmanagement.client.ui.BasicData;
import com.classmanagement.client.ui.Login;
import com.classmanagement.client.ui.MainPanel;
import com.classmanagement.client.utils.GetWeather;
import com.classmanagement.client.utils.JsonParser;
import com.classmanagement.client.utils.LoginVerification;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.Calendar;
import java.util.Random;

/**
 * ClassManager
 *
 * @author Richard-J
 * @description 测试
 * @date 2019.03
 */

public class test {
    public static void main(String[] args) throws Exception {
        new Login();
//        String cookie = LoginVerification.simulateLogin("201800301323", "a131104708");
////        BasicData basicData = new BasicData(cookie);
//
//        User user = new User();
//        user = JsonParser.getUser(cookie);
//        user.setSignature("外向的孤独患者自我拉扯");
//        user.setNickname("哎望特·to福莱");
//        user.setPortrait(2);
//        user.setClassId(6);
//        String weather[] = GetWeather.getWeather(0);
////        for (int i = 0; i < 5; i++) {
////            System.out.println(weather[i]);
////        }
////        System.out.println();
////        new BasicData(cookie);
//        MainPanel mainPanel = new MainPanel(user);
    }
}

