package com.jianglibo.nutchbuilder.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.security.SecurityProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.session.ChangeSessionIdAuthenticationStrategy;
import org.springframework.security.web.context.request.async.WebAsyncManagerIntegrationFilter;

import com.jianglibo.nutchbuilder.config.userdetail.BootUserDetailManager;
import com.jianglibo.nutchbuilder.config.userdetail.BootUserManagerConfigurer;


/**
 * 
 * @author jianglibo@gmail.com
 *
 */
@Configuration
@EnableWebSecurity
@Order(SecurityProperties.ACCESS_OVERRIDE_ORDER)
public class WebSecConfig extends WebSecurityConfigurerAdapter {

    private static Logger logger = LoggerFactory.getLogger(WebSecConfig.class);

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Value("${spring.data.rest.base-path}")
    private String basePath;
    
    @Autowired
    private ApplicationConfig applicationConfig;
    
    @Autowired
    private BootUserDetailManager bootUserManager;
    /**
     * disable default. then read father class's gethttp method. write all config your self.
     */
    public WebSecConfig() {
        super(true);
    }
    

    @Override
    @Bean
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        // @formatter:off
        BootUserManagerConfigurer<AuthenticationManagerBuilder> pc = auth.apply(new BootUserManagerConfigurer<AuthenticationManagerBuilder>(bootUserManager)).passwordEncoder(passwordEncoder);
        pc.withUser("admin")
        	.accountExpired(false)
        	.accountLocked(false)
        	.authorities("abc")
        	.credentialsExpired(false)
        	.disabled(false)
        	.displayName("admin")
        	.email("admin@localhost.com")
        	.emailVerified(true)
        	.mobile("123456789012")
        	.password("123456");
        // @formatter:on
    };

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        // @formatter:off
    	if (!applicationConfig.isDisableCsrf()) {
    		http.csrf();
    	}
		http
		.addFilter(new WebAsyncManagerIntegrationFilter())
		.exceptionHandling().and()
		.headers().and()
		.sessionManagement().and()
		.securityContext().and()
		.requestCache().and()
		.anonymous().and()
		.servletApi().and()
		.authorizeRequests()
        .antMatchers(basePath + "/**", "/login", "/", "/static/**", "/**").permitAll()
        .anyRequest().authenticated().and()
        .formLogin().loginPage("/login").and()
//        .rememberMe().and()
//		.apply(new DefaultLoginPageConfigurer<HttpSecurity>()).and()
		.logout();
//		.deleteCookies("remove").invalidateHttpSession(false)
//			.logoutUrl("/custom-logout").logoutSuccessUrl("/logout-success");
		
//        http.authorizeRequests()
//            .antMatchers(basePath + "/**", "/login").permitAll()
//            .anyRequest().authenticated();
//            .anyRequest().fullyAuthenticated();
//            .anyRequest().permitAll();
        
//            .and()
//            .logout().logoutRequestMatcher(new AntPathRequestMatcher("/logout"));
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder(11);
    }
    
    @Bean
    public ChangeSessionIdAuthenticationStrategy sessionAuthenticationStrategy() {
        return new ChangeSessionIdAuthenticationStrategy();
    }
}
