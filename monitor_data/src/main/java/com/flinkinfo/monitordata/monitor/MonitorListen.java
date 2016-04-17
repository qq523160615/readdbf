package com.flinkinfo.monitordata.monitor;

import com.flinkinfo.monitordata.dao.DBHelper;
import com.flinkinfo.monitordata.dbf.DBFFileManager;
import org.apache.commons.io.monitor.FileAlterationListenerAdaptor;

import java.io.File;
import java.io.IOException;
import java.sql.SQLException;

/**
 * 监控监听类
 *
 * @author jimmy
 */
public class MonitorListen extends FileAlterationListenerAdaptor
{
    //DBF文件管理类
    private DBFFileManager dbfFileManager;

    //数据库帮助类
    private DBHelper dbHelper;

    public MonitorListen(DBFFileManager dbfFileManager, DBHelper dbHelper)
    {
        this.dbfFileManager = dbfFileManager;
        this.dbHelper = dbHelper;
    }

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
            String table = file.getName().substring(0, file.getName().indexOf("."));
            String fileName = file.getAbsolutePath();
            if (fileName.endsWith(".DBF") || fileName.endsWith(".dbf"))
            {
                dbfFileManager.writeToDb(fileName, dbHelper, table);
                dbfFileManager.closeInputStream();
            }

        } catch (IOException e)
        {
            e.printStackTrace();
        } catch (SQLException e)
        {
            e.printStackTrace();
        }
    }
}

