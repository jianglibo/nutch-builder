/**
 * 2016 jianglibo@gmail.com
 *
 */
package com.jianglibo.nutchbuilder.di;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;

@Configuration
public class Co {
    
    @Bean(name="romx")
    @Primary
    public ObjectMapper omx() {
        return new ObjectMapper();
    }
    
    @Bean("indentOm")
    public ObjectMapper indentOm() {
        ObjectMapper mapper = new ObjectMapper();
        mapper.configure(SerializationFeature.INDENT_OUTPUT, true);
        return mapper;
    }

}
