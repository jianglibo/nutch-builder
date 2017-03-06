package com.jianglibo.nutchbuilder.controller;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.authentication;

import org.junit.Before;
import org.junit.Test;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.ResultHandler;

import com.fasterxml.jackson.databind.node.ObjectNode;
import com.jianglibo.nutchbuilder.MvcBase;


/**
 * @author jianglibo@gmail.com
 * 
 */
public class UserProjectionsTest extends MvcBase {

//    @Before
//    public void b() {
//        deleteAllUsers();
//    }
//
//    /**
//     * @throws Exception
//     */
//    @Test
//    public void testFetchUsers() throws Exception {
//        fetchInternal(getPluralPath(), "shuser-roles", //
//                authentication(getUserBauthentication()), //
//                new ResultHandler() {
//                    @Override
//                    public void handle(MvcResult result) throws Exception {
//                        String c = result.getResponse().getContentAsString();
//                        printme(c);
//
//                        jsonAssertUtil().embeddedNum(c, getPlural(), 1); // buser
//                        ObjectNode on = jsonUtil().getEmbeddedFirst(c, getPlural());
//                        jsonAssertUtil().hasKey(on, "roles");
//                    }
//                });
//    }
//
//    /**
//     * use shuser-roles projection
//     * 
//     * @throws Exception
//     */
//    @Test
//    public void testFetchOneUserWithProjection() throws Exception {
//        fetchInternal(getOneItemUrl(getBuser().getId()), "shuser-roles", //
//                authentication(getUserBauthentication()), //
//                new ResultHandler() {
//                    @Override
//                    public void handle(MvcResult result) throws Exception {
//                        String c = result.getResponse().getContentAsString();
//                        printme(c);
//                        jsonAssertUtil().hasAllKeys(c, "roles", "quota");
//                    }
//                });
//    }
//
//    /**
//     * no projection
//     * 
//     * @throws Exception
//     */
//    @Test
//    public void testFetchOneUser() throws Exception {
//        fetchInternal(getOneItemUrl(getBuser().getId()), "", //
//                authentication(getUserBauthentication()), //
//                new ResultHandler() {
//                    @Override
//                    public void handle(MvcResult result) throws Exception {
//                        String c = result.getResponse().getContentAsString();
//                        printme(c);
//                        jsonAssertUtil().noKey(c, "roles");
//                        jsonAssertUtil().noKey(c, "quota");
//                    }
//                });
//    }

    @Override
    public String getPlural() {
        return "shusers";
    }

}
