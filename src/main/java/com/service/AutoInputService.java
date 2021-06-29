package com.service;

import com.dao.goods.DeliveryMapper;
import com.dao.goods.DetailsParams;
import com.driver.DriverManage;
import com.job.InputUtils;
import com.service.goods.DeliveryService;
import com.utils.AutomationUtils;
import io.jsonwebtoken.lang.Collections;
import org.apache.commons.io.FileUtils;
import org.openqa.selenium.chrome.ChromeDriver;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.io.*;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;

@Service
public class AutoInputService {
    private static final Logger log = LoggerFactory.getLogger(AutoInputService.class);
    @Value("${filepath}")
    private String filepath;
    // 吨比例
    @Value("${tonRatio}")
    private Integer tonRatio;
    @Value("${movepath}")
    private String movepath;
    @Autowired
    private DeliveryService deliveryService;
    @Autowired
    private DeliveryMapper deliveryMapper;
    private SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    String logfilePath = null;



    @PostConstruct
    public void init() {
        logfilePath = filepath + File.separator + "错误日志.txt";
        File file = new File(logfilePath);
        file.delete();
        try {
            file.createNewFile();
        } catch (IOException e) {
            e.printStackTrace();
        }
        writeErrLog("程序启动", logfilePath, format.format(new Date()), false);
    }
    public void autoInput() {
        // 读取文件
        readFile();

        // 从数据库读取需要自动录入的数据
        List<DetailsParams> params = deliveryMapper.getAutoInputDetails();
        if(!Collections.isEmpty(params)) {
            Map<String, List<DetailsParams>> autoInputMaps = InputUtils.constructInputData(params,deliveryService);
            AutomationUtils automationUtils = getAutomationUtils();
            ChromeDriver driver = automationUtils.getCurrentDriver();
            if(automationUtils.isLoginFlag() == false) {
                log.info("一分钟内未完成登录，程序结束！！！");
                this.quitDriver(driver);
                return;
            }
            try{
                automationUtils.autoInput1(autoInputMaps);

            } catch (Exception e) {
                log.info("异常信息：" + e.getMessage());
                e.printStackTrace();
                this.quitDriver(driver);
            }



            // 退出chromeDriver
            this.quitDriver(driver);
            Map<String, String> name = deliveryMapper.getUserPasw();
//            automationUtils.crawlDeliveryList(name.get("username"), name.get("password"));
        }

    }

    private void quitDriver(ChromeDriver driver) {
        try {
            driver.close();
        } catch (Exception e) {
            writeErrLog("关闭chrome异常出错", logfilePath, format.format(new Date()), true);
        } finally {
            try {
                driver.quit();
            } catch (Exception e1) {
                writeErrLog("闭chrome异常出错!", logfilePath, format.format(new Date()), true);
            }
        }
    }

    private AutomationUtils getAutomationUtils() {
        Map<String, String> map = deliveryMapper.getUserPasw();
        String username = map.get("username");
        String password = map.get("password");
        //
        AutomationUtils automationUtils = new AutomationUtils(deliveryService,username, password);
        return  automationUtils;
    }
    private void readFile(){
        File[] files = InputUtils.listFiles(filepath);
        // 是否爬虫
        boolean crawlOrNot = files != null && files.length != 0;
        if(files!=null) {
            for(int i = 0; i < files.length; i++) {
                String createTime = format.format(new Date());
                File file = files[i];
                // 文件夹，进行下一次循环
                if(file.isDirectory() || !(file.getName().contains(".xls") || file.getName().contains(".xlsx"))) {
                    continue;
                }
                List<Map<String,String>> list = null;
                try {
                    list  = InputUtils.readExcelGetObj(file.getAbsolutePath(), tonRatio);
                    // 如果读取文件没有发生异常，移动文件
                    try {
                        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH_mm_ss");
                        FileUtils.moveFile(file, new File(movepath + File.separator + format.format(new Date()) + file.getName()));
                    } catch (IOException e) {
                        e.printStackTrace();
                        writeErrLog("移动文件发生异常！移动文件：" + file.getAbsolutePath(), logfilePath, createTime,true);
                    }
                } catch (Exception e) {
                    writeErrLog("读取文件发生异常！读取文件：" + file.getAbsolutePath(), logfilePath, createTime, true);
                }
                if(!Collections.isEmpty(list)) {
                    deliveryMapper.addAutoInputDetails(list, createTime);
                }




            }
        }
    }

    /**
     * 写入错误日志
     * @param logMsg  日志消息
     * @param filePath 文件路径
     */
    private static void writeErrLog(String logMsg, String filePath, String logTime, boolean append) {

        File file = new File(filePath);
        FileWriter fw = null;
        try {
            fw = new FileWriter(file, true);
            String msg = logTime + " 写入日志 : " + logMsg + "\n";
            if(append) {
                fw.append(msg);
            } else {
                fw.write(msg);
            }

            fw.flush();
            fw.close();
        } catch (IOException e) {
            e.printStackTrace();
            log.info(e.toString());
        }

    }

    public static void main(String[] args) {
//        writeErrLog("456","C:\\Users\\dell\\Desktop\\xmciq\\错误日志.txt", new Date());
    }
    /**
     * 创建错误日志文件
     */
    private static void createErrLogFile(String logfilePath) {
        File file = new File(logfilePath);
//        File file = new File("C:\\Users\\dell\\Desktop\\xmciq\\错误日志.txt");
        // 创建错误日志文件
        if(!file.exists()) {
            try {
                file.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
                log.info(e.toString());
            }
        }
    }
}
