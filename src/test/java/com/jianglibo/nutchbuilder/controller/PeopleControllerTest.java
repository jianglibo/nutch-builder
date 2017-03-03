package com.jianglibo.nutchbuilder.controller;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.header;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.Test;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.ResultHandler;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.jianglibo.nutchbuilder.MvcBase;

/**
 * @author jianglibo@gmail.com
 *         2015年12月24日
 *
 */
public class PeopleControllerTest extends MvcBase {
    
    
//    @Test
//    public void createUser() throws Exception {
//        Person person = new Person();
//        person.setName("aaa");
//        person.setEmail("aaa@bb.cc");
//        person.setPassword("123456");
//        String c = getObjectMapper().writeValueAsString(person);
//        
//        createInternal(getPluralPath(), c, null, new ResultHandler() {
//            @Override
//            public void handle(MvcResult result) throws Exception {
//                printHeaders(result);
//            }
//        }, status().is(201));
//    }

    /* (non-Javadoc)
     * @see com.m3958.logcollector.MvcBase#getPlural()
     */
    @Override
    public String getPlural() {
        return "people";
    }

}
