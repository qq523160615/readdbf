package com.flinkinfo.monitordata.monitor;

import com.flinkinfo.monitordata.dbf.DBFFileManager;
import com.flinkinfo.monitordata.util.LoggerUtil;
import org.apache.commons.io.monitor.FileAlterationListenerAdaptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.IOException;
import java.sql.SQLException;
import java.util.Date;

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
        LoggerUtil.info("[新增]:" + file.getAbsolutePath());
        System.out.println("[新增]:" + file.getAbsolutePath());
//        Calendar cal = Calendar.getInstance();
//        cal.setTimeInMillis(file.lastModified());
//        System.out.println(cal.getTime().toLocaleString());
        writeToDb(file,new Date());
    }

    @Override
    public void onFileChange(File file)
    {
        LoggerUtil.info("[修改]:" + file.getAbsolutePath());
        System.out.println("[修改]:" + file.getAbsolutePath());
//        Calendar cal = Calendar.getInstance();
//        cal.setTimeInMillis(file.lastModified());
//        System.out.println(cal.getTime().toLocaleString());
        writeToDb(file,new Date());
    }

    @Override
    public void onFileDelete(File file)
    {
        LoggerUtil.info("[删除]:" + file.getAbsolutePath());
        System.out.println("[删除]:" + file.getAbsolutePath());
    }

    private void writeToDb(File file,Date time)
    {
        try
        {
            String fileName = file.getAbsolutePath();
            if (fileName.endsWith(".DBF") || fileName.endsWith(".dbf"))
            {
                String table = file.getName().substring(0, file.getName().indexOf("."));
                dbfFileManager.writeToDb(fileName, table,time);
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

