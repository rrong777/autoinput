package com.utils;

import com.driver.DriverManage;
import com.service.goods.DeliveryService;
import org.openqa.selenium.chrome.ChromeDriver;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class InputUtils extends DriverManage{
    private static final Logger log = LoggerFactory.getLogger(DriverManage.class);
    // 请求url
    private static final String URL = "http://apq.customs.gov.cn/_forms/" +
            "default.aspx?ReturnUrl=%2f_layouts%2f15%2fAuthenticate.aspx%3f" +
            "Source%3d%252FSitePages%252FHome%252Easpx&Source=%2FSitePages%2FHome.aspx";
    // 用户名表单元素选择器
    private static final String USERNAME_ELE_SELECTOR = "#ctl00_PlaceHolderMain_signInControl_UserName";
    // 密码表单元素选择器
    private static final String PASW_ELE_SELECTOR = "#ctl00_PlaceHolderMain_signInControl_password";

    private static final String LOGIN_ELE_SEKECTOR = "#ctl00_PlaceHolderMain_signInControl_login";

    // 用户名
    private static final String USERNAME = "390100k001";
    // 密码
    private static final String PASSWORD = "Hailong0102";

    // 自动录入步骤
    private static final String firstButton = "进境粮食管理系统";
    private static final String secondButton = "码头企业";
    private static final String thirdButton = "#zz8_RootAspMenu > li:nth-child(1) > ul > li:nth-child(2)";

    // 第一个表单的输入框
    private static final String dateInputSelector = "table[onclientclick] > tbody > tr:last-child > td:nth-child(2) > input";
    // 小时选择器
    private static final String hourSelectSelector = "table[onclientclick] > tbody > tr:last-child > td:nth-child(2) > select:nth-child(2)";
    // 分钟选择器
    private static final String minutesSelectSelector = "table[onclientclick] > tbody > tr:last-child > td:nth-child(2) > select:nth-child(3)";
    // 运输方式
    private static final String transportTypeSelector = "table[onclientclick] > tbody > tr:last-child > td:nth-child(3) > select";
    // 运输工具
    private static final String transportToolsSelector = "table[onclientclick] > tbody > tr:last-child > td:nth-child(4) > select";
    // 运输工具号码
    private static final String transportToolsNumSelector = "table[onclientclick] > tbody > tr:last-child > td:nth-child(5) > input";
    // 集装箱号
    private static final String containerNum = "table[onclientclick] > tbody > tr:last-child > td:nth-child(6) > input";
    // 发货重量
    private static final String deleverGood = "table[onclientclick] > tbody > tr:last-child > td:nth-child(7) > input";
    // 配载数量
    private static final String vehiclesCount = "table[onclientclick] > tbody > tr:last-child > td:nth-child(8) > input";
    // 发货说明
    private static final String intro = "table[onclientclick] > tbody > tr:last-child > td:nth-child(9) > input";

    private DeliveryService deliveryService;
    private ChromeDriver driver;

    public void autoInput() {
        this.driver = getDriver();

    }
}
