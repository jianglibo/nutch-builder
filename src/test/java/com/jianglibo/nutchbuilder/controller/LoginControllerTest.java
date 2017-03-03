package com.jianglibo.nutchbuilder.controller;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.authentication;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.Before;
import org.junit.Test;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.security.core.Authentication;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.ResultHandler;

import com.jianglibo.nutchbuilder.MvcBase;


public class LoginControllerTest extends MvcBase {
    
    @Before
    public void b() {
//        deleteAllUsers();
    }

    @Test
    public void tAnonymousState() throws Exception {
        fetchInternal("/login/state", "", null, new ResultHandler() {
            @Override
            public void handle(MvcResult result) throws Exception {
                // {"logined":false,"user":null,"allRoles":[]}
                MockHttpServletResponse response = result.getResponse();
                String c = response.getContentAsString();
                printme(c);
                jsonAssertUtil().hasAllKeys(c, "logined", "user", "allRoles");
                jsonAssertUtil().isNullValue(c, "user");
                jsonAssertUtil().isFalseValue(c, "logined");
                jsonAssertUtil().isArrayValue(c, "allRoles");
            }
        }, status().is2xxSuccessful());
    }

//    @Test
//    public void tUserState() throws Exception {
//        Authentication bu = getAdminAuthentication();
//        fetchInternal("/login/state", "", authentication(bu), new ResultHandler() {
//            @Override
//            public void handle(MvcResult result) throws Exception {
//                MockHttpServletResponse response = result.getResponse();
//                String c = response.getContentAsString();
//                // {"logined":true,"user":{"id":1,"email":"admin@m3958.com","mobile":null,"name":"admin","displayName":"admin","roles":[{"id":1,"name":"ROLE_PERSON_MANAGER"},{"id":2,"name":"ROLE_USER"}]},"allRoles":[{"id":2,"name":"ROLE_USER"},{"id":4,"name":"ROLE_WORKER"},{"id":8,"name":"ROLE_REPORTER"},{"id":6,"name":"ROLE_APP_DATA_CHANGE_FIX_RATE"},{"id":5,"name":"ROLE_SCANAPI_CLIENT"},{"id":7,"name":"ROLE_NOT_EXIST"},{"id":1,"name":"ROLE_PERSON_MANAGER"}]}
//                printme(c);
//                jsonAssertUtil().hasAllKeys(c, "logined", "user", "allRoles");
//                jsonAssertUtil().isObjectValue(c, "user");
//                jsonAssertUtil().isTrueValue(c, "logined");
//                jsonAssertUtil().isArrayValue(c, "user", "roles");
//                jsonAssertUtil().isArrayValue(c, "allRoles");
//            }
//        }, status().is2xxSuccessful());
//    }

    /**
     * csrf().
     * 
     * @throws Exception
     */
    @Test
//    public void loginUserOK() throws Exception {
//        createFullPerson("admin", "admin");
//        LoginAttemptVo latvo = new LoginAttemptVo("admin", "admin");
//        String content = getObjectMapper().writeValueAsString(latvo);
//        mvc.perform(post("/login/do").contentType(MediaType.APPLICATION_JSON).content(content).with(csrf())).andExpect(status().isOk())
//                .andDo(new ResultHandler() {
//                    @Override
//                    public void handle(MvcResult result) throws Exception {
//                        String c = result.getResponse().getContentAsString();
//                        printme(c);
//                        // {"state":"FULL","message":"NO_MESSAGE","wtg":null,"showCaptcha":false,"userState":{"logined":true,"user":{"id":1,"email":"admin@m3958.com","mobile":null,"name":"admin","displayName":"admin","roles":[{"id":1,"name":"ROLE_PERSON_MANAGER"},{"id":2,"name":"ROLE_USER"}]},"allRoles":[{"id":5,"name":"ROLE_NOT_EXIST"},{"id":1,"name":"ROLE_PERSON_MANAGER"},{"id":4,"name":"ROLE_APP_DATA_CHANGE_FIX_RATE"},{"id":2,"name":"ROLE_USER"}]}}
//                        jsonAssertUtil().hasAllKeys(c, "state", "message", "wtg", "showCaptcha", "userState");
//                        jsonAssertUtil().stringValueIs(c, "FULL", "state");
//                        jsonAssertUtil().stringValueIs(c, "NO_MESSAGE", "message");
//                        jsonAssertUtil().isNullValue(c, "wtg");
//                        jsonAssertUtil().isObjectValue(c, "userState");
//                    }
//                });
//    }
    
//    @Test
//    public void loginUserFail() throws Exception {
//        LoginAttemptVo latvo = new LoginAttemptVo("admin", "admin1");
//        String content = getObjectMapper().writeValueAsString(latvo);
//        mvc.perform(post("/login/do").contentType(MediaType.APPLICATION_JSON).content(content).with(csrf())).andExpect(status().isOk())
//                .andDo(new ResultHandler() {
//                    @Override
//                    public void handle(MvcResult result) throws Exception {
//                        String c = result.getResponse().getContentAsString();
//                        printme(c);
//                        // {"state":"ANONYMOUS","message":"USERNAME_PASSWORD_NOT_MATCH","wtg":null,"showCaptcha":false,"userState":null}
//                        jsonAssertUtil().hasAllKeys(c, "state", "message", "wtg", "showCaptcha", "userState");
//                        jsonAssertUtil().stringValueIs(c, "ANONYMOUS", "state");
//                        jsonAssertUtil().stringValueIs(c, LoginResultMessage.USERNAME_PASSWORD_NOT_MATCH.toString(), "message");
//                        jsonAssertUtil().isNullValue(c, "wtg");
//                        jsonAssertUtil().isNullValue(c, "userState");
//                    }
//                });
//    }


    @Override
    public String getPlural() {
        return null;
    }

}
