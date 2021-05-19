package com.railf.framework.infrastructure.quartz;

import com.railf.framework.infrastructure.util.JsonUtils;
import lombok.extern.slf4j.Slf4j;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.scheduling.quartz.QuartzJobBean;

/***
 * @author : Rain
 * @date : 2020/10/18 9:36 AM
 *
 * 自定义Job实现示例
 */
@Slf4j
public class QuartzJob extends QuartzJobBean {

    @Override
    protected void executeInternal(JobExecutionContext jobExecutionContext) throws JobExecutionException {
        log.info("任务开始, JobDetail: {}", JsonUtils.toJsonString(jobExecutionContext.getJobDetail()));
        // do something
        log.info("任务结束");
    }
}
