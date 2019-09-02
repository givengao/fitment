package com.zxyun.user.base;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestBody;

/**
 * @des: 基础控制器
 * @Author: given
 * @Date 2019/8/4 14:13
 */
public abstract class BaseController {

    private static final Logger logger = LoggerFactory.getLogger(BaseController.class);

    @ModelAttribute
    public void beforeController (@RequestBody BaseDto baseDto) {
        baseDto.setCompanyId(1000L);
        baseDto.setGroup(1);
        baseDto.setUserId(2988L);
        baseDto.setUserName("王元美");
        logger.info("请求开始处理...");
    }
}
