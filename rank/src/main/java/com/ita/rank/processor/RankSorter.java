package com.ita.rank.processor;

import com.ita.rank.pojo.PtsRecordChampionPojo;
import com.ita.rank.pojo.PtsRecordPojo;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

/**
 * @Author: liuxingxin
 * @Date: 2019/2/20
 */

@Component
public class RankSorter {

    // 降序 允许并列
    public static void sortRank(List<PtsRecordPojo> ptsRecordPojoList) {

        List<Integer> rankList = new ArrayList<>();

        ptsRecordPojoList.sort(new Comparator<PtsRecordPojo>() {
            @Override
            public int compare(PtsRecordPojo o1, PtsRecordPojo o2) {
                return -Integer.compare(o1.getTotalPts(), o2.getTotalPts());
            }
        });

        int index = 0;
        int lastPts = -1;
        for (int i = 0; i < ptsRecordPojoList.size(); i ++) {
            PtsRecordPojo ptsRecordPojo = ptsRecordPojoList.get(i);
            if (ptsRecordPojo.getTotalPts() != lastPts) {
                index = i + 1;
                lastPts = ptsRecordPojo.getTotalPts();
                ptsRecordPojo.setRank(index);
            } else {
                ptsRecordPojo.setRank(index);
            }
        }

//        for (int i = 0; i < ptsRecordPojoList.size(); i ++) {
//            System.out.println(ptsRecordPojoList.get(i).getTotalPts() + " " + ptsRecordPojoList.get(i).getRank());
//        }
    }

    public static void sortChampionRank(List<PtsRecordChampionPojo> ptsRecordChampionPojoList) {

        List<Integer> rankList = new ArrayList<>();

        ptsRecordChampionPojoList.sort(new Comparator<PtsRecordChampionPojo>() {
            @Override
            public int compare(PtsRecordChampionPojo o1, PtsRecordChampionPojo o2) {
                return -Integer.compare(o1.getTotalPts(), o2.getTotalPts());
            }
        });

        int index = 0;
        int lastPts = -1;
        for (int i = 0; i < ptsRecordChampionPojoList.size(); i ++) {
            PtsRecordChampionPojo ptsRecordChampionPojo = ptsRecordChampionPojoList.get(i);
            if (ptsRecordChampionPojo.getTotalPts() != lastPts) {
                index = i + 1;
                lastPts = ptsRecordChampionPojo.getTotalPts();
                ptsRecordChampionPojo.setRank(index);
            } else {
                ptsRecordChampionPojo.setRank(index);
            }
        }

//        for (int i = 0; i < ptsRecordChampionPojoList.size(); i ++) {
//            System.out.println(ptsRecordChampionPojoList.get(i).getTotalPts() + " " + ptsRecordChampionPojoList.get(i).getRank());
//        }
    }
}
