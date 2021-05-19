package com.railf.framework.infrastructure.quartz.service;

import lombok.extern.slf4j.Slf4j;
import org.quartz.*;
import org.quartz.impl.matchers.GroupMatcher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.scheduling.quartz.QuartzJobBean;
import org.springframework.stereotype.Service;

import java.util.*;

@Slf4j
@SuppressWarnings("unchecked")
public class QuartzServiceImpl implements QuartzService {
    @Autowired
    @Qualifier("schedulerFactoryBean")
    private Scheduler scheduler;

    /**
     * 增加一个job
     *
     * @param jobClass     任务实现类
     * @param jobName      任务名称(建议唯一)
     * @param jobGroupName 任务组名
     * @param jobTime      时间表达式 (这是每隔多少秒为一次任务)
     * @param jobTimes     运行的次数 （<0:表示不限次数）
     * @param jobData      参数
     */
    @Override
    public void addJob(Class<? extends QuartzJobBean> jobClass, String jobName, String jobGroupName, int jobTime, int jobTimes, Map<String, Object> jobData) throws SchedulerException {
        try {
            log.info("增加定时任务, jobType: {}, jobName: {}, jobGroupName: {}, jobTime: {}, jobTimes: {}, jobData: {}", jobClass.getName(), jobName, jobGroupName, jobTime, jobTimes, jobData);
            JobDetail jobDetail = JobBuilder.newJob(jobClass).withIdentity(jobName, jobGroupName).build();
            if (jobData != null && jobData.size() > 0) {
                jobDetail.getJobDataMap().putAll(jobData);
            }

            // 使用simpleTrigger规则
            Trigger trigger = null;
            if (jobTimes < 0) {
                trigger = TriggerBuilder.newTrigger()
                        .withIdentity(jobName, jobGroupName)
                        .withSchedule(SimpleScheduleBuilder.repeatSecondlyForever(1).withIntervalInSeconds(jobTime))
                        .startNow()
                        .build();
            } else {
                trigger = TriggerBuilder.newTrigger()
                        .withIdentity(jobName, jobGroupName)
                        .withSchedule(SimpleScheduleBuilder.repeatSecondlyForever(1).withIntervalInSeconds(jobTime).withRepeatCount(jobTimes))
                        .startNow()
                        .build();
            }
            scheduler.scheduleJob(jobDetail, trigger);
            log.info("增加定时任务成功");
        } catch (SchedulerException e) {
            log.error("增加定时任务失败", e);
            throw e;
        }
    }

    /**
     * 增加一个job
     *
     * @param jobClass     任务实现类
     * @param jobName      任务名称(建议唯一)
     * @param jobGroupName 任务组名
     * @param jobTime      时间表达式 （如：0/5 * * * * ? ）
     * @param jobData      参数
     */
    @Override
    public void addJob(Class<? extends QuartzJobBean> jobClass, String jobName, String jobGroupName, String jobTime, Map<String, Object> jobData) throws SchedulerException {
        log.info("增加定时任务, jobType: {}, jobName: {}, jobGroupName: {}, jobTime: {}, jobData: {}", jobClass.getName(), jobName, jobGroupName, jobTime, jobData);
        try {
            JobDetail jobDetail = JobBuilder.newJob(jobClass).withIdentity(jobName, jobGroupName).build();
            if (jobData != null && jobData.size() > 0) {
                jobDetail.getJobDataMap().putAll(jobData);
            }

            // 使用cornTrigger规则
            Trigger trigger = TriggerBuilder.newTrigger()
                    .withIdentity(jobName, jobGroupName)
                    .startAt(DateBuilder.futureDate(1, DateBuilder.IntervalUnit.SECOND))
                    .withSchedule(CronScheduleBuilder.cronSchedule(jobTime))
                    .startNow()
                    .build();

            // 把作业和触发器注册到任务调度中
            scheduler.scheduleJob(jobDetail, trigger);
            log.info("增加定时任务成功");
        } catch (SchedulerException e) {
            log.error("增加定时任务失败", e);
            throw e;
        }
    }

    /**
     * 修改 一个job的 时间表达式 (cron)
     */
    @Override
    public void updateJob(String jobName, String jobGroupName, String jobTime) throws SchedulerException {
        log.info("更新定时任务, jobName: {}, jobGroupName: {}, jobTime: {}", jobName, jobGroupName, jobTime);
        try {
            TriggerKey triggerKey = TriggerKey.triggerKey(jobName, jobGroupName);
            CronTrigger trigger = (CronTrigger) scheduler.getTrigger(triggerKey);
            trigger = trigger.getTriggerBuilder()
                    .withIdentity(triggerKey)
                    .withSchedule(CronScheduleBuilder.cronSchedule(jobTime))
                    .build();
            // 重启触发器
            scheduler.rescheduleJob(triggerKey, trigger);
            log.info("更新定时任务成功");
        } catch (SchedulerException e) {
            log.error("更新定时任务失败", e);
            throw e;
        }
    }

    /**
     * 删除任务一个job
     */
    @Override
    public void deleteJob(String jobName, String jobGroupName) throws SchedulerException {
        log.info("删除定时任务, jobName: {}, jobGroupName: {}", jobName, jobGroupName);
        try {
            scheduler.deleteJob(new JobKey(jobName, jobGroupName));
            log.info("删除定时任务成功");
        } catch (Exception e) {
            log.error("删除定时任务失败,", e);
            throw e;
        }
    }

