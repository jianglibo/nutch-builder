package com.jianglibo.nutchbuilder.util;


import com.jianglibo.nutchbuilder.domain.UcToken;
import com.jianglibo.nutchbuilder.vo.BootUserVo;

public class SignupConfirmTemplate extends SendCloudTemplate {
    
    public static final String VURL = "vurl";
    
    private BootUserVo personVo;

    public SignupConfirmTemplate(BootUserVo personVo, String host, UcToken uctk) {
        super("regist_confirm", VURL);
        setSubjectTpl("�뼤�������˺ţ�%s?");
        this.personVo = personVo;
        withVar(VURL, host + "/email-verify?uctk=" + uctk.getTk());
    }

    @Override
    protected String getSubject() {
        return String.format(getSubjectTpl(), personVo.getUsername());
    }
}
