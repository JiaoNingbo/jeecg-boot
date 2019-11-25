package org.jeecg.common.util;

import cn.hutool.core.date.DateUtil;

/**
 * @author JiaoNingbo
 * @version 1.0
 * @date 2019/11/23 21:00
 */
public class OssUtil {

    /**
     * 根据时间分片 https://jiaoningbo.oss-cn-beijing.aliyuncs.com/jeecg/2019/32/test.sql
     * @return
     */
    public static final String splitWeekOfYear() {
        return "jeecg/" + DateUtil.thisYear() + "/" + DateUtil.thisWeekOfYear() + "/";
    }
}
