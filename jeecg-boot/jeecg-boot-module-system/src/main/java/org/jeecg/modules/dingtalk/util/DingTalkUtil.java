package org.jeecg.modules.dingtalk.util;

import cn.snowheart.dingtalk.robot.starter.client.DingTalkRobotClient;
import cn.snowheart.dingtalk.robot.starter.entity.BaseMessage;
import org.apache.commons.codec.binary.Base64;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;

/**
 * @author JiaoNingbo
 * @version 1.0
 * @date 2019/12/4 23:07
 */
@Component
public class DingTalkUtil {

    private static final String SECRET = "SECfae68014cc63e971e7c3be3d1a659e52fe594946477040e5a6b05078f6689b69";
    private static final String MESSAGE_CHANNEL = "https://oapi.dingtalk.com/robot/send?access_token=cbbaed3d37be45ae90790624b9e20e97728adb5aeb1813c482b92af976969af9";


    public static String dingtalkSign() throws Exception {
        long timestamp = System.currentTimeMillis();
        String stringToSign = timestamp + "\n" + SECRET;
        Mac mac = Mac.getInstance("HmacSHA256");
        mac.init(new SecretKeySpec(SECRET.getBytes(StandardCharsets.UTF_8), "HmacSHA256"));
        byte[] signData = mac.doFinal(stringToSign.getBytes(StandardCharsets.UTF_8));
        return URLEncoder.encode(new String(Base64.encodeBase64(signData)), "UTF-8");
    }

    public static void sendMessage(){
        DingTalkRobotClient client = new DingTalkRobotClient();
    }

    public static void main(String[] args) throws Exception {
        System.out.println(DingTalkUtil.dingtalkSign());
    }
}
