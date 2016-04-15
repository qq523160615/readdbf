package com.flinkinfo.monitor_data.monitor;

import com.flinkinfo.monitor_data.dbf.DBFManager;
import org.apache.commons.io.monitor.FileAlterationListenerAdaptor;

import java.io.File;


public class MonitorListen extends FileAlterationListenerAdaptor
{
    private DBFManager dbfManager;

    public MonitorListen(DBFManager dbfManager)
    {
        this.dbfManager = dbfManager;
    }

    @Override
    public void onFileCreate(File file)
    {
        System.out.println("[新建]:" + file.getAbsolutePath());
        String fileName = file.getName();
        String table = file.getName().substring(0, fileName.indexOf("."));
        dbfManager.init(file.getAbsolutePath(), table);
        dbfManager.create();
        dbfManager.insert();
    }

    @Override
    public void onFileChange(File file)
    {
        System.out.println("[修改]:" + file.getAbsolutePath());
    }

    @Override
    public void onFileDelete(File file)
    {
        System.out.println("[删除]:" + file.getAbsolutePath());
    }
}

