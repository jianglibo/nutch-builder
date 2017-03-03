package com.jianglibo.nutchbuilder.util;

public class TaskFeedbackTemplate extends SendCloudTemplate {
    
    public static String APPNAME = "appname";

    public TaskFeedbackTemplate(String appname) {
        super("task_feedback", APPNAME);
        setSubjectTpl("任务完成通知");
        withVar(APPNAME, appname);
    }

    @Override
    protected String getSubject() {
        return getSubjectTpl();
    }
}
