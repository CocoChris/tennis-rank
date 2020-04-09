package com.ita.rank.common.utils;

import com.ita.rank.service.PlayerInfoService;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Author: liuxingxin
 * @Date: 2019/2/17
 */

@Component
public class ExcelReaderUtil {

    @Autowired
    PlayerInfoService playerInfoService;

    public static void main(String[] args) {
        ExcelReaderUtil obj = new ExcelReaderUtil();
//        File file = new File("/Users/coco/Files/wca/rank_season_0/第0期排名.xls");
//        obj.readExcel(file);
//        obj.insertPtsOfSeason0();
    }

    public Map<String, Map<String, Integer>> getPtsMapOfSeason0() throws IOException
    {
        String path1 = "/Users/coco/Files/wca/rank_season_0/第0期排名_0.xlsx";
        String path2 = "/Users/coco/Files/wca/rank_season_0/第1期排名_0.xlsx";

        XSSFWorkbook xwb = new XSSFWorkbook(path1);
        XSSFSheet sheet = xwb.getSheetAt(0);
        XSSFRow row;
        String cell;

        List<String> eventNameList1 = new ArrayList<>();
        int index = sheet.getFirstRowNum();
        row = sheet.getRow(index);
        for (int j = 1; j < row.getPhysicalNumberOfCells(); j++)
        {
            cell = row.getCell(j).toString();
            eventNameList1.add(cell);
        }

        System.out.println(eventNameList1);

        Map<String, Map<String, Integer>> ptsOfSeason0Map = new HashMap<>();

        for (int i = index + 1; i < sheet.getPhysicalNumberOfRows(); i ++) {
            row = sheet.getRow(i);
            String playerName = row.getCell(0).toString();
            if (playerName.equals("182.0")) {
                playerName = "182";
            }
            if (playerName.equals("1231986.0")) {
                playerName = "1231986";
            }
            Map<String, Integer> ptsMap = new HashMap<>();
            for (int j = 1; j < row.getPhysicalNumberOfCells(); j++)
            {
                cell = row.getCell(j).toString();
                String eventName = eventNameList1.get(j-1);
                if (cell.equals("")) {
                    ptsMap.put(eventName, 0);
                } else {
                    ptsMap.put(eventName, Double.valueOf(cell).intValue());
                }
            }
            ptsOfSeason0Map.put(playerName, ptsMap);
        }

        xwb = new XSSFWorkbook(path2);
        sheet = xwb.getSheetAt(0);

        List<String> eventNameList2 = new ArrayList<>();
        index = sheet.getFirstRowNum();
        row = sheet.getRow(index);
        for (int j = 1; j < row.getPhysicalNumberOfCells(); j++)
        {
            cell = row.getCell(j).toString();
            eventNameList2.add(cell);
        }

        System.out.println(eventNameList2);

        for (int i = index + 1; i < sheet.getPhysicalNumberOfRows(); i ++) {
            row = sheet.getRow(i);
            String playerName = row.getCell(0).toString();
            if (playerName.equals("182.0")) {
                playerName = "182";
            }
            if (playerName.equals("1231986.0")) {
                playerName = "1231986";
            }
            Map<String, Integer> ptsMap = ptsOfSeason0Map.get(playerName);
            for (int j = 1; j < row.getPhysicalNumberOfCells(); j++)
            {
                cell = row.getCell(j).toString();
                String eventName = eventNameList2.get(j-1);
                if (cell.equals("")) {
                    ptsMap.put(eventName, 0);
                } else {
                    ptsMap.put(eventName, Double.valueOf(cell).intValue());
                }
            }
            ptsOfSeason0Map.put(playerName, ptsMap);
        }

//        for (String playerName : ptsOfSeason0Map.keySet()) {
//            System.out.println(playerName + " " + ptsOfSeason0Map.get(playerName));
//        }

        return ptsOfSeason0Map;
    }

//    public void insertPtsOfSeason0() {
//
//        try {
//            Map<String, Map<String, Integer>> ptsOfSeason0 = getPtsMapOfSeason0();
//
//            for (String playerName : ptsOfSeason0.keySet()) {
//                Map<String, Integer> ptsMap = ptsOfSeason0.get(playerName);
//
//                int playerId = playerInfoService.selectByPlayerName(playerName).getPlayerId();
////                } catch (Exception e) {
////                    System.out.println(playerName);
////                }
//
//                for (String eventName : ptsMap.keySet()) {
//                    int pts = ptsMap.get(eventName);
//                    if (pts > 0) {
//                        EventInfoSeason0Pojo eventInfoSeason0Pojo = eventInfoSeason0Service.selectByEventName(eventName);
//                        String eventLevel = eventInfoSeason0Pojo.getEventLevel();
//                        int week = eventInfoSeason0Pojo.getWeek();
//                        int season = eventInfoSeason0Pojo.getSeason();
//
//                        GradeRecordSeason0Pojo gradeRecordSeason0Pojo = new GradeRecordSeason0Pojo(playerId, playerName, eventLevel, pts, week, season);
//                        gradeRecordSeason0Service.insert(gradeRecordSeason0Pojo);
//                    }
//                }
//            }
//
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//    }

}
