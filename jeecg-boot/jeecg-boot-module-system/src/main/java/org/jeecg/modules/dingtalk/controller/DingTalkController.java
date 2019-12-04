package org.jeecg.modules.dingtalk.controller;

import cn.snowheart.dingtalk.robot.starter.client.DingTalkRobotClient;
import cn.snowheart.dingtalk.robot.starter.entity.DingTalkResponse;
import cn.snowheart.dingtalk.robot.starter.entity.LinkMessage;
import cn.snowheart.dingtalk.robot.starter.entity.TextMessage;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.jeecg.common.api.vo.Result;
import org.jeecg.modules.dingtalk.service.DingTalkService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author JiaoNingbo
 * @version 1.0
 * @date 2019/12/4 22:51
 */
@Slf4j
@Api(tags = "钉钉接入")
@RestController
@RequestMapping("/dingtalk")
public class DingTalkController {


    @Autowired
    private DingTalkService dingTalkService;

    @GetMapping("/test")
    @ApiOperation("测试机器人")
    public Result test() {
        TextMessage message = new TextMessage();
        message.setContent("晚安亲们");

        LinkMessage linkMessage = new LinkMessage();
        linkMessage.setMessageUrl("https://www.bilibili.com/video/av66402771?from=search&seid=7744371757282577816");
        linkMessage.setTitle("张镇辉台球教学【台球规则】");
        linkMessage.setPicUrl("https://i0.hdslb.com/bfs/archive/9c63dadd8b30aa726dd6bb756f02065f2337a451.jpg");
        linkMessage.setText("母球第一个接触的必须是你自己的目标球，你不可以先碰别人的球，所以球被挡住只有靠弹库或者跳球去解球，这里要注意一点，如果你靠弹库解球，白球弹库解到了目标球以后必须还要有任意一颗球碰库（目标球进袋不用）才行。总而言之，就是没进袋的情况下，母球接触到目标球以后必须还要有任意一球碰到库。如果你打了目标球结果没有任何球碰库，就是对方自由球");

        DingTalkResponse dingTalkResponse = dingTalkService.sendMessage(message);
        log.info("test dingtalk robot message. dingTalkResponse:{}", dingTalkResponse);
        return Result.ok(dingTalkResponse);
    }
}
