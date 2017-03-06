package com.jianglibo.nutchbuilder;

import java.io.UnsupportedEncodingException;

import org.springframework.test.web.servlet.MvcResult;

import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * @author jianglibo@gmail.com
 *
 */
public abstract class M3958TsBase {
    
    protected static String EMBEDDED = "_embedded";
    
    public abstract ObjectMapper getObjectMapper();
    
    public abstract String getRestUri(String withoutBase);

    public JsonUtil jsonUtil() {
        return new JsonUtil(getObjectMapper());
    }
    
    public JsonAssertUtil jsonAssertUtil() {
        JsonUtil jsonUtil = new JsonUtil(getObjectMapper());
        return new JsonAssertUtil(getObjectMapper(), jsonUtil);
    }
    
    public void printme(Object o) {
        System.out.println(o);
    }
    
    public void printme(Object o, Object o1) {
        System.out.print(o);
        System.out.print(":");
        System.out.println(o1);
    }
    

    public String loadResourceInTbrsPackage(String rn) {
        return ResourceLoader.load("cc/openscanner/tbrs/" + rn);
    }
    
    
    public void printHeaders(MvcResult result) {
        result.getResponse().getHeaderNames().forEach(hn -> {
            printme(hn + ":" + result.getResponse().getHeaderValue(hn));
        });
        
    }
    public void printContent(MvcResult result) throws UnsupportedEncodingException {
        printme(result.getResponse().getContentAsString());
        
    }
}
