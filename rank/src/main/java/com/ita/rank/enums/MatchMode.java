package com.ita.rank.enums;

import java.util.HashMap;
import java.util.Map;

/**
 * @Author: liuxingxin
 * @Date: 2019/2/22
 */
public enum MatchMode {

    NORMAL(0, "普通"),
    FAST(1, "快速"),
    SKIN(2, "skin"),
    WHEELCHAIR(3, "轮椅");

    private int index;
    private String mode;

    private static final Map<Integer, String> indexMap = new HashMap<>();
    private static final Map<String, Integer> modeMap = new HashMap<>();

    MatchMode(int index, String mode) {
        this.index = index;
        this.mode = mode;
    }

    /**
     * @return the index
     */
    public int getIndex() {
        return this.index;
    }

    /**
     * @return the mode
     */
    public String getMode() {
        return this.mode;
    }

    static {
        for (MatchMode matchMode : MatchMode.values()) {
            indexMap.put(matchMode.getIndex(), matchMode.getMode());
            modeMap.put(matchMode.getMode(), matchMode.getIndex());
        }
    }

    public static String getModeByIndex(int index) {
        return indexMap.get(index);
    }

    public static int getIndexByMode(String mode) {
        return modeMap.get(mode);
    }
}
