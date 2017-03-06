package com.jianglibo.nutchbuilder.controller;

import static org.hamcrest.Matchers.isEmptyString;
import static org.junit.Assert.assertThat;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.authentication;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.ResultHandler;

import com.fasterxml.jackson.databind.node.ObjectNode;
import com.google.common.net.MediaType;
import com.jianglibo.nutchbuilder.MvcBase;
import com.jianglibo.nutchbuilder.util.LinkUtil;

/**
 * @author jianglibo@gmail.com
 * 
 */
public class PersonFetchTest extends MvcBase {
    
    @Autowired
    private LinkUtil linkUtil;

//    @Before
//    public void b() {
//        deleteAllUsers();
//    }

    /**
     * 
     * @throws Exception
     */
//    @Test
//    public void testFetchUsersDenied() throws Exception {
//        fetchInternal(getPluralPath(), "", authentication(getAuthentication("auser")), new ResultHandler() {
//            @Override
//            public void handle(MvcResult result) throws Exception {
//                String c = result.getResponse().getContentAsString();
//                assertThat(c, isEmptyString());
//            }
//        }, status().is4xxClientError());
//    }

    /**
     * 
     * @throws Exception
     */
//    @Test
//    public void testFetchOnePersonDenied() throws Exception {
//        M3958LoginAuthenticationToken auth = (M3958LoginAuthenticationToken) getAuthentication("auser");
//
//        fetchInternal(getOneItemUrl(auth.getPrincipal().getId()), "", authentication(getAuthentication("buser")), new ResultHandler() {
//            @Override
//            public void handle(MvcResult result) throws Exception {
//                String c = result.getResponse().getContentAsString();
//                assertThat(c, isEmptyString());
//            }
//        }, status().is4xxClientError());
//    }

    /**
     * 
     * @throws Exception
     */
    @Test
    public void testFetchOnePersonRedirect() throws Exception {
        fetchInternal(getOneItemUrl(55), "", null, null, status().is3xxRedirection());
    }

    /**
     * 
     * @throws Exception
     */
//    @Test
//    public void testFetchUsersAllowed() throws Exception {
//        fetchInternal(getPluralPath(), "", authentication(getAdminAuthentication()), new ResultHandler() {
//            @Override
//            public void handle(MvcResult result) throws Exception {
//                String c = result.getResponse().getContentAsString();
////                printme(c);
//                jsonAssertUtil().embeddedNum(c, getPlural(), 1);
//                
//                jsonAssertUtil().noKey(jsonUtil().getEmbeddedFirst(c, getPlural()), "id");
//                jsonAssertUtil().hasLinks(c, "self", "profile", "search");
//                
//                jsonAssertUtil().noKey(jsonUtil().getEmbeddedAt(c, getPlural(), 0), "id");
//
//                ObjectNode person = jsonUtil().getEmbeddedFirst(c, getPlural());
//                jsonAssertUtil().hasLinks(person, "self", "person", "roles", "thirdConns");
//            }
//        });
//    }

