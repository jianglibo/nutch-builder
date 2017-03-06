package com.jianglibo.nutchbuilder.vo;

import java.util.Map;

import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * @author jianglibo@gmail.com
 *
 */
@Component
public class JavaTypeHolder implements InitializingBean {

    private JavaType mapStringString;

    private JavaType treeMapStringString;

    private JavaType tuaMapStringString;

    @Autowired
    private ObjectMapper objectMapper;

    /*
     * (non-Javadoc)
     * 
     * @see org.springframework.beans.factory.InitializingBean#afterPropertiesSet()
     */
    @Override
    public void afterPropertiesSet() throws Exception {
        mapStringString = objectMapper.getTypeFactory().constructMapLikeType(Map.class, String.class, String.class);
//        treeMapStringString = objectMapper.getTypeFactory().constructParametricType(Tree.class, mapStringString);
//        tuaMapStringString = objectMapper.getTypeFactory().constructParametricType(TreeUpdateAction.class, mapStringString);
    }

    public JavaType getMapStringString() {
        return mapStringString;
    }

    public JavaType getTreeMapStringString() {
        return treeMapStringString;
    }

    public JavaType getTuaMapStringString() {
        return tuaMapStringString;
    }
}
