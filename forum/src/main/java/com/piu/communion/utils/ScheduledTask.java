package com.piu.communion.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.io.File;

@Component
public class ScheduledTask {


    @Scheduled(cron = "0 0 0 * * ?")
    public void executeFileDownLoadTask() {
        final Logger logger = LoggerFactory.getLogger(ScheduledTask.class);
        // 间隔2分钟,执行任务
        File file = new File(ImagesResultUtils.uploadTemp());
        ImagesResultUtils.delete(file);

    }
}
