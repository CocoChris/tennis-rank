package com.ita.rank.utils;

import com.alibaba.fastjson.JSON;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.ResourceUtils;

import java.io.*;
import java.text.DecimalFormat;
import java.util.*;

/**
 * @Author: liuxingxin
 * @Date: 2020/4/7
 */
public class XlsxUtil {
  private Logger logger = LoggerFactory.getLogger(XlsxUtil.class);
  private Workbook wb;
  private Sheet sheet;
  private Row row;

  public XlsxUtil(String filepath) {
    if (filepath == null) {
      return;
    }
    String ext = filepath.substring(filepath.lastIndexOf("."));
    try {
      InputStream is = new FileInputStream(filepath);
      if (".xls".equals(ext)) {
        wb = new HSSFWorkbook(is);
      } else if (".xlsx".equals(ext)) {
        wb = new XSSFWorkbook(is);
      } else {
        wb = null;
      }
    } catch (FileNotFoundException e) {
      logger.error("FileNotFoundException", e);
    } catch (IOException e) {
      logger.error("IOException", e);
    }
  }

  /**
   * 读取Excel表格表头的内容
   *
   * @return String 表头内容的数组
   * @author zengwendong
   */
  public String[] readExcelTitle() throws Exception {
    if (wb == null) {
      throw new Exception("Workbook对象为空");
    }
    sheet = wb.getSheetAt(0);
    row = sheet.getRow(0);
    // 标题总列数
    int colNum = row.getPhysicalNumberOfCells();
    System.out.println("colNum:" + colNum);
    String[] title = new String[colNum];
    for (int i = 0; i < colNum; i++) {
      // title[i] = getStringCellValue(row.getCell((short) i));
      title[i] = row.getCell(i).toString();
    }
    return title;
  }

  /**
   * 读取Excel数据内容
   *
   * @return Map 包含单元格数据内容的Map对象
   * @author zengwendong
   */
  public Map<Integer, Map<Integer, Object>> readExcelContent()
    throws Exception {
    if (wb == null) {
      throw new Exception("Workbook对象为空");
    }
    Map<Integer, Map<Integer, Object>> content = new HashMap<Integer, Map<Integer, Object>>();

    sheet = wb.getSheetAt(0);
    // 得到总行数
    int rowNum = sheet.getLastRowNum();
    row = sheet.getRow(0);
    int colNum = row.getPhysicalNumberOfCells();
    // 正文内容应该从第二行开始,第一行为表头的标题
    for (int i = 1; i <= rowNum; i++) {
      row = sheet.getRow(i);
      int j = 0;
      Map<Integer, Object> cellValue = new HashMap<Integer, Object>();
      while (j < colNum) {
        Object obj = getCellFormatValue(row.getCell(j));
        cellValue.put(j, obj);
        j++;
      }
      content.put(i, cellValue);
    }
    return content;
  }

  /**
   * 读取Excel数据内容
   *
   * @return Map 包含单元格数据内容的Map对象
   * @author zengwendong
   */
  public List<List<Object>> readExcelContent_() throws Exception {
    if (wb == null) {
      throw new Exception("Workbook对象为空！");
    }
    List<List<Object>> list = new ArrayList<List<Object>>();
    List<Object> li = null;
    sheet = wb.getSheetAt(0);
    // 得到总行数
    int rowNum = sheet.getLastRowNum();
    row = sheet.getRow(0);
    int colNum = row.getPhysicalNumberOfCells();
    // 正文内容应该从第二行开始,第一行为表头的标题
    for (int i = 1; i <= rowNum; i++) {
      li = new ArrayList<Object>();
      row = sheet.getRow(i);
      int j = 0;
      while (j < colNum) {
        Object obj = getCellFormatValue(row.getCell(j));
        li.add(obj);
        j++;
      }
      list.add(li);
    }
    return list;
  }

  /**
   *
   * 根据Cell类型设置数据
   *
   * @param cell
   * @return
   * @author zengwendong
   */
  private Object getCellFormatValue(Cell cell) {
    Object cellvalue = "";
    if (cell != null) {
      // 判断当前Cell的Type
      switch (cell.getCellType()) {
        case Cell.CELL_TYPE_NUMERIC:// 如果当前Cell的Type为NUMERIC
          double cellValue = cell.getNumericCellValue();
          cellvalue = new DecimalFormat("#").format(cellValue);
          break;
        case Cell.CELL_TYPE_FORMULA: {
          // 判断当前的cell是否为Date
          if (DateUtil.isCellDateFormatted(cell)) {
            // 如果是Date类型则，转化为Data格式
            // data格式是带时分秒的：2013-7-10 0:00:00
            // cellvalue = cell.getDateCellValue().toLocaleString();
            // data格式是不带带时分秒的：2013-7-10
            Date date = cell.getDateCellValue();
            cellvalue = date;
          } else {// 如果是纯数字

            // 取得当前Cell的数值
            cellvalue = String.valueOf(cell.getNumericCellValue());
          }
          break;
        }
        case Cell.CELL_TYPE_STRING:// 如果当前Cell的Type为STRING
          // 取得当前的Cell字符串
          cellvalue = cell.getRichStringCellValue().getString();
          break;
        default:// 默认的Cell值
          cellvalue = "";
      }
    } else {
      cellvalue = "";
    }
    return cellvalue;
  }

  public static void main(String[] args) {
    try {
      File path = new File(ResourceUtils.getURL("classpath:").getPath());
      File xlsx = new File(path.getAbsolutePath(),"xlsx/rank_200323.xlsx");
      String filepath = xlsx.getAbsolutePath();
      System.out.println(filepath);
      XlsxUtil excelReader = new XlsxUtil(filepath);

      // 对读取Excel表格内容测试
      // Map<Integer, Map<Integer, Object>> map = excelReader
      // .readExcelContent();
      // System.out.println("获得Excel表格的内容:");
      List<List<Object>> list = excelReader.readExcelContent_();
      System.out.println("获得Excel表格的内容:");
      System.err.println(JSON.toJSONString(list));
    } catch (FileNotFoundException e) {
      System.out.println("未找到指定路径的文件!");
      e.printStackTrace();
    } catch (Exception e) {
      e.printStackTrace();
    }
  }
}  
