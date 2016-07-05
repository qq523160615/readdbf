package com.flinkinfo.monitordata.componet.http.bean;

import com.alibaba.fastjson.annotation.JSONField;

import java.io.Serializable;
import java.util.Map;

/**
 * 返回数据实体
 *
 * @author nico huangwenzeng1@163.com
 */
public class ResponseVO implements Serializable
{
    //成功状态
    public static final int STATUS_SUCCESS = 0;

    //失败状态
    public static final int STATUS_FAILURE = 2;

    //状态--int 0是正常，2是错误
    @JSONField(name = "status")
    private int status;

    //错误信息--string 只有错误时才有返回，正常是返回''
    @JSONField(name = "error_message")
    private String errorMessage;

    //返回实体--对应接口返回的实体，错误时没有这个返回
    @JSONField(name = "content")
    private Map<String, Object> content;

    public int getStatus()
    {
        return status;
    }

    public void setStatus(int status)
    {
        this.status = status;
    }

    public String getErrorMessage()
    {
        return errorMessage;
    }

    public void setErrorMessage(String errorMessage)
    {
        this.errorMessage = errorMessage;
    }

    public Map<String, Object> getContent()
    {
        return content;
    }

    public void setContent(Map<String, Object> content)
    {
        this.content = content;
    }
}
