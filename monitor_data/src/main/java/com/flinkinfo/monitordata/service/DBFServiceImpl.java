package com.flinkinfo.monitordata.service;

import com.flinkinfo.monitordata.manager.DBFFileManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * DBF服务实现类
 *
 * @author jimmy
 */
@Component
public class DBFServiceImpl implements DBFService
{
    @Autowired
    private DBFFileManager dbfFileManager;

    /**
     * 读取dbf文件
     *
     * @param path 文件路径
     * @return
     * @throws Exception
     */
    public String readDBF(String path) throws Exception
    {
        return dbfFileManager.readDBF(path, false);
    }

    /**
     * 读取变化文件
     *
     * @param path
     * @return
     * @throws Exception
     */
    public String readChangeDBF(String path) throws Exception
    {
        return dbfFileManager.readDBF(path, true);
    }
}
