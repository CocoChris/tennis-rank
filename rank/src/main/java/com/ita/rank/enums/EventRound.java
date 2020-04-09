package com.ita.rank.enums;

import java.util.HashMap;
import java.util.Map;

/**
 * @Author: liuxingxin
 * @Date: 2019/2/14
 */
public enum EventRound {

    QR1(0, "QR1"),
    QR2(1, "QR2"),
    QFi(2, "QFi"),
    Q(3, "Q"),
    R64(4, "R64"),
    R32(5, "R32"),
    R16(6, "R16"),
    QF(7, "QF"),
    SF(8, "SF"),
    F(9, "F"),
    W(10, "W");

    private int index;
    private String round;

    private static final Map<Integer, String> indexMap = new HashMap<>();
    private static final Map<String, Integer> roundMap = new HashMap<>();

    EventRound(int index, String round) {
        this.index = index;
        this.round = round;
    }

    /**
     * @return the index
     */
    public int getIndex() {
        return this.index;
    }

    /**
     * @return the round
     */
    public String getRound() {
        return this.round;
    }

    static {
        for (EventRound eventRound : EventRound.values()) {
            indexMap.put(eventRound.getIndex(), eventRound.getRound());
            roundMap.put(eventRound.getRound(), eventRound.getIndex());
        }
    }

    public static String getRoundByIndex(int index) {
        return indexMap.get(index);
    }

    public static int getIndexByRound(String round) {
        return roundMap.get(round);
    }

    public static boolean compare(String round1, String round2) {
        if (getIndexByRound(round1) >= getIndexByRound(round2)) {
            return true;
        } else {
            return false;
        }
    }

    public static String max(String round1, String round2) {
        if (compare(round1, round2)) {
            return round1;
        } else {
            return round2;
        }
    }

    public static String min(String round1, String round2) {
        if (compare(round1, round2)) {
            return round2;
        } else {
            return round1;
        }
    }

    public static boolean isRoundValid(String round) {
        if (roundMap.containsKey(round)) {
            return true;
        } else {
            return false;
        }
    }
}
