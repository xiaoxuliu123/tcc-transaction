package org.mengyun.tcctransaction.spring;

import org.mengyun.tcctransaction.recover.TransactionRecovery;
import org.mengyun.tcctransaction.spring.support.SpringTransactionConfigurator;
import org.quartz.Scheduler;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.concurrent.ThreadPoolTaskScheduler;


@Configuration
@ComponentScan
//@EnableScheduling
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
    public ThreadPoolTaskScheduler threadPoolTaskScheduler() {
        return new ThreadPoolTaskScheduler();
    }

//    @Bean
//    public RecoverScheduledJob recoverScheduledJob(){
//        RecoverScheduledJob recoverScheduledJob = new RecoverScheduledJob();
//        recoverScheduledJob.setTransactionConfigurator(springTransactionConfigurator());
//        recoverScheduledJob.setThreadPoolTaskScheduler(threadPoolTaskScheduler());
//        recoverScheduledJob.setTransactionRecovery(transactionRecovery());
//        return recoverScheduledJob;
//    }
}
