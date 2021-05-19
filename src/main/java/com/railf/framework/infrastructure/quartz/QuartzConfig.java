package com.railf.framework.infrastructure.quartz;

import com.railf.framework.infrastructure.quartz.runner.QuartzRunner;
import com.railf.framework.infrastructure.quartz.service.QuartzService;
import com.railf.framework.infrastructure.quartz.service.QuartzServiceImpl;
import com.zaxxer.hikari.HikariDataSource;
import org.quartz.spi.JobFactory;
import org.quartz.spi.TriggerFiredBundle;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.AutowireCapableBeanFactory;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.quartz.SchedulerFactoryBean;
import org.springframework.scheduling.quartz.SpringBeanJobFactory;

import javax.sql.DataSource;
import java.util.Properties;

/**
 * @author : rain
 * @date : 2021/5/19 13:38
 */
@Configuration
@ConditionalOnProperty(prefix = "framework.quartz", value = "enable", havingValue = "true")
public class QuartzConfig {

    @Bean
    @ConditionalOnMissingBean
    public QuartzRunner quartzRunner() {
        return new QuartzRunner();
    }

    @Bean
    @ConditionalOnMissingBean
    public QuartzService quartzService() {
        return new QuartzServiceImpl();
    }

    /**
     * 配置任务工厂实例
     */
    @Bean
    @ConditionalOnMissingBean
    public JobFactory jobFactory() {
        // 采用自定义任务工厂 整合spring实例来完成构建任务
        return new AutowiringSpringBeanJobFactory();
    }

    /**
     * 配置任务调度器
     * 使用项目数据源作为quartz数据源
     *
     * @param jobFactory 自定义配置任务工厂
     * @param dataSource 数据源实例
     */
    @Bean(destroyMethod = "destroy", name = "schedulerFactoryBean")
    @ConditionalOnMissingBean
    public SchedulerFactoryBean schedulerFactoryBean(JobFactory jobFactory, DataSource dataSource) throws Exception {
        SchedulerFactoryBean schedulerFactoryBean = new SchedulerFactoryBean();
        //将spring管理job自定义工厂交由调度器维护
        schedulerFactoryBean.setJobFactory(jobFactory);
        //设置覆盖已存在的任务
        schedulerFactoryBean.setOverwriteExistingJobs(true);
        //项目启动完成后，等待2秒后开始执行调度器初始化
        schedulerFactoryBean.setStartupDelay(2);
        //设置调度器自动运行
        schedulerFactoryBean.setAutoStartup(true);
        //设置数据源，使用与项目统一数据源
        schedulerFactoryBean.setDataSource(dataSource);
        //设置上下文
        schedulerFactoryBean.setApplicationContextSchedulerContextKey("applicationContext");
        //设置配置文件位置
        schedulerFactoryBean.setQuartzProperties(configQuartzProperties());
        return schedulerFactoryBean;
    }

    private Properties configQuartzProperties() {
        Properties properties = new Properties();
        properties.put("org.quartz.threadPool.class", "org.quartz.simpl.SimpleThreadPool");
        properties.put("org.quartz.threadPool.threadCount", "10");
        properties.put("org.quartz.threadPool.threadPriority", "5");
        properties.put("org.quartz.threadPool.threadsInheritContextClassLoaderOfInitializingThread", "true");
        properties.put("org.quartz.scheduler.instanceId", "AUTO");
        properties.put("org.quartz.scheduler.rmi.export", "false");
        properties.put("org.quartz.scheduler.rmi.proxy ", "false");
        properties.put("org.quartz.jobStore.class", "org.quartz.impl.jdbcjobstore.JobStoreTX");
        properties.put("org.quartz.jobStore.driverDelegateClass", "org.quartz.impl.jdbcjobstore.StdJDBCDelegate");
        properties.put("org.quartz.jobStore.useProperties", "false");
        properties.put("org.quartz.jobStore.tablePrefix", "qrtz_");
        properties.put("org.quartz.jobStore.isClustered", "true");
        properties.put("org.quartz.jobStore.clusterCheckinInterval", "5000");
        properties.put("org.quartz.jobStore.misfireThreshold", "6000");
        return properties;
    }

    /**
     * 继承org.springframework.scheduling.quartz.SpringBeanJobFactory
     * 实现任务实例化方式
     */
    public static class AutowiringSpringBeanJobFactory extends SpringBeanJobFactory implements ApplicationContextAware {
        private transient AutowireCapableBeanFactory beanFactory;

        @Override
        public void setApplicationContext(final ApplicationContext context) {
            beanFactory = context.getAutowireCapableBeanFactory();
        }

        /**
         * 将job实例交给spring ioc托管
         * 我们在job实例实现类内可以直接使用spring注入的调用被spring ioc管理的实例
         */
        @Override
        protected Object createJobInstance(final TriggerFiredBundle bundle) throws Exception {
            final Object job = super.createJobInstance(bundle);
            // 将job实例交付给spring ioc
            beanFactory.autowireBean(job);
            return job;
        }
    }
}
