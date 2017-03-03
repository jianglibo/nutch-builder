package com.jianglibo.nutchbuilder.domain;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.jianglibo.nutchbuilder.Tbase;
import com.jianglibo.nutchbuilder.repository.NutchBuilderTemplateRepository;

/**
 * @author jianglibo@gmail.com
 *         2015年9月28日
 *
 */
public class TestNutchBuilderTemplate extends Tbase {
    
    @Autowired
    private NutchBuilderTemplateRepository nbtp;

    @Test
    public void tCreate() {
        NutchBuilderTemplate nbt = new NutchBuilderTemplate();
        nbt.setName("notexists");
        nbtp.save(nbt);
    }
}
