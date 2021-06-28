package com.job;

import com.dao.goods.DeliveryMapper;
import com.dao.goods.DetailsParams;
import com.service.goods.DeliveryService;
import com.utils.AutomationUtils;
import io.jsonwebtoken.lang.Collections;
import org.apache.commons.lang.StringUtils;
import org.apache.poi.hssf.usermodel.HSSFDateUtil;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.openqa.selenium.chrome.ChromeDriver;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * @author: wql78
 * @date: 2020/11/26 14:34
 * @description:
 */
public class InputUtils {
    private static final Logger logger = LoggerFactory.getLogger(InputUtils.class);
    public void moveFile(String filepath, String movepath, List<String> successList) {
        File[] files = listFiles(filepath);
        for (File file : files) {
            if(successList.contains(file.getName())) {
                file.renameTo(new File(movepath + File.separator + file.getName()));
            }
        }
    }
    public static File[] listFiles(String path) {
        File file = new File(path);
        if(file.isDirectory()) {
            return file.listFiles();
        }
        return null;
    }
    public static Map<String, List<DetailsParams>> constructInputData(List<DetailsParams> params, DeliveryService deliveryService) {
        Map<String, List<DetailsParams>> autoInputMaps = new HashMap<>();
        for(int i = 0;i < params.size();i++) {
            DetailsParams tmp = params.get(i);
            List<DetailsParams> tmpList = autoInputMaps.get(tmp.getInspectionNumber());
            if(tmpList==null) {
                tmpList = new ArrayList<>();
                autoInputMaps.put(tmp.getInspectionNumber(), tmpList);
            }
            tmpList.add(tmp);
        }
        return autoInputMaps;
    }

    //读取excel
    public static Workbook readExcel(String filePath){
        Workbook wb = null;
        if(filePath==null){
            return null;
        }
        String extString = filePath.substring(filePath.lastIndexOf("."));
        InputStream is = null;
        try {
            is = new FileInputStream(filePath);
            if(".xls".equals(extString)){
                return wb = new HSSFWorkbook(is);
            }else if(".xlsx".equals(extString)){
                return wb = new XSSFWorkbook(is);
            }else{
                return wb = null;
            }

        } catch (FileNotFoundException e) {
            logger.info(e.getMessage());
            e.printStackTrace();
        } catch (IOException e) {
            logger.info(e.getMessage());
            e.printStackTrace();
        }
        return wb;
    }

    public static void main(String[] args) {
        String s = " 2020/12/26 8:34:39";
        System.out.println(s.trim());
    }
    public static List<Map<String,String>> readExcelGetObj(String filePath, Integer tonRatio) throws Exception {
        Workbook wb =null;
        Sheet sheet = null;
        Row row = null;
        List<Map<String,String>> list = null;
        String cellData = null;
        String columns[] = {"InspectionNumber","VehicleNumber","DeliveryTime", "Weight", "Pcs"};
        wb = readExcel(filePath);
        if(wb != null){
            //用来存放表中数据
            list = new ArrayList<Map<String,String>>();
            //获取第一个sheet
            sheet = wb.getSheetAt(0);
            //获取最大行数
            int rownum = sheet.getPhysicalNumberOfRows();
            //获取第一行
            row = sheet.getRow(0);
            //获取最大列数
            int colnum = row.getPhysicalNumberOfCells();
            if(colnum != 5) {
                throw new Exception("列数不匹配！" + filePath);
            }
            for (int i = 1; i<rownum; i++) {
                Map<String,String> map = new LinkedHashMap<String,String>();
                row = sheet.getRow(i);
                if(row !=null){
                    String firstColumn = (String) getCellFormatValue(row.getCell(0));
                    if(StringUtils.isBlank(firstColumn)) {
                        continue;
                    }
                    for (int j=0;j<colnum;j++){
                        cellData = (String) getCellFormatValue(row.getCell(j));
                        if(columns[j].equals("Weight")) {
                            DecimalFormat format = new DecimalFormat("#0.00");
//                            单位换算
//                            cellData = format.format(Double.valueOf(cellData));
                            cellData = format.format(Double.valueOf(cellData) / tonRatio);
                        }
                        if(columns[j].equals("DeliveryTime")) {
                            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                            Date date = null;
                            try {
                                cellData = cellData.replace(String.valueOf((char)160), "");
                                cellData = cellData.trim();
                                Double dateDoubleValue = Double.parseDouble(cellData.trim());
                                date = HSSFDateUtil.getJavaDate(dateDoubleValue);
                            } catch (Exception e) {
                                logger.info(e.getMessage());
                                if(e.getMessage().indexOf("For input string") != -1) {
                                    try {
                                        date = sdf.parse(cellData);
                                    } catch (ParseException innerE) {
                                        SimpleDateFormat innerSdf = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
                                        date = innerSdf.parse(cellData);
                                    }
                                }
                            }
                            cellData = sdf.format(date);
                        }
                        map.put(columns[j], cellData);
                    }
                }else{
                    break;
                }

                list.add(map);
            }
        }
        //遍历解析出来的list
        for (Map<String,String> map : list) {
            for (Map.Entry<String,String> entry : map.entrySet()) {
                System.out.print(entry.getKey()+":"+entry.getValue()+",");
            }
        }
        return list;
    }
    public static Object getCellFormatValue(Cell cell){
        Object cellValue = null;
        if(cell!=null){
            //判断cell类型
            switch(cell.getCellType()){
                case Cell.CELL_TYPE_NUMERIC:{
                    cellValue = String.valueOf(cell.getNumericCellValue());
                    break;
                }
                case Cell.CELL_TYPE_FORMULA:{
                    //判断cell是否为日期格式
                    if(DateUtil.isCellDateFormatted(cell)){
                        //转换为日期格式YYYY-mm-dd
                        cellValue = cell.getDateCellValue();
                    }else{
                        //数字
                        cellValue = String.valueOf(cell.getNumericCellValue());
                    }
                    break;
                }
                case Cell.CELL_TYPE_STRING:{
                    cellValue = cell.getRichStringCellValue().getString();
                    break;
                }
                default:
                    cellValue = "";
            }
        }else{
            cellValue = "";
        }
        return cellValue;
    }



}
