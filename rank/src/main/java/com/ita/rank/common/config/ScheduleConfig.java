package com.ita.rank.common.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.SchedulingConfigurer;
import org.springframework.scheduling.config.ScheduledTaskRegistrar;

import java.util.concurrent.Executors;

/**
 * Created by liuxingxin on 2018/7/25.
 * @description: 配置自动触发的多线程定时器，使带@Scheduled注解的定时任务并发执行
 */
@Configuration
public class ScheduleConfig implements SchedulingConfigurer {
    @Override
    public void configureTasks(ScheduledTaskRegistrar taskRegistrar) {

        taskRegistrar.setScheduler(Executors.newScheduledThreadPool(5));
    }
}

