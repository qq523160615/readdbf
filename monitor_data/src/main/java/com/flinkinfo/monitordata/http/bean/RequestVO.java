package com.flinkinfo.monitordata.http.bean;

import com.alibaba.fastjson.annotation.JSONField;

import java.io.Serializable;
import java.util.Map;

/**
 * 请求参数
 *
 * @author nico huangwenzeng1@163.com
 */
public class RequestVO implements Serializable
{
    //接口名
    @JSONField(name = "service_name")
    private String serviceName;

    //接口对应参数
    @JSONField(name = "params")
    private Map<String, Object> params;

    public String getServiceName()
    {
        return serviceName;
    }

    public void setServiceName(String serviceName)
    {
        this.serviceName = serviceName;
    }

    public Map<String, Object> getParams()
    {
        return params;
    }

    public void setParams(Map<String, Object> params)
    {
        this.params = params;
    }
}
