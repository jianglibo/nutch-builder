package com.jianglibo.nutchbuilder.controller;

import static org.hamcrest.CoreMatchers.notNullValue;
import static org.junit.Assert.assertThat;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.Before;
import org.junit.Test;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.ResultHandler;

import com.jianglibo.nutchbuilder.MvcBase;

public class SignupControllerTest extends MvcBase {
    
//    @Before
//    public void b() {
//        deleteAllUsers();
//    }
    
//    @Test
//    public void signup() throws Exception {
//        
//        String email = "signup@m3958.com";
//        String name = "signup";
//        
//        SignupVo sv = new SignupVo(name, email, null, "signup001");
//        
//        String c = getObjectMapper().writeValueAsString(sv);
//
//        createInternal("/login/signup", c, null, new ResultHandler() {
//            @Override
//            public void handle(MvcResult result) throws Exception {
//                String c = result.getResponse().getContentAsString();
//                printme(c);
//                Person p = personRepo.findByEmail(email);
//                assertThat(p, notNullValue());
//            }
//        }, status().is(200));
//    }

    @Override
    public String getPlural() {
        return null;
    }

}
