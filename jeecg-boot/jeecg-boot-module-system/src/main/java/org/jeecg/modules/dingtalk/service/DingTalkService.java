package org.jeecg.modules.dingtalk.service;

import cn.snowheart.dingtalk.robot.starter.client.DingTalkRobotClient;
import cn.snowheart.dingtalk.robot.starter.entity.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

/**
 * 钉钉
 *
 * @author JiaoNingbo
 * @version 1.0
 * @date 2019/12/5 11:07
 */
@Slf4j
@Service
public class DingTalkService {

    @Value("${dingtalk.robot.keyword}")
    private String keyword;

    @Autowired
    private DingTalkRobotClient dingTalkRobotClient;

    public DingTalkResponse sendMessage(BaseMessage message) {
        if (message instanceof TextMessage) {
            ((TextMessage) message).setContent(((TextMessage) message).getContent() + keyword);
        }
        if (message instanceof LinkMessage) {
            ((LinkMessage) message).setText(((LinkMessage) message).getText() + keyword);
        }
        if (message instanceof MarkdownMessage) {
            ((MarkdownMessage) message).setText(((MarkdownMessage) message).getText() + keyword);
        }
        if (message instanceof ActionCardMessage) {
            ((ActionCardMessage) message).setText(((ActionCardMessage) message).getText() + keyword);
        }
        return dingTalkRobotClient.sendMessage(message);
    }

}
