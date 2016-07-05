package com.flinkinfo.monitordata.service;

/**
 * 推送内容到中转
 *
 * @author jimmy
 */
public interface PushService
{
    void push(String name,String data) throws Exception;
}
