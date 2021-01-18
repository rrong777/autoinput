//package com;
//
//import com.dao.goods.DeliveryMapper;
//import com.dao.goods.DetailsParams;
//import com.job.InputUtils;
//import com.service.goods.DeliveryService;
//import com.utils.AutomationUtils;
//import org.apache.poi.hssf.usermodel.HSSFDateUtil;
//import org.apache.poi.hssf.usermodel.HSSFWorkbook;
//import org.apache.poi.ss.usermodel.*;
//import org.apache.poi.xssf.usermodel.XSSFWorkbook;
//import org.junit.Test;
//import org.junit.runner.RunWith;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.beans.factory.annotation.Value;
//import org.springframework.boot.test.context.SpringBootTest;
//import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
//import org.springframework.test.context.web.WebAppConfiguration;
//
//import java.io.*;
//import java.text.SimpleDateFormat;
//import java.util.*;
//
///**
// * @author: wql78
// * @date: 2020/11/26 14:12
// * @description:
// */
//@RunWith(SpringJUnit4ClassRunner.class)
//@SpringBootTest
//@WebAppConfiguration
//public class Test2 {
//    @Value("${filepath}")
//    private String filepath;
//    @Value("${movepath}")
//    private String movepath;
//    @Autowired
//    private DeliveryService deliveryService;
//    @Autowired
//    private DeliveryMapper deliveryMapper;
//    private static final Logger logger = LoggerFactory.getLogger(SpringTest.class);
//
//    @Test
//    public void autoInput() {
//        Long begin = System.currentTimeMillis();
//        List<DetailsParams> params = getDetailsParmsList();
//        if(params != null) {
//            autoInputMethods(params);
//        }
//        long end = System.currentTimeMillis();
////        logger.info("自动录入结束，耗时" + (end - begin) + "毫秒！ 录入" + (params == null ? 0 : params.size()) + "条数据！");
//
//    }
//
//    @Test
//    public void crawlTest() {
////        logger.info("爬取发货列表定时任务启动！");
////        long start = System.currentTimeMillis();
////        AutomationUtils automationUtils = new AutomationUtils(deliveryService);
////        automationUtils.crawlDeliveryList();
////        long end = System.currentTimeMillis();
////        logger.info("共花费时间：{}" + (end - start) + "毫秒");
//    }
//
//    @Test
//    public void mainTest() {
////        String username = "";
////        String password = "";
////        Long begin = System.currentTimeMillis();
//////        logger.info("自动录入开始");
////        InputUtils inputUtils = new InputUtils();
////        List<DetailsParams> params = inputUtils.getDetailsParmsList(deliveryMapper, filepath);
////        if(params != null) {
////            inputUtils.autoInputMethods(params, deliveryService,username, password);
////        }
////        long end = System.currentTimeMillis();
//////        logger.info("自动录入结束，耗时" + (end - begin) + "毫秒！ 录入" + (params == null ? 0 : params.size()) + "条数据！");
////        inputUtils.moveFile(filepath, movepath);
//    }
//
//
//    public void moveFile() {
//        File[] files = listFiles(filepath);
//        for (File file : files) {
//            if(file.isFile()) {
//                file.renameTo(new File(movepath + File.separator + file.getName()));
//            }
//        }
//    }
//
//    public List<DetailsParams> getDetailsParmsList() {
//        File[] files = listFiles(filepath);
//        List<Map<String,String>> finalList = new ArrayList<>();
//        String createTime = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date());
//        if(files != null) {
//            for(int i = 0;i < files.length;i++) {
//                File file = files[i];
//                if(file.isDirectory()) {
//                    continue;
//                }
//                List<Map<String,String>> list = readExcelGetObj(file.getAbsolutePath());
//                if(list!=null && list.size() != 0) {
//                    finalList.addAll(list);
//                }
//            }
//            // 没有读取到
//            if(finalList.size() == 0) {
//                return null;
//            }
//            // 五分钟插入一次
//            deliveryMapper.addAutoInputDetails(finalList, createTime);
//            List<DetailsParams> params = deliveryMapper.getAutoInputDetails(createTime);
//            return params;
//        } else {
//            return null;
//        }
//    }
//    //读取excel
//    public static Workbook readExcel(String filePath){
//        Workbook wb = null;
//        if(filePath==null){
//            return null;
//        }
//        String extString = filePath.substring(filePath.lastIndexOf("."));
//        InputStream is = null;
//        try {
//            is = new FileInputStream(filePath);
//            if(".xls".equals(extString)){
//                return wb = new HSSFWorkbook(is);
//            }else if(".xlsx".equals(extString)){
//                return wb = new XSSFWorkbook(is);
//            }else{
//                return wb = null;
//            }
//
//        } catch (FileNotFoundException e) {
//            e.printStackTrace();
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//        return wb;
//    }
//    public static Object getCellFormatValue(Cell cell){
//        Object cellValue = null;
//        if(cell!=null){
//            //判断cell类型
//            switch(cell.getCellType()){
//                case Cell.CELL_TYPE_NUMERIC:{
//                    cellValue = String.valueOf(cell.getNumericCellValue());
//                    break;
//                }
//                case Cell.CELL_TYPE_FORMULA:{
//                    //判断cell是否为日期格式
//                    if(DateUtil.isCellDateFormatted(cell)){
//                        //转换为日期格式YYYY-mm-dd
//                        cellValue = cell.getDateCellValue();
//                    }else{
//                        //数字
//                        cellValue = String.valueOf(cell.getNumericCellValue());
//                    }
//                    break;
//                }
//                case Cell.CELL_TYPE_STRING:{
//                    cellValue = cell.getRichStringCellValue().getString();
//                    break;
//                }
//                default:
//                    cellValue = "";
//            }
//        }else{
//            cellValue = "";
//        }
//        return cellValue;
//    }
//    public List<Map<String,String>> readExcelGetObj(String filePath) {
//        Workbook wb =null;
//        Sheet sheet = null;
//        Row row = null;
//        List<Map<String,String>> list = null;
//        String cellData = null;
//        String columns[] = {"InspectionNumber","VehicleNumber","DeliveryTime", "Weight", "Pcs"};
//        wb = readExcel(filePath);
//        if(wb != null){
//            //用来存放表中数据
//            list = new ArrayList<Map<String,String>>();
//            //获取第一个sheet
//            sheet = wb.getSheetAt(0);
//            //获取最大行数
//            int rownum = sheet.getPhysicalNumberOfRows();
//            //获取第一行
//            row = sheet.getRow(0);
//            //获取最大列数
//            int colnum = row.getPhysicalNumberOfCells();
//            for (int i = 1; i<rownum; i++) {
//                Map<String,String> map = new LinkedHashMap<String,String>();
//                row = sheet.getRow(i);
//                if(row !=null){
//                    for (int j=0;j<colnum;j++){
//                        cellData = (String) getCellFormatValue(row.getCell(j));
//                        if(columns[j].equals("DeliveryTime")) {
//                            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
//                            Date date = HSSFDateUtil.getJavaDate(Double.parseDouble(cellData));
//                            cellData = sdf.format(date);
//                        }
//                        map.put(columns[j], cellData);
//                    }
//                }else{
//                    break;
//                }
//
//                list.add(map);
//            }
//        }
//        //遍历解析出来的list
//        for (Map<String,String> map : list) {
//            for (Map.Entry<String,String> entry : map.entrySet()) {
//                System.out.print(entry.getKey()+":"+entry.getValue()+",");
//            }
//        }
//        return list;
//    }
//    public File[] listFiles(String path) {
//        File file = new File(path);
//        if(file.isDirectory()) {
//            return file.listFiles();
//        }
//        return null;
//    }
//
//    public void autoInputMethods(List<DetailsParams> params) {
////        Map<String, List<DetailsParams>> autoInputMaps = new HashMap<>();
////        for(int i = 0;i < params.size();i++) {
////            DetailsParams tmp = params.get(i);
////            List<DetailsParams> tmpList = autoInputMaps.get(tmp.getInspectionNumber());
////            if(tmpList==null) {
////                tmpList = new ArrayList<>();
////                autoInputMaps.put(tmp.getInspectionNumber(), tmpList);
////            }
////            tmpList.add(tmp);
////        }
////        AutomationUtils automationUtils = new AutomationUtils(deliveryService);
////        automationUtils.autoInput(autoInputMaps, params.size());
//    }
//}
