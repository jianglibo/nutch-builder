package com.jianglibo.nutchbuilder;

import javax.sql.DataSource;

import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.batch.core.launch.support.SimpleJobLauncher;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceBuilder;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.context.annotation.Primary;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.data.web.config.EnableSpringDataWebSupport;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.task.DelegatingSecurityContextAsyncTaskExecutor;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

@SpringBootApplication
@EnableSpringDataWebSupport
@EnableJpaRepositories(basePackages="com.jianglibo.nutchbuilder.repository")
@EnableWebMvc
@EnableBatchProcessing
@EnableAsync
@EnableScheduling
@EnableAspectJAutoProxy
public class Application {

    public static void main(String[] args) {
        ApplicationContext ctx = SpringApplication.run(Application.class, args);

//        System.out.println("Let's inspect the beans provided by Spring Boot:");
//
//        String[] beanNames = ctx.getBeanDefinitionNames();
//        Arrays.sort(beanNames);
//        for (String beanName : beanNames) {
//            System.out.println(beanName);
//        }
//        System.out.println(beanNames.length);
    }
    
//    see: MessageSourceAutoConfiguration
    
//    @Bean
//    public MessageSource messageSource() {
//    	ResourceBundleMessageSource parent = new ResourceBundleMessageSource();
//    	parent.setBasename("messages.shared");
//    	ResourceBundleMessageSource rbm = new ResourceBundleMessageSource();
//    	rbm.setParentMessageSource(parent);
//    	rbm.setBasenames("messages.children.format", "messages.children.validate");
//    	return rbm;
//    }
    
    @Bean
    @Primary
    @ConfigurationProperties(prefix="spring.datasource")
    public DataSource primaryDataSource() {
        return DataSourceBuilder.create().build();
    }
    
    
    @Bean("asyncJobLauncher")
    public JobLauncher asyncJl(JobRepository jobRepository) {
    	SimpleJobLauncher jl = new SimpleJobLauncher();
    	jl.setJobRepository(jobRepository);
    	ThreadPoolTaskExecutor tpte = new ThreadPoolTaskExecutor();
    	jl.setTaskExecutor(new DelegatingSecurityContextAsyncTaskExecutor(tpte, SecurityContextHolder.getContext()));
    	return jl;
    }
    
    @Bean("syncJobLauncher")
    public JobLauncher syncJl(JobRepository jobRepository) {
    	SimpleJobLauncher jl = new SimpleJobLauncher();
    	jl.setJobRepository(jobRepository);
    	return jl;
    }
}
