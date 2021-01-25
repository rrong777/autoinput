//package com;
//
//
//import com.alibaba.fastjson.JSONArray;
//import com.alibaba.fastjson.JSONObject;
//import com.dao.goods.DeliveryMapper;
//import com.dao.goods.DetailsParams;
//import com.job.InputUtils;
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
// * @author wqr
// * @data 2020-11-24 - 17:13
// */
//@RunWith(SpringJUnit4ClassRunner.class)
//@SpringBootTest
//@WebAppConfiguration
//public class SpringTest {
//    private static final Logger log = LoggerFactory.getLogger(SpringTest.class);
//    @Value("${filepath}")
//    private String filepath;
//    @Value("${movepath}")
//    private String movepath;
//
//    @Autowired
//    private DeliveryMapper deliveryMapper;
//
//    @Test
//    public void test12() {
//        InputUtils inputUtils = new InputUtils();
//        inputUtils.listFiles(filepath);
//
//    }
//
//    @Test
//    public void test() {
////        readExcelGetObj(filepath);
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
//                return ;
//            }
//            // 五分钟插入一次
//            deliveryMapper.addAutoInputDetails(finalList, createTime);
//            List<DetailsParams> params = deliveryMapper.getAutoInputDetails(createTime);
//            System.out.println(params);
//        } else {
//
//        }
//    }
//
//    List<Map<String, Object>> getNewMap(List<Map<String,String>> finalList){
//        List<Map<String, Object>> newList = new ArrayList<>();
//        for(Map<String, String> tmp : finalList) {
//            Map<String, Object> newMap = new HashMap<>();
//            for(Map.Entry<String, String> entry : tmp.entrySet()) {
//                if(entry.getKey().equals("Pcs")) {
//                    int pcs = Double.valueOf(entry.getValue()).intValue();
//
//                }
//            }
//        }
//        return null;
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
//
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
//
//    public DetailsParams parseObj(JSONObject jsonObject) {
//        return JSONObject.parseObject(jsonObject.toString(), DetailsParams.class);
//    }
//
//    /**
//     * 获得自动录入对象
//     * @return
//     */
//    public List<DetailsParams> getInputObj(List<String> jsonStrs) {
//        // 一个文件就是一个对象数组，多个文件就是多个对象数组
//        List<DetailsParams> res = new ArrayList<>();
//        for (String tmp : jsonStrs) {
//            try {
//                JSONArray tmpJSONs = JSONObject.parseArray(tmp);
//                for(Object tmpObj : tmpJSONs) {
//                    DetailsParams detailsParams = JSONObject.parseObject(tmpObj.toString(), DetailsParams.class);
//                    res.add(detailsParams);
//                }
//            } catch (Exception e) {
//                log.info("解析json出错 ：" + tmp);
//            }
//        }
//        return res;
//
//    }
//
//    public List<String> readFiles(File[] files) {
//        List<String> jsonList = new ArrayList<>();
//        for(int i = 0;i < files.length;i++) {
//            File tmpFile = files[i];
//            if(tmpFile.isFile()) {
//                String fileRes = readFile(tmpFile.getAbsolutePath());
//                if(fileRes != null) {
//                    jsonList.add(fileRes);
//                }
//
//            }
//        }
//        return jsonList;
//    }
//
//    public File[] listFiles(String path) {
//        File file = new File(path);
//        if(file.isDirectory()) {
//            return file.listFiles();
//        }
//        return null;
//    }
//
//    public String readFile(String path) {
//        try {
//            BufferedReader in = new BufferedReader(new FileReader(path));
//            StringBuffer sb = new StringBuffer();
//            String str;
//            while ((str = in.readLine()) != null) {
//                sb.append(str);
//            }
//            return sb.toString();
//        } catch (IOException e) {
//            log.info(e.getMessage());
//        }
//
//        return null;
//    }
//}
