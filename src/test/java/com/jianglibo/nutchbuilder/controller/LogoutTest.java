package com.jianglibo.nutchbuilder.controller;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.ResultHandler;

import com.google.common.net.HttpHeaders;
import com.jianglibo.nutchbuilder.MvcBase;
import com.jianglibo.nutchbuilder.config.ApplicationConfig;

public class LogoutTest extends MvcBase {
    
    @Autowired
    private ApplicationConfig appConfig;
    
//    @Test
//    public void t() throws Exception {
//        mvc.perform(get("/logout")).andExpect(status().is3xxRedirection()).andDo(new ResultHandler() {
//            @Override
//            public void handle(MvcResult result) throws Exception {
//                printHeaders(result);
//                String location = result.getResponse().getHeader(HttpHeaders.LOCATION);
//                assertThat("redirect location should be right", location, is(appConfig.getLogoutSuccessUrl()));
//            }
//        });
//    }

    @Override
    public String getPlural() {
        return null;
    }

}
