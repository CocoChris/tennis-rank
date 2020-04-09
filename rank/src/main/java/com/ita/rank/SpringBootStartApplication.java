package com.ita.rank;

import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;

/**
 * @Author: liuxingxin
 * @Date: 2018/11/22
 */

public class SpringBootStartApplication extends SpringBootServletInitializer {

    @Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder builder) {

        setRegisterErrorPageFilter(false);

        return builder.sources(RankApplication.class);
    }
}