    /**
     * 暂停一个job
     */
    @Override
    public void pauseJob(String jobName, String jobGroupName) throws SchedulerException {
        log.info("暂停定时任务, jobName: {}, jobGroupName: {}", jobName, jobGroupName);
        try {
            JobKey jobKey = JobKey.jobKey(jobName, jobGroupName);
            scheduler.pauseJob(jobKey);
            log.info("暂停定时任务成功");
        } catch (SchedulerException e) {
            log.error("暂停定时任务失败,", e);
            throw e;
        }
    }

    /**
     * 恢复一个job
     */
    @Override
    public void resumeJob(String jobName, String jobGroupName) throws SchedulerException {
        log.info("恢复定时任务, jobName: {}, jobGroupName: {}", jobName, jobGroupName);
        try {
            JobKey jobKey = JobKey.jobKey(jobName, jobGroupName);
            scheduler.resumeJob(jobKey);
            log.info("恢复定时任务成功");
        } catch (SchedulerException e) {
            log.error("恢复定时任务失败", e);
            throw e;
        }
    }

    /**
     * 立即执行一个job
     */
    @Override
    public void runAJobNow(String jobName, String jobGroupName) throws SchedulerException {
        log.info("立刻执行定时任务, jobName: {}, jobGroupName: {}", jobName, jobGroupName);
        try {
            JobKey jobKey = JobKey.jobKey(jobName, jobGroupName);
            scheduler.triggerJob(jobKey);
            log.info("立刻执行定时任务成功");
        } catch (SchedulerException e) {
            log.error("立刻执行定时任务失败", e);
            throw e;
        }
    }

    /**
     * 根据name，group 查询某一个任务
     */
    @Override
    public Map<String, Object> getJob(String name, String group) throws SchedulerException {
        log.info("查询定时任务, jobName: {}, jobGroupName: {}", name, group);
        Map<String, Object> map = new HashMap<>();
        try {
            TriggerKey jobKey = new TriggerKey(name, group);
            Trigger trigger = scheduler.getTrigger(jobKey);
            if (trigger != null) {
                map.put("jobName", jobKey.getName());
                map.put("jobGroupName", jobKey.getGroup());
                map.put("description", "触发器:" + trigger.getKey());
                Trigger.TriggerState triggerState = scheduler.getTriggerState(trigger.getKey());
                map.put("jobStatus", triggerState.name());
                if (trigger instanceof CronTrigger) {
                    CronTrigger cronTrigger = (CronTrigger) trigger;
                    String cronExpression = cronTrigger.getCronExpression();
                    map.put("jobTime", cronExpression);
                }
            }
        } catch (SchedulerException e) {
            log.error("查询定时任务失败", e);
            throw e;
        }

        log.info("查询定时任务成功, data: {}", map);
        return map;
    }

    /**
     * 获取所有计划中的任务列表
     */
    @Override
    public List<Map<String, Object>> queryAllJob() throws SchedulerException {
        log.info("查询所有定时任务");
        List<Map<String, Object>> jobList = null;
        try {
            GroupMatcher<JobKey> matcher = GroupMatcher.anyJobGroup();
            Set<JobKey> jobKeys = scheduler.getJobKeys(matcher);
            jobList = new ArrayList<>();
            for (JobKey jobKey : jobKeys) {
                List<? extends Trigger> triggers = scheduler.getTriggersOfJob(jobKey);
                for (Trigger trigger : triggers) {
                    Map<String, Object> map = new HashMap<>();
                    map.put("jobName", jobKey.getName());
                    map.put("jobGroupName", jobKey.getGroup());
                    map.put("description", "触发器:" + trigger.getKey());
                    Trigger.TriggerState triggerState = scheduler.getTriggerState(trigger.getKey());
                    map.put("jobStatus", triggerState.name());
                    if (trigger instanceof CronTrigger) {
                        CronTrigger cronTrigger = (CronTrigger) trigger;
                        String cronExpression = cronTrigger.getCronExpression();
                        map.put("jobTime", cronExpression);
                    }
                    jobList.add(map);
                }
            }
        } catch (SchedulerException e) {
            log.error("查询所有定时任务失败", e);
            throw e;
        }

        log.info("查询所有定时任务成功, dataList: {}", jobList);
        return jobList;
    }

    /**
     * 获取所有正在运行的job
     */
    @Override
    public List<Map<String, Object>> queryRunJob() throws SchedulerException {
        log.info("查询运行中的定时任务");
        List<Map<String, Object>> jobList = null;
        try {
            List<JobExecutionContext> executingJobs = scheduler.getCurrentlyExecutingJobs();
            jobList = new ArrayList<>(executingJobs.size());
            for (JobExecutionContext executingJob : executingJobs) {
                Map<String, Object> map = new HashMap();
                JobDetail jobDetail = executingJob.getJobDetail();
                JobKey jobKey = jobDetail.getKey();
                Trigger trigger = executingJob.getTrigger();
                map.put("jobName", jobKey.getName());
                map.put("jobGroupName", jobKey.getGroup());
                map.put("description", "触发器:" + trigger.getKey());
                Trigger.TriggerState triggerState = scheduler.getTriggerState(trigger.getKey());
                map.put("jobStatus", triggerState.name());
                if (trigger instanceof CronTrigger) {
                    CronTrigger cronTrigger = (CronTrigger) trigger;
                    String cronExpression = cronTrigger.getCronExpression();
                    map.put("jobTime", cronExpression);
                }
                jobList.add(map);
            }
        } catch (SchedulerException e) {
            log.error("查询运行中的定时任务失败", e);
            throw e;
        }

        log.info("查询运行中的定时任务成功, dataList: {}", jobList);
        return jobList;
    }


}
