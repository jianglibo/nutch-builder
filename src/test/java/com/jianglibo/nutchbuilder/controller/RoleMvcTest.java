package com.jianglibo.nutchbuilder.controller;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.authentication;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.Test;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.security.core.Authentication;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.ResultHandler;

import com.jianglibo.nutchbuilder.MvcBase;

/**
 * @author jianglibo@gmail.com
 *         2015年8月19日
 * 
 * 
 */
public class RoleMvcTest extends MvcBase {

    /**
     * user manager可以访问。
     * 
     * @throws Exception
     */
//    @Test
//    public void testFetchAllRoles() throws Exception {
//        Authentication bu = getAdminAuthentication();
//        
//        fetchInternal(getPluralPath(), "", authentication(bu), new ResultHandler() {
//            @Override
//            public void handle(MvcResult result) throws Exception {
//                MockHttpServletResponse response = result.getResponse(); 
//                String c = response.getContentAsString();
//                printme(c);
//                jsonAssertUtil().embeddedNum(c, getPlural(), 7);
//            }
//        }, status().is2xxSuccessful());
//    }
    
    @Test
    public void create() throws Exception {
        String content = "{\"name\": \"trole\"}";
        createInternal(getPlural(), content, null, null, status().is4xxClientError());
    }


    @Override
    public String getPlural() {
        return "roles";
    }
}
