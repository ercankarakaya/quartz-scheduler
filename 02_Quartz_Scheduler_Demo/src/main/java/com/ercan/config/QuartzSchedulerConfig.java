package com.ercan.config;

import com.ercan.service.GlobalJobListener;
import com.ercan.service.GlobalTriggerListener;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.config.PropertiesFactoryBean;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.scheduling.quartz.SchedulerFactoryBean;

import javax.sql.DataSource;
import java.io.IOException;
import java.util.Properties;

@Configuration
@AllArgsConstructor
public class QuartzSchedulerConfig {

    private DataSource dataSource;
    private ApplicationContext applicationContext;
    private GlobalTriggerListener triggerListener;
    private GlobalJobListener jobsListener;


    /**
     * Create Scheduler
     */
    @Bean
    public SchedulerFactoryBean schedulerFactoryBean() throws IOException {
        SchedulerFactoryBean factoryBean = new SchedulerFactoryBean();
        factoryBean.setOverwriteExistingJobs(true);
        factoryBean.setDataSource(dataSource);
        factoryBean.setQuartzProperties(quartzProperties());

        // Register listeners to get notification on Trigger misfire...
        factoryBean.setGlobalTriggerListeners(triggerListener);
        factoryBean.setGlobalJobListeners(jobsListener);

        AutowiringSpringBeanJobFactory jobFactory = new AutowiringSpringBeanJobFactory();
        jobFactory.setApplicationContext(applicationContext);

        factoryBean.setJobFactory(jobFactory);

        return factoryBean;
    }

    /**
     * Configure quartz using properties file
     */
    @Bean
    public Properties quartzProperties() throws IOException {
        PropertiesFactoryBean propertiesFactoryBean = new PropertiesFactoryBean();
        propertiesFactoryBean.setLocation(new ClassPathResource("/quartz.properties"));
        propertiesFactoryBean.afterPropertiesSet();
        return propertiesFactoryBean.getObject();
    }

}
