package com.service;

import com.driver.DriverManage;
import org.apache.commons.lang.StringUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.Select;

import java.lang.reflect.Array;
import java.util.List;

/**
 * @author: wql78
 * @date: 2020/10/6 13:05
 * @description:
 */
public class AutoLogin extends DriverManage {
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


    /**
     * 根据一个时间日期字符串获得日期 小时 分钟
     * @param dateTime 时间日期字符串
     */
    private String[] getDateHourAndMinutes(String dateTime) {
        int spaceIndex = dateTime.indexOf(" ");
        int hourEndIndex = dateTime.indexOf(":");
        int minutedEndIndex = dateTime.lastIndexOf(":");
        String date = dateTime.substring(0,spaceIndex);
        date = date.replaceAll("-", "/");
        String hour = dateTime.substring(spaceIndex + 1, hourEndIndex);
        String minutes = dateTime.substring(hourEndIndex+1, minutedEndIndex);
        return new String[] {date,hour,minutes};
    }

    /**
     * 进入码头发货页面
     */
    private void enterWharfPage(ChromeDriver driver) {
        By firstStepButtonEle = By.xpath("//a[text() = '" + firstButton + "']");
        WebElement firstStepButton = driver.findElement(firstStepButtonEle);
        firstStepButton.click();

        // 关闭当前窗口，并且切换到刚刚的窗口。
        switchWindow(driver, true);


        By secondStepButtonEle = By.xpath("//span[text() = '" + secondButton + "']");
        WebElement secondStepButton = driver.findElement(secondStepButtonEle);
        secondStepButton.click();

        By thirdStepButtonEle = By.cssSelector(thirdButton);
        WebElement thirdStepButton = driver.findElement(thirdStepButtonEle);
        thirdStepButton.click();
    }

    /**
     * 自动录入
     */
    private void autoInput(ChromeDriver driver) {
        List<WebElement> list = driver.findElements( By.xpath("//a[text() = '选择']"));
        for(int i = 0;i < list.size();i++) {
            WebElement wEle = list.get(i);
            wEle.click();
            // 切换窗口
            switchWindow(driver, false);



            // 新增码头发货。
            By addEle = By.cssSelector("input[value='新增']");
            WebElement addButton = driver.findElement(addEle);
            addButton.click();



            inputForm(driver , "2020-10-13 20:00:00", "水运", "江河船舶" ,"123123", "1", "12", "111", null);

            // 关闭当前录入页面，进入下个页面。
//            driver.close();
            // 测试的时候只弄一次
            break;
        }



    }


    /**
     *
     * @param dateTime 日期时间 2020-10-10 12:00:00
     */
    private  void  inputForm(ChromeDriver driver,
                            String dateTime,
                            String transportTypeText,
                            String transportToolText,
                            String transportToolNumText,
                            String containerNumText,
                            String deleverGoodText,
                            String vehiclesCountText,
                            String introText) {
        // 判断是否为空 以及去除首尾空格
        dateTime = StringUtils.isBlank(dateTime) ? "" : dateTime.trim();
        transportTypeText = StringUtils.isBlank(transportTypeText) ? "" : transportTypeText.trim();
        transportToolText = StringUtils.isBlank(transportToolText) ? "" : transportToolText.trim();
        transportToolNumText = StringUtils.isBlank(transportToolNumText) ? "" : transportToolNumText.trim();
        containerNumText = StringUtils.isBlank(containerNumText) ? "" : containerNumText.trim();
        deleverGoodText = StringUtils.isBlank(deleverGoodText) ? "" : deleverGoodText.trim();
        vehiclesCountText = StringUtils.isBlank(vehiclesCountText) ? "" : vehiclesCountText.trim();
        introText = StringUtils.isBlank(introText) ? "" : introText.trim();



        if(!dateTime.equals("")) {
            String[] datetimeArr = getDateHourAndMinutes(dateTime);
            WebElement dateInput = driver.findElement(By.cssSelector(dateInputSelector));
            dateInput.clear();
            dateInput.sendKeys(datetimeArr[0]);
            driver.findElement(By.cssSelector(hourSelectSelector)).sendKeys(datetimeArr[1]);
            driver.findElement(By.cssSelector(minutesSelectSelector)).sendKeys(datetimeArr[2]);
        }


        Select typeSelect = new Select(driver.findElement(By.cssSelector(transportTypeSelector)));
        typeSelect.selectByVisibleText(transportTypeText);
        Select toolSelect = new Select(driver.findElement(By.cssSelector(transportToolsSelector)));
        toolSelect.selectByVisibleText(transportToolText);
        driver.findElement(By.cssSelector(transportToolsNumSelector)).sendKeys(transportToolNumText);
        driver.findElement(By.cssSelector(containerNum)).sendKeys(containerNumText);
        driver.findElement(By.cssSelector(deleverGood)).sendKeys(deleverGoodText);
        driver.findElement(By.cssSelector(vehiclesCount)).sendKeys(vehiclesCountText);
        driver.findElement(By.cssSelector(intro)).sendKeys(introText);

        driver.findElement(By.xpath("//a[text() = '保存']")).click();

    }

    /**
     * 自动登录与表单录入。
     */
    public void autoLoginAndInput() {
        ChromeDriver driver = getDriver();
        driver.get(URL);

        By usernameCondition = By.cssSelector(USERNAME_ELE_SELECTOR);
        WebElement usernameEle  = driver.findElement(usernameCondition);
        inputKeywords(usernameEle, USERNAME);

        By paswCondition = By.cssSelector(PASW_ELE_SELECTOR);
        WebElement paswEle  = driver.findElement(paswCondition);
        inputKeywords(paswEle, PASSWORD);

        sleep(500);

        By loginButtonCondition = By.cssSelector(LOGIN_ELE_SEKECTOR);
        WebElement loginButton = driver.findElement(loginButtonCondition);
        loginButton.click();
        sleep(1000);
        String cookies = getCookies(driver);

        enterWharfPage(driver);

        autoInput(driver);
    }

    public static void main(String[] args) {
        AutoLogin autoLogin = new AutoLogin();
//        autoLogin.autoLogin();
    }
}
