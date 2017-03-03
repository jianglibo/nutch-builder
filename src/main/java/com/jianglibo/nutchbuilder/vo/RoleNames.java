package com.jianglibo.nutchbuilder.vo;

import java.lang.reflect.Field;
import java.util.List;
import java.util.stream.Stream;

import com.google.common.collect.Lists;

public class RoleNames {
    
    public static final String ROLE_PREFIX = "ROLE_";
    
    
    public static final String USER = "ROLE_USER";
    public static final String APP_DATA_CHANGE_FIX_RATE = "ROLE_APP_DATA_CHANGE_FIX_RATE";
    public static final String NOT_EXIST = "ROLE_NOT_EXIST";
    public static final String PERSON_MANAGER = "ROLE_PERSON_MANAGER";
    public static final String PERSON_DELETER = "ROLE_PERSON_DELETER";
    public static final String PERSON_CREATOR = "ROLE_PERSON_CREATOR";
    public static final String ROLE_CREATOR = "ROLE_ROLECREATOR";
    
    public static final String ACL_OWNERSHIP = "ROLE_ACL_OWNERSHIP";
    public static final String ACL_AUDITING = "ROLE_ACL_AUDITING";
    public static final String ACL_DETAIL = "ROLE_ACL_DETAIL";
    public static final String SYSTEM_MANAGER = "ROLE_SYSTEM_MANAGER";
    
    public static List<String> allFields() {
        Field[] fields = RoleNames.class.getDeclaredFields();
        List<String> fs = Lists.newArrayList();
        
        Stream.of(fields).forEach(f -> {
            try {
                String rn = f.get(null).toString();
                if (rn != ROLE_PREFIX) {
                    fs.add(rn);
                }
                
            } catch (Exception e) {
            }
        });
        return fs;
    }
}
