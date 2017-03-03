package com.jianglibo.nutchbuilder.controller;

import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.isEmptyString;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.authentication;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.List;
import java.util.stream.Collectors;

import org.junit.Before;
import org.junit.Test;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.ResultHandler;

import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.google.common.collect.Lists;
import com.google.common.net.MediaType;
import com.jianglibo.nutchbuilder.MvcBase;


/**
 * 用来测试spring内置的update机制。也就是repositoryentitycontroller
 * 
 * @author jianglibo@gmail.com
 *         2015年9月8日
 *
 */
public class UserUpdateSpringTest extends MvcBase {

//    @Before
//    public void be() {
//        deleteAllUsers();
//    }

    @Override
    public String getPlural() {
        return "shusers";
    }
    
    @Test
    public void t() {
        assertTrue(true);
    }

}
