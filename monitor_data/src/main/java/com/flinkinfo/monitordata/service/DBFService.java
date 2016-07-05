package com.flinkinfo.monitordata.service;

/**
 * DBF文件服务
 *
 * @author jimmy
 */
public interface DBFService
{
    /**
     * 获取DBF文件内容
     *
     * @param path
     * @return
     */
    String readDBF(String path) throws Exception;

    /**
     * 获取DBF变化部分内容
     *
     * @param path
     * @return
     */
    String readChangeDBF(String path) throws Exception;
}
