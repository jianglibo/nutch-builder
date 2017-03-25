package com.jianglibo.nutchbuilder.hbaserest;

import org.apache.http.HttpHost;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import org.springframework.http.client.support.BasicAuthorizationInterceptor;
import org.springframework.web.client.RestTemplate;

import com.jianglibo.nutchbuilder.config.ApplicationConfig;

@Configuration
public class HbaseRestConfig {

    @Autowired
    private ApplicationConfig applicationConfig;
    
    @Bean(name="hbaseRestTemplate")
    public RestTemplate hbaseRestTemplate() {
        HttpHost host = new HttpHost(applicationConfig.getHbaseRestHost(), applicationConfig.getHbaseRestPort(), applicationConfig.getHbaseRestProtocol());
        RestTemplate restTemplate = new RestTemplate(
          new HttpComponentsClientHttpRequestFactoryBasicAuth(host));
        restTemplate.getInterceptors().add(
        		  new BasicAuthorizationInterceptor("username", "password"));
        restTemplate.getInterceptors().add(
      		  new HeaderRequestInterceptor("Accept", MediaType.APPLICATION_JSON_VALUE));
        restTemplate.getInterceptors().add(
        		  new HeaderRequestInterceptor("Content-Type", MediaType.APPLICATION_JSON_VALUE));

        return restTemplate;
    }
}
