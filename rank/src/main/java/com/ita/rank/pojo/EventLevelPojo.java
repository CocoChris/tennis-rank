package com.ita.rank.pojo;

import lombok.Data;

@Data
public class EventLevelPojo {
    private int id;

    private String level;

    private String code;

    private int qr1;

    private int qr2;

    private int qfi;

    private int q;

    private int r64;

    private int r32;

    private int r16;

    private int qf;

    private int sf;

    private int f;

    private int w;

    public EventLevelPojo(int id, String level, String code, int qr1, int qr2, int qfi, int q, int r64, int r32, int r16, int qf, int sf, int f, int w) {
        this.id = id;
        this.level = level;
        this.code = code;
        this.qr1 = qr1;
        this.qr2 = qr2;
        this.qfi = qfi;
        this.q = q;
        this.r64 = r64;
        this.r32 = r32;
        this.r16 = r16;
        this.qf = qf;
        this.sf = sf;
        this.f = f;
        this.w = w;
    }

    public EventLevelPojo() { super(); }
}