    /*
     * ��/people?id=xxx&projection=xxx�ĸ�ʽ���ʡ�Ϊ�˼���emberjs data�ĸ��Ի����룬ֱ��ʹ��emberjsdata����ơ�
     */
//    @Test
//    public void testEmberQueryOneRecord() throws Exception {
//        M3958LoginAuthenticationToken auth = (M3958LoginAuthenticationToken) getAuthentication("auser");
//
//        /**
//         * {
//         * "createdAt" : "2015-12-15T05:56:29.203+0000",
//         * "displayName" : null,
//         * "avatar" : null,
//         * "emailVerified" : false,
//         * "mobileVerified" : false,
//         * "level" : 0,
//         * "gender" : "FEMALE",
//         * "name" : "auser",
//         * "email" : "auser@m3958.com",
//         * "mobile" : null,
//         * "accountNonExpired" : true,
//         * "accountNonLocked" : true,
//         * "credentialsNonExpired" : true,
//         * "enabled" : true,
//         * "_links" : {
//         * "self" : {
//         * "href" : "http://localhost/api/v1/people/13"
//         * },
//         * "person" : {
//         * "href" : "http://localhost/api/v1/people/13{?projection}",
//         * "templated" : true
//         * },
//         * "thirdConns" : {
//         * "href" : "http://localhost/api/v1/people/13/thirdConns"
//         * },
//         * "roles" : {
//         * "href" : "http://localhost/api/v1/people/13/roles"
//         * }
//         * }
//         * }
//         */
//        /**
//         * ��û��projection������£����᷵��ID��
//         */
//        mvc.perform(get(getPluralPath())//
//                .param("id", auth.getPrincipal().getId() + "")//
//                .contentType(MediaType.JSON_UTF_8.toString())//
//                .accept(MediaType.JSON_UTF_8.toString())//
//                .with(authentication(auth)))//
//                .andExpect(status().is2xxSuccessful())//
//                .andDo(new ResultHandler() {
//                    @Override
//                    public void handle(MvcResult result) throws Exception {
//                        contentTypeShoudBeApplicationJson(result);
//                        String c = result.getResponse().getContentAsString();
////                        printme(c);
//                        jsonAssertUtil().noAllKeys(c, "roles", "id"); // û��Я��roles
//                        jsonAssertUtil().hasLinks(c, "roles", "self", "person", "thirdConns");
//                    }
//                });
//
//        mvc.perform(get(getPluralPath())//
//                .param("id", auth.getPrincipal().getId() + "")//
//                .param("projection", "person-roles")// ����projection����ȡ����ϸ�����ݡ�
//                .contentType(MediaType.JSON_UTF_8.toString())//
//                .accept(MediaType.JSON_UTF_8.toString())//
//                .with(authentication(auth)))//
//                .andExpect(status().is2xxSuccessful())//
//                .andDo(new ResultHandler() {
//                    @Override
//                    public void handle(MvcResult result) throws Exception {
//                        contentTypeShoudBeApplicationJson(result);
//                        String c = result.getResponse().getContentAsString();
////                        printme(c);
//                        jsonAssertUtil().hasKey(c, "roles"); // Я��roles
//                        jsonAssertUtil().hasLinks(c, "roles", "self", "person", "thirdConns");
//                    }
//                });
//
//    }

//    @Test
//    public void testQUsers() throws Exception {
//        
//        M3958LoginAuthenticationToken auth = (M3958LoginAuthenticationToken) getAuthentication("auser");
//
//        mvc.perform(get(getPluralPath()).param("q", "a=b")//
//                .contentType(MediaType.JSON_UTF_8.toString())//
//                .accept(MediaType.JSON_UTF_8.toString())//
//                .with(authentication(auth)))//
//                .andExpect(status().is2xxSuccessful())//
//                .andDo(new ResultHandler() {
//                    @Override
//                    public void handle(MvcResult result) throws Exception {
//                        
//                        contentTypeShoudBeApplicationJson(result);
//                        String c = result.getResponse().getContentAsString();
////                        printme(c);
//                        
//                        ObjectNode on = jsonUtil().getEmbeddedFirst(c, getPlural());
//                        
//                        jsonAssertUtil().noKey(on, "id");
//                        
//                        String content = on.toString();
//                        
//                        long id = linkUtil.extractIdFromHref(on, "self");
//
//                        // �����û������У��ֶβ�ȫ��version�ֶ�û�С�
//                        mvc.perform(put(getOneItemUrl(id) + "/update").with(csrf()).with(authentication(auth))//
//                                .contentType(MediaType.JSON_UTF_8.toString()).content(content))//
//                                .andExpect(status().is2xxSuccessful()).andDo(new ResultHandler() {
//
//                            @Override
//                            public void handle(MvcResult result) throws Exception {
//                                String c = result.getResponse().getContentAsString();
//                                assertThat(c, isEmptyString());
//                            }
//                        });
//                    }
//                });
//    }

    @Override
    public String getPlural() {
        return "people";
    }

}
