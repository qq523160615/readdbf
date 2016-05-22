package com.flinkinfo.monitordata.monitor;

import com.flinkinfo.monitordata.dbf.DBFFileManager;
import com.flinkinfo.monitordata.util.LoggerUtil;
import org.apache.commons.io.monitor.FileAlterationListenerAdaptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.IOException;
import java.sql.SQLException;

/**
 * 监控监听类
 *
 * @author jimmy
 */
@Component
public class MonitorListen extends FileAlterationListenerAdaptor
{
    //DBF文件管理类
    @Autowired
    private DBFFileManager dbfFileManager;

    @Override
    public void onFileCreate(File file)
    {
        System.out.println("[新增]:" + file.getAbsolutePath());
        writeToDb(file);
    }

    @Override
    public void onFileChange(File file)
    {
        System.out.println("[修改]:" + file.getAbsolutePath());
        writeToDb(file);
    }

    @Override
    public void onFileDelete(File file)
    {
        System.out.println("[删除]:" + file.getAbsolutePath());
    }

    private void writeToDb(File file)
    {
        try
        {
            String fileName = file.getAbsolutePath();
            if (fileName.endsWith(".DBF") || fileName.endsWith(".dbf"))
            {
                String table = file.getName().substring(0, file.getName().indexOf("."));
                dbfFileManager.writeToDb(fileName, table);
                dbfFileManager.closeInputStream();
            }
        } catch (IOException e)
        {
            e.printStackTrace();
            LoggerUtil.error(e.getMessage());
        } catch (SQLException e)
        {
            e.printStackTrace();
            LoggerUtil.error(e.getMessage());
        }
    }
}

