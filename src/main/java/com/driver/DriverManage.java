package com.driver;


import com.utils.ReflectionUtils;
import org.openqa.selenium.Cookie;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.TimeUnit;

/**
 * @author: wql78
 * @date: 2020/10/6 12:33
 * @description:
 */
public class DriverManage {
    private static final Logger logger = LoggerFactory.getLogger(DriverManage.class);
    private static ChromeOptions options;
    private static final String DRIVER_PATH;
    private static final String PATH_PREFIX = "lib" + File.separator;


    /*初始化Options*/
    static {
        options = new ChromeOptions();
        boolean headless = false;

        String pathSuffix = null;
        String osName = System.getProperties().getProperty("os.name").toLowerCase();
        if(osName.indexOf("linux") != -1) {
            headless = true;
            pathSuffix = "chromedriver_linux";
        } else {
            headless = true;
            if(osName.indexOf("mac") != -1) {
                pathSuffix = "chromedriver_mac";
            } else if(osName.indexOf("windows") != -1) {
                pathSuffix = "chromedriver.exe";
            }
        }

        DRIVER_PATH = PATH_PREFIX + pathSuffix;
        options.setHeadless(headless);
        options.setAcceptInsecureCerts(false);
        options.addArguments("no-sandbox");
        boolean imgless = false;
        // 无图
        if(imgless) {
            Map<String, Integer> map = new HashMap<>();
            map.put("profile.managed_default_content_settings.images", 2);
            options.setExperimentalOption("prefs", map);
        }
        System.setProperty("webdriver.chrome.driver", DRIVER_PATH);
    }


    protected static ChromeDriver getDriver() {
        ChromeDriver driver = new ChromeDriver(options);
        driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
        return driver;
    }
    /**
     * chromeDriver切换窗口的方法
     * close 是否关闭刚才的窗口
     */
    protected void switchWindow(ChromeDriver driver, boolean close) {

        Set<String> handles = driver.getWindowHandles();
        String handle = driver.getWindowHandle();
        for(String tempHandle : handles) {
            if (!tempHandle.equals(handle)) {
                if(close) {
                    driver.close();
                }
                driver.switchTo().window(tempHandle);
            }
        }

    }
    
    protected static String getCookies(ChromeDriver driver) {
        Set<Cookie> cookieSet = driver.manage().getCookies();
        StringBuilder sb = new StringBuilder();
        for(Cookie tmp:cookieSet) {
            sb.append(tmp.getName() + ":" + tmp.getValue() + ";");
        }
        return sb.toString();
    }

    /**
     * 模拟键盘键入
     * @param ele form表单元素
     * @param keywords 关键字
     */
    protected void inputKeywords(WebElement ele, String keywords) {
        // 转化为字符数组
        char[] chars = keywords.toCharArray();
        for(int i = 0;i < chars.length;i++) {
            ele.sendKeys(chars[i] + "");
            sleep(100);
        }
    }

    protected static void sleep(int millis) {
        try {
            Thread.sleep(millis);
        } catch (InterruptedException e) {
            logger.info("ERROR___线程休眠异常！");
        }
    }
}
