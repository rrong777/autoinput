package com.job;


import com.dao.goods.DeliveryMapper;
import com.dao.goods.DetailsParams;
import com.service.goods.DeliveryService;
import com.utils.AutomationUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @author: wql78
 * @date: 2020/10/18 12:50
 * @description:
 */
@Component
@EnableScheduling
@Configuration      //1.主要用于标记配置类，兼备Component的效果。
public class DeliveryJob {
    private final Logger logger = LoggerFactory.getLogger(DeliveryJob.class);

    @Autowired
    private DeliveryService deliveryService;

    @Value("${filepath}")
    private String filepath;
    @Value("${movepath}")
    private String movepath;
    @Autowired
    private DeliveryMapper deliveryMapper;
    // 半夜12：30跑一次 爬取发货列表

    private void crawlDelivery(String username, String password) {
        // 爬取发货列表
        long start = System.currentTimeMillis();
        AutomationUtils automationUtils = new AutomationUtils(deliveryService);
        automationUtils.crawlDeliveryList(username, password);
        long end = System.currentTimeMillis();
    }

    @Scheduled(cron = "0 0 12-20/2 * * ?")
    public void autoInput1()  {
        // 获得账号密码
        Map<String, String> map = deliveryMapper.getUserPasw();
        String username = map.get("username");
        String password = map.get("password");
        InputUtils inputUtils = new InputUtils();
        inputUtils.autoInputDetailsSingleFile(movepath,deliveryMapper,filepath, deliveryService, username, password);
        crawlDelivery(username, password);
    }

//    @Scheduled()
//    public void crawl() {
//        crawlDelivery(username, password);
//    }








}
