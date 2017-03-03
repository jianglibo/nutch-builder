package com.jianglibo.nutchbuilder.controller;


import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.Test;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.stereotype.Controller;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.ResultHandler;

import com.jianglibo.nutchbuilder.MvcBase;


@Controller
public class UriPatternControllerTest extends MvcBase {
    
    
    @Test
    public void testMatched() throws Exception {
        matched("mconsole");
        matched("mconsole/view");
        matched("mconsole/view?q=u");
        matched("mconsole/edit/1");
    }
    
    @Test
    public void testNotMatched() throws Exception {
        notMatched("mconsole.html");
        notMatched("mconsole/view.do");
        notMatched("mconsole/view.?q=u");
        notMatched("mconsole/edit/1.kk?u=v");
    }
    
    private void matched(String url) throws Exception {
        fetchInternal("/llapp/kkk/" + url, "", null, new ResultHandler() {
            @Override
            public void handle(MvcResult result) throws Exception {
                MockHttpServletResponse response = result.getResponse(); 
                String c = response.getContentAsString();
                String path = url.split("\\?")[0];
                assertThat(path.endsWith(c), is(true));
            }
        }, status().is2xxSuccessful());
    }
    
    private void notMatched(String url) throws Exception {
        fetchInternal("/llapp/kkk/" + url, "", null,null , status().is4xxClientError());
    }

    @Override
    public String getPlural() {
        return null;
    }
    
}
