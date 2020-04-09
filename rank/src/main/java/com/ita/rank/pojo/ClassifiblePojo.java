package com.ita.rank.pojo;

import lombok.Data;

/**
 * @Author: liuxingxin
 * @Date: 2019/2/21
 */

@Data
public class ClassifiblePojo {

    private String eventLevel;

    private int matchMode;

    public ClassifiblePojo(String eventLevel, int matchMode) {
        this.eventLevel = eventLevel;
        this.matchMode = matchMode;
    }

    public ClassifiblePojo(String eventLevel) {
        this.eventLevel = eventLevel;
    }

    public ClassifiblePojo() {
        super();
    }
}
