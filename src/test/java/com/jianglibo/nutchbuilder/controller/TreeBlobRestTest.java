package com.jianglibo.nutchbuilder.controller;

import static org.hamcrest.Matchers.notNullValue;
import static org.junit.Assert.assertThat;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.authentication;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.io.IOException;
import java.util.Map;

import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.ResultHandler;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.google.common.net.HttpHeaders;
import com.jianglibo.nutchbuilder.MvcBase;
import com.jianglibo.nutchbuilder.vo.JavaTypeHolder;


public class TreeBlobRestTest extends MvcBase {

    
    @Autowired
    private JavaTypeHolder javaTypeHolder;

//    @Before
//    public void be() {
//        loginAs("system", RoleNames.SYSTEM_MANAGER);
//        tbRepo.deleteAll();
//        logout();
//    }
//
//    @Test
//    public void create() throws Exception {
//        String tn = "tree-one";
//        TreeBlob tb = new TreeBlob(tn);
//        String c = getObjectMapper().writeValueAsString(tb);
//        createInternal(getPluralPath(), c, authentication(getAuthentication("system", RoleNames.SYSTEM_MANAGER)), new ResultHandler() {
//            @Override
//            public void handle(MvcResult result) throws Exception {
//                String location = result.getResponse().getHeader(HttpHeaders.LOCATION);
//                assertThat("location should be exists", location, notNullValue());
//                
//                String c = result.getResponse().getContentAsString();
//                
//                printme(c);
//                
//                jsonAssertUtil().hasAllKeys(c, "createdAt", "name", "treeContent");
//                jsonAssertUtil().noKey(c, "id");
//
//                loginAs("system", RoleNames.SYSTEM_MANAGER);
//                TreeBlob tb1 = tbRepo.findByName("tree-one");
//
//                Tree<Map<String, String>> it = getObjectMapper().readValue(tb1.getTreeContent(), javaTypeHolder.getTreeMapStringString());
//
//                it.getRoot().addChild(new TreeNode<>(nsn.getNextSeqId(), "ol"));
//                it.getRoot().getChildren().get(0).addChild(new TreeNode<>(nsn.getNextSeqId(), "tl"));
//
//                tb1.setTreeContent(getObjectMapper().writeValueAsString(it));
//
//                tbRepo.save(tb1);
//
//                logout();
//
//                fetchInternal(getPluralPath() + "?q=true&name=" + tn, null, authentication(getOrdinaryAuthentication()), new ResultHandler() {
//                    @Override
//                    public void handle(MvcResult result) throws Exception {
//                        String c = result.getResponse().getContentAsString();
//                        printme(c);
//                        jsonAssertUtil().embeddedNum(c, getPlural(), 1);
//                        ObjectNode jn = jsonUtil().getEmbeddedFirst(c, getPlural());
//
//                        jsonAssertUtil().hasLinks(jn, "self", "treeBlob");
//                        jsonAssertUtil().hasAllKeys(jn, "createdAt", "name", "treeContent");
//                    }
//                }, status().is2xxSuccessful());
//
//                fetchInternal(getPluralPath(), "treeblob-simple", authentication(getOrdinaryAuthentication()), new ResultHandler() {
//
//                    @Override
//                    public void handle(MvcResult result) throws Exception {
//                        String c = result.getResponse().getContentAsString();
//                        jsonAssertUtil().embeddedNum(c, getPlural(), 1);
//                        ObjectNode jn = jsonUtil().getEmbeddedFirst(c, getPlural());
//                        
//                        printme(c);
//
//                        jsonAssertUtil().hasLinks(jn, "self", "treeBlob");
//                        jsonAssertUtil().hasAllKeys(jn, "name", "id", "localedName", "description");
//                        jsonAssertUtil().noKey(c, "treeContent");
//                        jsonAssertUtil().noKey(c, "createdAt");
//                    }
//                }, status().is2xxSuccessful());
//
//                fetchInternal(getPluralPath() + "/search/nameIs?name=" + tn, null, authentication(getOrdinaryAuthentication()), new ResultHandler() {
//
//                    @Override
//                    public void handle(MvcResult result) throws Exception {
//                        String c = result.getResponse().getContentAsString();
//                        printme(c);
//                        JsonNode jn = getObjectMapper().readTree(c);
//
//                        jsonAssertUtil().hasLinks(jn, "self", "treeBlob");
//                        jsonAssertUtil().hasAllKeys(jn, "name", "treeContent", "createdAt");
//                    }
//                }, status().is2xxSuccessful());
//            }
//        }, status().is(201));
//    }
//
//    /**
//     * update url is /treenodes/treeid/update
//     * @throws Exception
//     */
//    @Test
//    public void update() throws Exception {
//        loginAs("system", RoleNames.SYSTEM_MANAGER);
//        String tn = "tree-one";
//        TreeBlob tb = new TreeBlob(tn);
//        tbRepo.save(tb);
//        logout();
//
//        updateInternal("/treenodes/" + tb.getId() + "/update", createAddChild(tb).toString(), authentication(getOrdinaryAuthentication()), new ResultHandler() {
//
//            @Override
//            public void handle(MvcResult result) throws Exception {
//                String c = result.getResponse().getContentAsString();
//                printme(c);
//            }
//        }, status().is2xxSuccessful());
//    }
//
//    private ObjectNode createAddChild(TreeBlob tb) throws JsonParseException, JsonMappingException, IOException {
//        TreeUpdateAction<Map<String, String>> tua = new TreeUpdateAction<>();
//        Tree<Map<String, String>> mvt = getObjectMapper().readValue(tb.getTreeContent(), javaTypeHolder.getTreeMapStringString());
//        tua.setAction(TreeUpdateActionName.ADD);
//        tua.setNodeId(mvt.getRoot().getId());
//        tua.setName("achild");
//        ObjectNode on = getObjectMapper().valueToTree(tua);
//        return on;
//    }

    @Override
    public String getPlural() {
        return "trees";
    }

}
