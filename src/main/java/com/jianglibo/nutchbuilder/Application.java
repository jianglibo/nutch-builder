package com.jianglibo.nutchbuilder;

import javax.sql.DataSource;

import org.apache.http.impl.client.HttpClientBuilder;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceBuilder;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.context.annotation.Import;
import org.springframework.context.annotation.Primary;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.data.web.config.EnableSpringDataWebSupport;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.dataformat.xml.JacksonXmlModule;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import com.jianglibo.nutchbuilder.config.ApplicationConfig;
import com.jianglibo.nutchbuilder.config.KatharsisModuleConfig;
import com.jianglibo.nutchbuilder.facade.CrawlCatFacadeRepository;
import com.jianglibo.nutchbuilder.katharsis.dto.LoginAttemptDto;
import com.jianglibo.nutchbuilder.katharsis.dto.MySiteDto;
import com.jianglibo.nutchbuilder.katharsis.dto.RoleDto;
import com.jianglibo.nutchbuilder.katharsis.dto.SiteDto;
import com.jianglibo.nutchbuilder.katharsis.dto.UserDto;

import io.katharsis.client.KatharsisClient;
import io.katharsis.client.http.apache.HttpClientAdapter;
import io.katharsis.client.http.apache.HttpClientAdapterListener;
import io.katharsis.spring.boot.v3.KatharsisConfigV3;

@SpringBootApplication
@EnableSpringDataWebSupport
@EnableJpaRepositories(basePackages="com.jianglibo.nutchbuilder.repository")
@EnableWebMvc
@EnableAsync
@EnableScheduling
@EnableAspectJAutoProxy
@Import({ KatharsisConfigV3.class, KatharsisModuleConfig.class })
@ComponentScan(basePackages={"com.jianglibo"})
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
    
	@Bean("watcherExecutor")
	public ThreadPoolTaskExecutor watcherExecutor(ApplicationConfig applicationConfig, CrawlCatFacadeRepository crawlCatRepository) {
		ThreadPoolTaskExecutor tple = new ThreadPoolTaskExecutor();
		return tple;
	}
    
    @Bean
    public KatharsisClient katharsisClient(@Value("${katharsis.domainName}") String domainName, @Value("${katharsis.pathPrefix}") String pathPrefix) {
    	
    	KatharsisClient kc = new KatharsisClient(domainName + pathPrefix);
    	HttpClientAdapter hca = (HttpClientAdapter) kc.getHttpAdapter();
    	hca.addListener(new HttpClientAdapterListener() {
			@Override
			public void onBuild(HttpClientBuilder builder) {
				System.out.println(builder);
			}
		});
    	// load resource
    	kc.getRepositoryForType(SiteDto.class);
    	kc.getRepositoryForType(UserDto.class);
    	kc.getRepositoryForType(RoleDto.class);
    	kc.getRepositoryForType(MySiteDto.class);
    	kc.getRepositoryForType(LoginAttemptDto.class);
    	return kc;
    }
	
	
    @Bean
    @Primary
    @ConfigurationProperties(prefix="spring.datasource")
    public DataSource primaryDataSource() {
        return DataSourceBuilder.create().build();
    }
    
    // pay attention on JacksonAutoConfiguration.
    
//    @Bean("xmlObjectMapper")
//    public ObjectMapper xmlObjectMapper() {
//    	JacksonXmlModule module = new JacksonXmlModule();
//    	// and then configure, for example:
////    	module.setDefaultUseWrapper();
//    	return new XmlMapper(module);
//    	// and you can also configure AnnotationIntrospectors 
//    }
    
    
//    @Bean("asyncJobLauncher")
//    public JobLauncher asyncJl(JobRepository jobRepository) {
//    	SimpleJobLauncher jl = new SimpleJobLauncher();
//    	jl.setJobRepository(jobRepository);
//    	ThreadPoolTaskExecutor tpte = new ThreadPoolTaskExecutor();
//    	jl.setTaskExecutor(new DelegatingSecurityContextAsyncTaskExecutor(tpte, SecurityContextHolder.getContext()));
//    	return jl;
//    }
//    
//    @Bean("syncJobLauncher")
//    public JobLauncher syncJl(JobRepository jobRepository) {
//    	SimpleJobLauncher jl = new SimpleJobLauncher();
//    	jl.setJobRepository(jobRepository);
//    	return jl;
//    }
}
