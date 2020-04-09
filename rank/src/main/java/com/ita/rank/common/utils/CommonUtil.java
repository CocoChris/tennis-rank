package com.ita.rank.common.utils;

import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @Author: liuxingxin
 * @Date: 2019/2/19
 */

@Component
public class CommonUtil {

    /**
     * 求list的和
     * @param list
     * @return
     */
    public static int getSum(List<Integer> list) {
        int sum = 0;
        for (int i = 0; i < list.size(); i ++) {
            sum += list.get(i);
        }
        return sum;
    }
}
