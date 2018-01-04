package org.mengyun.tcctransaction.spring;

import org.mengyun.tcctransaction.recover.TransactionRecovery;
import org.mengyun.tcctransaction.spring.recover.RecoverScheduledJob;
import org.mengyun.tcctransaction.spring.support.SpringTransactionConfigurator;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.SchedulerFactory;
import org.quartz.impl.StdScheduler;
import org.quartz.impl.StdSchedulerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.concurrent.ThreadPoolTaskScheduler;
import org.springframework.scheduling.quartz.SchedulerFactoryBean;


@Configuration
@ComponentScan
@EnableScheduling
public class TccTransactionAutoConfiguration {

    @Bean
    public SpringTransactionConfigurator springTransactionConfigurator(){
        return new SpringTransactionConfigurator();
    }

    @Bean
    public ConfigurableTransactionAspect configurableTransactionAspect(){
        ConfigurableTransactionAspect configurableTransactionAspect = new ConfigurableTransactionAspect();
        configurableTransactionAspect.setTransactionConfigurator(springTransactionConfigurator());
        return configurableTransactionAspect;
    }

    @Bean
    public ConfigurableCoordinatorAspect configurableCoordinatorAspect(){
        ConfigurableCoordinatorAspect configurableCoordinatorAspect = new ConfigurableCoordinatorAspect();
        configurableCoordinatorAspect.setTransactionConfigurator(springTransactionConfigurator());
        return configurableCoordinatorAspect;
    }

    @Bean
    public  TransactionRecovery transactionRecovery(){
        TransactionRecovery transactionRecovery = new TransactionRecovery();
        transactionRecovery.setTransactionConfigurator(springTransactionConfigurator());
        return transactionRecovery;
    }

    //开启定时器支持
    @Bean
    public Scheduler scheduler() throws SchedulerException {
        return StdSchedulerFactory.getDefaultScheduler();
    }

    @Bean
    public RecoverScheduledJob recoverScheduledJob() throws SchedulerException {
        RecoverScheduledJob recoverScheduledJob = new RecoverScheduledJob();
        recoverScheduledJob.setTransactionConfigurator(springTransactionConfigurator());
        recoverScheduledJob.setScheduler(scheduler());
        recoverScheduledJob.setTransactionRecovery(transactionRecovery());
        return recoverScheduledJob;
    }
}
