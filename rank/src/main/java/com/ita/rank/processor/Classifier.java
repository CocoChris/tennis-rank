package com.ita.rank.processor;

import com.ita.rank.pojo.ClassifiblePojo;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Author: liuxingxin
 * @Date: 2019/2/21
 */

@Component
public class Classifier {

    public Map<String, List<ClassifiblePojo>> classifyByEventLevel(List<ClassifiblePojo> pojoList) {

        Map<String, List<ClassifiblePojo>> result = new HashMap<>();
        for (ClassifiblePojo classifiblePojo : pojoList) {
            String eventLevel = classifiblePojo.getEventLevel();
            if (result.containsKey(eventLevel)) {
                result.get(eventLevel).add(classifiblePojo);
            } else {
                result.put(eventLevel, new ArrayList<ClassifiblePojo>());
                result.get(eventLevel).add(classifiblePojo);
            }
        }
//        for (String eventLevel : result.keySet()) {
//            System.out.println(eventLevel);
//            for (ClassifiblePojo classifiblePojo : result.get(eventLevel)) {
//                System.out.println(classifiblePojo.toString());
//            }
//        }

        return result;
    }
}
