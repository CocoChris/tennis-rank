package com.ita.rank.common.constants;

import java.util.Arrays;
import java.util.List;

/**
 * @Author: liuxingxin
 * @Date: 2019/2/21
 */
public class eventLevelConstants {

    public static final String T1 = "T1";
    public static final String T2_PLUS = "T1.5";
    public static final String T2 = "T2";
    public static final String T3 = "T3";
    public static final String YEC = "YEC";
    // non-mandatory
    public static final String NM = "NM";

    public static final List<String> MANDATORY_EVENT = Arrays.asList(T1, T2_PLUS, T2);
}
