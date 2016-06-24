package com.flinkinfo.monitordata.monitor;

import com.flinkinfo.monitordata.dbf.DBFFileManager;
import com.flinkinfo.monitordata.util.LoggerUtil;
import org.apache.commons.io.monitor.FileAlterationListenerAdaptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.File;
import java.util.Date;
import java.util.concurrent.ThreadPoolExecutor;

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

    private ThreadPoolExecutor threadPoolExecutor;

    public void setThreadPoolExecutor(ThreadPoolExecutor threadPoolExecutor)
    {
        this.threadPoolExecutor = threadPoolExecutor;
    }

    @Override
    public void onFileCreate(File file)
    {
        LoggerUtil.info("[新增]:" + file.getAbsolutePath());
        System.out.println("[新增]:" + file.getAbsolutePath());
//        threadPoolExecutor.execute(new WriteThread(file,new Date()));

    }

    @Override
    public void onFileChange(final File file)
    {
        LoggerUtil.info("[修改]:" + file.getAbsolutePath());
        System.out.println("[修改]:" + file.getAbsolutePath());
        threadPoolExecutor.execute(new WriteThread(file,new Date()));

    }


    @Override
    public void onFileDelete(File file)
    {
        LoggerUtil.info("[删除]:" + file.getAbsolutePath());
        System.out.println("[删除]:" + file.getAbsolutePath());
    }


    class WriteThread extends Thread
    {
        private File file;

        private Date time;

        public WriteThread(File file, Date time)
        {
            this.file = file;
            this.time = time;
        }

        @Override
        public void run()
        {
            super.run();
            try
            {
                writeToDb();
            } catch (Exception e)
            {
                LoggerUtil.error(e.getMessage());
                e.printStackTrace();
            }
        }

        private void writeToDb() throws Exception
        {

            String fileName = file.getAbsolutePath();
            if (fileName.endsWith(".DBF") || fileName.endsWith(".dbf"))
            {
                String table = file.getName().substring(0, file.getName().indexOf("."));
                dbfFileManager.writeToDb(fileName, table, time);
                dbfFileManager.closeInputStream();
            }

        }
    }

}

