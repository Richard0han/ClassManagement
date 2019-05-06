import com.classmanagement.client.bean.ChatInfo;
import com.classmanagement.client.bean.FrameManager;
import com.classmanagement.client.bean.User;
import com.classmanagement.client.thread.ReceiveThread;
import com.classmanagement.client.ui.ChatFrame;
import com.classmanagement.client.ui.Login;
import com.classmanagement.client.ui.MainFrame;

import java.util.LinkedHashMap;

/**
 * ClassManager
 *
 * @author Richard-J
 * @description 测试
 * @date 2019.03
 */

public class test {
    public static void main(String[] args) throws Exception {
//        new Login();
//        String cookie = LoginVerification.simulateLogin("201800301323", "a131104708");
//        BasicData basicData = new BasicData(cookie);

//        User test = JsonParser.getUser(cookie);

        User user = new User();
        user.setStuNo("201800301323");
        user.setSignature("外向的孤独患者自我拉扯");
        user.setName("贾仁叙");
        user.setNickname("哎望特·to福莱");
        user.setNetAddress("localhost");
        user.setPort(10001);
        user.setPortrait(2);
        user.setClassId(6);
        user.setClassName("计软18.3");
        FrameManager.self = user;
        User classmate = new User();
        classmate.setStuNo("201800301244");
        classmate.setSignature("这个人很懒还什么都没说呢~");
        classmate.setClassId(6);
        classmate.setName("张晴阳");
        classmate.setNickname("寒武");
        classmate.setNetAddress("localhost");
        classmate.setPort(10002);
        classmate.setPortrait(0);
        classmate.setClassName("计软18.3");

        ChatInfo chatInfo = new ChatInfo();
        chatInfo.setType(0);
        chatInfo.setSelf(user);
        chatInfo.setClassmate(classmate);
        FrameManager.whisperFrameManager = new LinkedHashMap<String, ChatFrame>();
        FrameManager.forumFrameManager = new LinkedHashMap<Integer, ChatFrame>();
//        ChatFrame chatFrame = ReceiveThread.findWin(chatInfo);
//        String weather[] = GetWeather.getWeather(0);
//        for (int i = 0; i < 5; i++) {
//            System.out.println(weather[i]);
//        }
//        System.out.println();
//        MainFrame mainUI = new MainFrame(user);
        ReceiveThread.findWin(chatInfo);
    }
}

