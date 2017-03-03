package com.jianglibo.nutchbuilder.batch;

import javax.sql.DataSource;

import org.springframework.batch.core.configuration.JobRegistry;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;

public class JobConfigurationBase {

    @Autowired
    protected JobBuilderFactory jobBuilderFactory;

    @Autowired
    protected StepBuilderFactory stepBuilderFactory;
    
    @Autowired
    protected JobRegistry jobRegistry;

    @Autowired
    protected DataSource dataSource;
    
	@Autowired
	protected ApplicationContext applicationcontext;
}
