package com.utils;

import com.dao.goods.Delivery;
import com.dao.goods.DeliveryDetails;
import com.dao.goods.DetailsParams;
import com.driver.DriverManage;
import com.service.goods.DeliveryService;
import io.jsonwebtoken.lang.Collections;
import org.apache.commons.lang.StringUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.Select;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * ctrl alt O 去除多余的包（Windows）
 * commond option O 去除多余的包（mac）
 * @author: wql78
 * @date: 2020/10/6 13:05
 * @description: 自动化工具
 */
public class AutomationUtils extends DriverManage {
    private static final Logger log = LoggerFactory.getLogger(DriverManage.class);
    // 请求url
    private static final String URL = "http://apq.customs.gov.cn/SitePages/Home.aspx";
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

    private boolean loginFlag;

    public boolean isLoginFlag() {
        return loginFlag;
    }

    private ChromeDriver driver;
    private DeliveryService deliveryService;
    public AutomationUtils() {
    }

    public AutomationUtils(DeliveryService deliveryService, String username, String password) {
        this.deliveryService = deliveryService;
        this.driver = this.login();
        if(this.isLoginFlag() == true) {
            this.enterWharfPage(driver);
        }
    }



    public void setLoginFlag(boolean loginFlag) {
        this.loginFlag = loginFlag;
    }

