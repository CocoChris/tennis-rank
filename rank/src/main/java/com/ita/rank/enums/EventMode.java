package com.ita.rank.enums;

import java.util.HashMap;
import java.util.Map;

/**
 * @Author: liuxingxin
 * @Date: 2019/2/22
 */
public enum EventMode {

    SINGLE(0, "单打"),
    DOUBLE(1, "双打"),
    TEAM(2, "团体");

    private int index;
    private String mode;

    private static final Map<Integer, String> indexMap = new HashMap<>();
    private static final Map<String, Integer> modeMap = new HashMap<>();

    EventMode(int index, String mode) {
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
        for (EventMode eventMode : EventMode.values()) {
            indexMap.put(eventMode.getIndex(), eventMode.getMode());
            modeMap.put(eventMode.getMode(), eventMode.getIndex());
        }
    }

    public static String getModeByIndex(int index) {
        return indexMap.get(index);
    }

    public static int getIndexByMode(String mode) {
        return modeMap.get(mode);
    }
}
