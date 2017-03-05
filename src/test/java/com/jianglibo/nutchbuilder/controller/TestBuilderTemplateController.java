package com.jianglibo.nutchbuilder.controller;

import static org.hamcrest.Matchers.equalTo;
import static org.junit.Assert.assertThat;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.attoparser.trace.MarkupTraceEvent.ProcessingInstructionTraceEvent;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.ResultHandler;

import com.jianglibo.nutchbuilder.MvcBase;
import com.jianglibo.nutchbuilder.domain.NutchBuilderTemplate;
import com.jianglibo.nutchbuilder.repository.NutchBuilderTemplateRepository;


public class TestBuilderTemplateController extends MvcBase {
    
	@Autowired
	private NutchBuilderTemplateRepository nbtRepo;
	
	@Before
	public void b() {
		nbtRepo.deleteAll();
	}
    
    @Test
    public void createNewNoFolder() throws Exception {
    	assertThat("there should be no item", nbtRepo.count(), equalTo(0L));
        NutchBuilderTemplate  nbt = new NutchBuilderTemplate();
        nbt.setName("aaa");
        
        String content = getObjectMapper().writeValueAsString(nbt);
        
        createInternal(getPluralPath(), content, null, new ResultHandler() {
            @Override
            public void handle(MvcResult result) throws Exception {
                printHeaders(result);
                printContent(result);
                long count = nbtRepo.count();
                assertThat("there should be no item", count, equalTo(0L));
            }
        }, status().is(201));
    }
    
    
    @Test
    public void createNew() throws Exception {
    	assertThat("there should be no item", nbtRepo.count(), equalTo(0L));
        NutchBuilderTemplate  nbt = new NutchBuilderTemplate();
        nbt.setName("apache-nutch-2.3.1");
        
        String content = getObjectMapper().writeValueAsString(nbt);
        
        createInternal(getPluralPath(), content, null, new ResultHandler() {
            @Override
            public void handle(MvcResult result) throws Exception {
                printHeaders(result);
            }
        }, status().is(201));
        
        Thread.sleep(100);
        long count = nbtRepo.count();
        assertThat("there should be 1 item", count, equalTo(1L));
    }

    /* (non-Javadoc)
     * @see com.m3958.logcollector.MvcBase#getPlural()
     */
    @Override
    public String getPlural() {
        return "nutchBuilderTemplates";
    }

}