    public AutomationUtils(DeliveryService deliveryService) {
        this.deliveryService = deliveryService;
    }
    private ChromeDriver login() {
        ChromeDriver driver = getDriver();
        try {
            driver.get(URL);
        } catch (Exception e) {
            log.info("ERROR___请求超时！！！！" + URL);
        }

        for(int i = 0; i < 12; i++) {
            try {
                Thread.sleep(5000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            String pageSource = driver.getPageSource();
            if(pageSource.indexOf("登录") == -1) {
                this.setLoginFlag(true);
                break;
            }
        }
        return driver;
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
        log.info("登录成功！！！");
    }

    /**
     * 分步骤，爬取一条发货记录的详情
     * @param driver
     * @param details
     */
    private void crawlDetails(ChromeDriver driver, List<DeliveryDetails> details) {
        // 所有的选择按钮
        List<WebElement> list = driver.findElements( By.xpath("//a[text() = '选择']"));
        for(int i = 0;i < list.size();i++) {
            WebElement wEle = list.get(i);

            wEle.click();
            // 切换窗口
            switchWindow(driver, false);

            this.getDetails(driver, details);

            // 获得详情后关闭当前页面
            switchWindow(driver, true);
        }
    }

    private void getDetails(ChromeDriver driver, List<DeliveryDetails> details){
        WebElement table = driver.findElement(By.cssSelector("table.tabtitle"));
        // 总共8行将近20个字段
        List<WebElement> rows = table.findElements(By.tagName("tr"));
        SimpleDateFormat format = new SimpleDateFormat("yyyy/MM/dd");
        DeliveryDetails deliveryDetails = new DeliveryDetails();
        try {
            for(int i = 0;i < rows.size();i++){
                // 第i行
                WebElement row = rows.get(i);
                List<WebElement> columns = row.findElements(By.tagName("td"));
                if(i == 0) {
                    for(int j = 0;j < columns.size();j++) {
                        WebElement column = columns.get(j);
                        // 报检号
                        if(j == 0) {
                            Long temp = StringUtils.isBlank(column.getText()) ? null : Long.valueOf(column.getText());
                            deliveryDetails.setCheckNum(temp);
                            // 报检日期
                        } else if(j == 1) {
                            Date tmp = StringUtils.isBlank(column.getText()) ? null : format.parse(column.getText());
                            deliveryDetails.setCheckDate(tmp);
                            // 许可证号
                        } else if(j == 2) {
                            deliveryDetails.setLicence(column.getText());
                        }
                    }
                } else if(i == 1) {
                    for(int j = 0;j < columns.size();j++) {
                        WebElement column = columns.get(j);
                        // CIQ编码
                        if(j == 0) {
                            Long temp = StringUtils.isBlank(column.getText()) ? null : Long.valueOf(column.getText());
                            deliveryDetails.setCiq(temp);
                            // 货物中文名称
                        } else if(j == 1) {
                            deliveryDetails.setGoodsChName(column.getText());
                            // 货物品种
                        } else if(j == 2) {
                            deliveryDetails.setGoodsVarieties(column.getText());
                        }
                    }
                } else if(i == 2) {
                    for(int j = 0;j < columns.size();j++) {
                        WebElement column = columns.get(j);
                        // 重量 吨
                        if(j == 0) {
                            Double temp = StringUtils.isBlank(column.getText()) ? null : Double.valueOf(column.getText());
                            deliveryDetails.setWeight(Double.valueOf(column.getText()));
                            // 原产国
                        } else if(j == 1) {
                            deliveryDetails.setOrigin(column.getText());

                        }
                    }
                } else if(i == 3) {
                    for(int j = 0;j < columns.size();j++) {
                        WebElement column = columns.get(j);
                        // 调运重量
                        if(j == 0) {
                            Double temp = StringUtils.isBlank(column.getText()) ? null : Double.valueOf(column.getText());
                            deliveryDetails.setTransportWeight(Double.valueOf(column.getText()));
                            // 发货总重量
                        } else if(j == 1) {
                            Double temp = StringUtils.isBlank(column.getText()) ? null : Double.valueOf(column.getText());
                            deliveryDetails.setDeliveryWeight(Double.valueOf(column.getText()));
                        }
                    }
                } else if(i == 4) {
                    for(int j = 0;j < columns.size();j++) {
                        WebElement column = columns.get(j);
                        // 运输方式
                        if(j == 0) {
                            deliveryDetails.setTransportType(column.getText());
                            // 运输工具
                        } else if(j == 1) {
                            deliveryDetails.setTransportTools(column.getText());
                            // 运输工具号码
                        } else if(j == 2) {

                            deliveryDetails.setTransportToolsNum(column.getText());
                        }
                    }
                } else if(i == 5) {
                    for(int j = 0;j < columns.size();j++) {
                        WebElement column = columns.get(j);
                        // 发货单位名称
                        if(j == 0) {
                            deliveryDetails.setDeliveryUnitName(column.getText());
                            // 发货单位地址
                        } else if(j == 1) {
                            deliveryDetails.setDeliveryUnitPos(column.getText());

                        }
                    }
                } else if(i == 6) {
                    for(int j = 0;j < columns.size();j++) {
                        WebElement column = columns.get(j);
                        // 接收单位名称
                        if(j == 0) {
                            deliveryDetails.setReceiveUnitName(column.getText());
                            // 接收单位地址
                        } else if(j == 1) {
                            deliveryDetails.setReceiveUnitPos(column.getText());
                        }
                    }
                } else if(i == 7) {
                    for(int j = 0;j < columns.size();j++) {
                        WebElement column = columns.get(j);
                        // 运输计划
                        if(j == 0) {
                            deliveryDetails.setTransportPlan(column.getText());
                            // 发货状态
                        } else if(j == 1) {

                            deliveryDetails.setDeliveryStatus(column.getText());
                        }
                    }
                }
            }
        } catch (ParseException e) {
//
            log.info(e.getMessage());
            log.info("ERROR___获取详情 - 解析日期格式出错！");
            log.info(e.toString());
        } catch (Exception e) {
            log.info(e.getMessage());
            log.info(e.toString());
        }
        // 无论是否出错不影响下一次循环
        details.add(deliveryDetails);
        System.out.println(deliveryDetails);
    }




    public ChromeDriver getCurrentDriver() {
        return this.driver;
    }
    /**
     * 自动登录与表单录入。
     */
    private ChromeDriver login(String username, String password) {
        if(StringUtils.isBlank(username)) {
            username = USERNAME;
        }
        if(StringUtils.isBlank(password)) {
            password = PASSWORD;
        }
        ChromeDriver driver = getDriver();
        try {
            driver.get(URL);
        } catch (Exception e) {
            log.info("ERROR___请求超时！！！！" + URL);
        }

        // 输入账号
        By usernameCondition = By.cssSelector(USERNAME_ELE_SELECTOR);
        WebElement usernameEle  = driver.findElement(usernameCondition);
        inputKeywords(usernameEle, USERNAME);

        // 输入密码
        By paswCondition = By.cssSelector(PASW_ELE_SELECTOR);
        WebElement paswEle  = driver.findElement(paswCondition);
        inputKeywords(paswEle, PASSWORD);

        sleep(500);

        // 点击登录
        By loginButtonCondition = By.cssSelector(LOGIN_ELE_SEKECTOR);
        WebElement loginButton = driver.findElement(loginButtonCondition);
        loginButton.click();
        sleep(1000);
        // 获得cookie
        String cookies = getCookies(driver);
        return driver;
    }

    /**
     * 获得本页申请单号List
     * @param driver
     * @return
     */
    private List<String> getCurrentPageApplyNumList(ChromeDriver driver) {
        WebElement table = driver.findElement(By.cssSelector("table.RepL1"));
        List<WebElement> rows = driver.findElements(By.cssSelector("table.RepL1>tbody>tr:nth-child(odd)>td>table>tbody>tr>td:nth-child(2)"));
        System.out.println(rows.size());
        // 本页所有申请单号List
        List<String> allApplyNumList = new ArrayList<>();
        for(int j = 0;j < rows.size();j++) {
            WebElement tmp = rows.get(j);
            String applyNum = tmp.getText().substring(5);
            allApplyNumList.add(applyNum);
        }
        return allApplyNumList;
    }

    /**
     * 获得本页需要自动录入的数据
     * @return
     */
    private Map<String,  List<DetailsParams>> getCurrentPageAutoInputDatas(List<String> allApplyNumList, Map<String, List<DetailsParams>> autoInputMaps) {
        // 将要自动录入的数据
        Map<String, List<DetailsParams>> willAutoInputDetails = new HashMap<>();
        List<String> removeList = new ArrayList<>();
        // 遍历需要自动录入的数据 查看是否有本页的数据需要自动录入
        for(String autoInputApplyNum : autoInputMaps.keySet()) {
            if(allApplyNumList.indexOf(autoInputApplyNum) != -1) {
                willAutoInputDetails.put(autoInputApplyNum, autoInputMaps.get(autoInputApplyNum));
                removeList.add(autoInputApplyNum);
            }
        }
        // 删除本页自动录入的数据
        for(String tmp : removeList) {
            autoInputMaps.remove(tmp);
        }
        return  willAutoInputDetails;
    }

    public void autoInput1(Map<String, List<DetailsParams>> autoInputMaps) {
        WebElement pageNumEle = null;
        Integer totalPageNum = 1;
        try {
            pageNumEle = driver.findElement(By.cssSelector(".tabfy td>span:nth-child(2)"));
        } catch (NoSuchElementException e) {
            log.info("没有页码元素，可能只有第一页！！！！");
        }
        if(pageNumEle != null) {
            totalPageNum = Integer.valueOf(pageNumEle.getText());
        }
        // 遍历所有页码，进行自动录入
        for(int i = 0;i < totalPageNum;i ++) {
            // 没有需要自动录入的数据
            if(Collections.isEmpty(autoInputMaps)) {
                break;
            }
            // 本页所有的申请单号
            List<String> allApplyNumList = this.getCurrentPageApplyNumList(driver);
            // 本页需要自动录入的申请单号
            Map<String, List<DetailsParams>> willAutoInputDetails = this.getCurrentPageAutoInputDatas(allApplyNumList, autoInputMaps);

            // 不是最后一页，且本页没有需要自动录入的
            if((i!= (totalPageNum - 1)) && Collections.isEmpty(willAutoInputDetails)) {
                driver.findElement(By.xpath("//a[text() = '下一页']")).click();
                continue;
            }
            // 获得本页所有与申请单号对应的选择按钮
            List<WebElement> list = driver.findElements( By.xpath("//a[text() = '选择']"));

            // 遍历本页需要自动录入的申请单号
            for(String aotuInputApplyNum : willAutoInputDetails.keySet()) {
                Integer index = allApplyNumList.indexOf(aotuInputApplyNum);
                if(index == -1) {
                    continue;
                }
                WebElement wEle = list.get(index);
                Actions actions = new Actions(driver);
                actions.moveToElement(wEle).click().perform();
//                wEle.click();
                // 切换窗口
                switchWindow(driver, false);
                List<DetailsParams> autoInputParams = willAutoInputDetails.get(aotuInputApplyNum);
                for(DetailsParams tmpParams : autoInputParams) {
                    // 新增码头发货。
                    By addEle = By.cssSelector("input[value='新增']");
                    WebElement addButton = driver.findElement(addEle);
                    addButton.click();

                    this.inputForm(driver, tmpParams.getDeliveryTime(), tmpParams.getVehicleNumber(),
                            tmpParams.getWeight(), tmpParams.getPcs(), tmpParams.getInspectionNumber());
                    try {
                        // 最后一个休眠五秒 因为要关闭页面
                        if(autoInputParams.get(autoInputParams.size() - 1) == tmpParams) {
                            Thread.sleep(5000);
                        } else {
                            Thread.sleep(2000);
                        }
                    } catch (Exception e) {
                        log.info("线程休眠异常！！！");
                    }
                }
                switchWindow(driver, true);
            }

            if(i != totalPageNum - 1) {
                // 下一页
                driver.findElement(By.xpath("//a[text() = '下一页']")).click();
            } else {
                // 让driver回到第一页
                driver.findElement(By.xpath("//a[text() = '首页']")).click();
            }

        }

    }

    private  void  inputForm(ChromeDriver driver,
                             String deliveryTime,
                             String noTransport,
                             Double weight,
                             Integer pcs,
                             String inspectNum) {
        deliveryTime = StringUtils.isBlank(deliveryTime) ? "" : deliveryTime.trim();
        noTransport = StringUtils.isBlank(noTransport) ? "" : noTransport.trim();
        String weightStr = "";
        if(weight != null) {
            weightStr = String.valueOf(weight);
        }
        String pcsStr = "";
        if(pcs != null) {
            pcsStr = String.valueOf(pcs);
        }

        // 填写发货时间
        if(!deliveryTime.equals("")) {
            String[] datetimeArr = DateTimeUtils.getDateHourAndMinutes(deliveryTime);
            int i = 0;
            try {
                while(i < 3) {
                    WebElement dateInput = driver.findElement(By.cssSelector(dateInputSelector));
                    dateInput.clear();
                    dateInput.sendKeys(datetimeArr[0]);
                    i = 3;// 顺利结束就出循环
                }
            } catch (Exception e) {
                i++;
                e.printStackTrace();
                log.info("输入时间出错: 第" + i + "次");
            }

            Select hourSelect = new Select(driver.findElement(By.cssSelector(hourSelectSelector)));
            hourSelect.selectByValue(datetimeArr[1] + ":");
//            hourSelect.selectByVisibleText(datetimeArr[1] + ":");
//            driver.findElement(By.cssSelector(hourSelectSelector)).sendKeys(datetimeArr[1] + ":");
            Select minutesSelect = new Select(driver.findElement(By.cssSelector(minutesSelectSelector)));
            minutesSelect.selectByValue(datetimeArr[2]);
//            minutesSelect.selectByVisibleText(datetimeArr[2]);

        }

        Select typeSelect = new Select(driver.findElement(By.cssSelector(transportTypeSelector)));
        typeSelect.selectByVisibleText("陆运");
        Select toolSelect = new Select(driver.findElement(By.cssSelector(transportToolsSelector)));
        toolSelect.selectByVisibleText("粮食专用车");
        driver.findElement(By.cssSelector(transportToolsNumSelector)).sendKeys(noTransport);
        driver.findElement(By.cssSelector(deleverGood)).sendKeys(weightStr);
        driver.findElement(By.cssSelector(vehiclesCount)).sendKeys(pcsStr);
        driver.findElement(By.xpath("//a[text() = '保存']")).click();
        deliveryService.alterStatus(inspectNum, deliveryTime, weight, noTransport);
    }


    /**
     * 从字符串中获得数字
     * @param str
     */
    public static Double getNumFromStr(String str) {
        String numReg = "[^\\d^\\.]+";
        return Double.valueOf(str.replaceAll(numReg, ""));
    }
    /**
     * 对解析列进行简单封装。col 单个表列元素
     * 表行循环次数
     * j 当前col对象位于整行位置索引
     * @param cols
     * @param i
     * @return
     */
    private void analyseCols(List<WebElement> cols, int i, Delivery delivery) throws ParseException {
        SimpleDateFormat format = new SimpleDateFormat("yyyy/MM/dd");
        for(int j = 0;j < cols.size();j++) {
            // 单一表列元素
            WebElement col = cols.get(j);
//             i % 2 == 1 两行数据中的第一行
            if(i % 3 == 1 ) {
                if(j == 1) {
                    delivery.setCheckNum(Long.valueOf(col.getText()));
                    // 报检日期
                } else if(j == 2) {
                    Date tmp = col.getText().equals("")? null : format.parse(col.getText());
                    delivery.setCheckDate(tmp);

                    // 货物名称
                } else if(j == 3){
                    delivery.setGoods(col.getText());
                    // 原产国
                } else if(j == 4) {
                    delivery.setOrigin(col.getText());
                    // 运输方式
                } else if(j == 5) {
                    delivery.setTransport(col.getText());
                    // 进口商
                } else if(j == 6) {
                    delivery.setImportBusiness(col.getText());
                    // 调运重量
                } else if(j == 7) {
                    String weightStr = col.getText();
                    delivery.setWeight(getNumFromStr(col.getText()));
                }
                // 第二行数据
            } else {
                // 申请单号
                if(j == 1) {
                    String applyNumStr = col.getText();
                    String applyNum = applyNumStr.substring(applyNumStr.indexOf("：") + 1);
                    delivery.setApplyNum(applyNum);
                    // 收货单位
                } else if(j == 2) {
                    String receiveUnitStr = col.getText();
                    String receiveUnit = receiveUnitStr.substring(receiveUnitStr.indexOf("：") + 1);
                    delivery.setReceivingUnit(receiveUnit);
                    // 货物类型
                } else if(j == 3) {
//                    getNumFromStr(col.getText());
                    String goodsTypeStr = col.getText();
                    String goodsType = goodsTypeStr.substring(goodsTypeStr.indexOf("：") + 1);
                    delivery.setGoodsType(goodsType);
                    // 已发货重量
                } else if(j == 4) {
                    delivery.setAlreadyDeliveryWeight(getNumFromStr(col.getText()));
                    // 最后发货日期
                } else if(j == 5) {
                    String finalDateStr = col.getText().substring(col.getText().indexOf("：") + 1);
                    Date tmp = finalDateStr.equals("")? null : format.parse(finalDateStr);
                    delivery.setFinalDeliveryDate(tmp);
                    // 发货状态
                } else if(j == 6) {
                    String deliveryStatusStr = col.getText();
                    String deliveryStatus = deliveryStatusStr.substring(deliveryStatusStr.indexOf("：") + 1);
                    delivery.setGoodsType(deliveryStatus);
                }
            }
        }
    }

    /**
     * 判断新增一个delivery对象还是用之前的
     * @param deliveries
     * @param i
     * @return 返回一个发货实体对象
     */
    private Delivery getDelivery(List<Delivery> deliveries, int i) {
        Delivery delivery = null;
        // 如果是第n * 3 + 1 行，就获得一个新的对象
        if(i % 3 == 1) {
            delivery = new Delivery();
            deliveries.add(delivery);
        // 如果是第n*3行 则获取最新的一个已有的对象
        } else {
            delivery = deliveries.get(deliveries.size() - 1);
        }
        return delivery;
    }

    public void crawlDeliveryList(String username, String password) {
        long beginMillis = System.currentTimeMillis();
        ChromeDriver driver = null;
        try {
            // 登录
            driver = this.login(username, password);
            // 进入发货列表
            this.enterWharfPage(driver);
            // 总页码元素
            WebElement pageNumEle = driver.findElement(By.cssSelector(".tabfy td>span:nth-child(2)"));
            Integer totalPageNum = Integer.valueOf(pageNumEle.getText());
            List<Delivery> deliveries = new ArrayList<>();
            List<DeliveryDetails> deliveryDetails = new ArrayList<>();
            // 一页一页遍历爬取数据
            for(int i = 0;i < totalPageNum;i ++) {
                // 爬取外部发货列表
                this.crawlDeliveryListPage(driver, deliveries);
                // 爬取单条发货详情
                this.crawlDetails(driver,deliveryDetails);
                // 设置申请单号
                for(int x = 0;x < deliveries.size();x++){
                    Delivery tmpDelivery = deliveries.get(x);
                    DeliveryDetails tmpDeliveryDetails = deliveryDetails.get(x);
                    tmpDeliveryDetails.setApplyNum(tmpDelivery.getApplyNum());
                }
                deliveryService.batchInsertDetails(deliveryDetails);
                // 一次性插入一页 15条
                deliveryService.batchInsert(deliveries);

                // 清空这个list
                deliveries.clear();
                deliveryDetails.clear();
                driver.findElement(By.xpath("//a[text() = '下一页']")).click();
            }
            long endMillis = System.currentTimeMillis();
//            log.info("ERROR___自动爬取完成！耗时：" + (beginMillis - endMillis) + "毫秒！");
        } catch (Exception e) {
            log.info("ERROR___586----------------------------------------------------------------------------------------");
            log.info(e.getMessage());
            log.info("ERROR___-------------------------------------------------------------");
        } finally {
            if(driver != null) {
                driver.close();
                driver.quit();
            }
        }

    }

    /**
     * 爬取发货列表（单页）
     * @param driver
     */
    private void crawlDeliveryListPage(ChromeDriver driver, List<Delivery> deliveries) {
        WebElement table = driver.findElement(By.cssSelector("table.RepL1"));
        List<WebElement> rows = table.findElements(By.tagName("tr"));
        // 单页发货列表
        for(int i = 0;i < rows.size();i++) {
            // 第一行为表头，接下来每连续的三行为一个发货信息，第2行为多余信息
            if(i == 0 || (i % 3 == 2)) {
                continue;
            }
            Delivery delivery = this.getDelivery(deliveries, i);
            // 表行
            WebElement row = rows.get(i);
            // 表行的所有表列集合
            List<WebElement> cols = row.findElements(By.tagName("td"));

            try{
                // 解析一行数据
                this.analyseCols(cols, i, delivery);
            } catch (ParseException e) {
                log.info("ERROR___解析出错时间！！！！");
            }

        }


    }

}
