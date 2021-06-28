package com.job;


import com.dao.goods.DeliveryMapper;
import com.service.AutoInputService;
import com.service.goods.DeliveryService;
import com.utils.AutomationUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.Map;

/**
 * @author: wql78
 * @date: 2020/10/18 12:50
 * @description:
 */
@Component
@EnableScheduling
@Configuration      //1.主要用于标记配置类，兼备Component的效果。
public class DeliveryJob implements InitializingBean {
    private final Logger logger = LoggerFactory.getLogger(DeliveryJob.class);

    @Autowired
    private DeliveryService deliveryService;

    @Value("${filepath}")
    private String filepath;
    @Value("${movepath}")
    private String movepath;
    // 吨比例
    @Value("${tonRatio}")
    private Integer tonRatio;
    @Autowired
    private DeliveryMapper deliveryMapper;
    // 半夜12：30跑一次 爬取发货列表
    @Autowired
    private AutoInputService autoInputService;



    @Scheduled(cron = "0 0 8-20/2 * * ?")
//    @Scheduled(cron = "0 0/1 * * * ?")
    public void autoInput1()  {
        // 获得账号密码
        autoInputService.autoInput();
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        autoInput1();
    }
}
