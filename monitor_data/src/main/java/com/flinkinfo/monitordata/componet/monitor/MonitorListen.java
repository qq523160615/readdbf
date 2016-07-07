package com.flinkinfo.monitordata.componet.monitor;

import com.flinkinfo.monitordata.componet.util.DateUtil;
import com.flinkinfo.monitordata.componet.util.LoggerUtil;
import com.flinkinfo.monitordata.service.DBFServiceImpl;
import com.flinkinfo.monitordata.service.PushService;
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
    private DBFServiceImpl dbfService;

    //推送服务
    @Autowired
    private PushService pushService;

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
//        threadPoolExecutor.execute(new ReadThread(file,new Date()));

    }

    @Override
    public void onFileChange(final File file)
    {
        LoggerUtil.info("[修改]:" + file.getAbsolutePath());
        System.out.println("[修改]:" + file.getAbsolutePath());
        threadPoolExecutor.execute(new ReadThread(file, new Date()));

    }


    @Override
    public void onFileDelete(File file)
    {
        LoggerUtil.info("[删除]:" + file.getAbsolutePath());
        System.out.println("[删除]:" + file.getAbsolutePath());
    }


    class ReadThread extends Thread
    {
        private File file;

        private Date time;

        public ReadThread(File file, Date time)
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
                saveData();
            }
            catch (Exception e)
            {
                LoggerUtil.error(e.getMessage());
                e.printStackTrace();
            }
        }

        private void saveData() throws Exception
        {
            String json = "";
            String path = file.getAbsolutePath();
            if (path.endsWith(".DBF") || path.endsWith(".dbf"))
            {

                //保存数据
                Date date = new Date();

                //在09:00到09:15和12:45到13:00之外时间段发送数据
                if (!((date.getTime() > DateUtil.specialDate("085900").getTime()
                        && date.getTime() < DateUtil.specialDate("091500").getTime()) ||
                        (date.getTime() > DateUtil.specialDate("124400").getTime()
                                && date.getTime() < DateUtil.specialDate("130000").getTime())))
                {
                    //获取dbf文件数据
                    json = dbfService.readChangeDBF(path);
//                    json = dbfService.readDBF(path);
                }

                if (!json.equals(""))
                {
                    String name = file.getName().substring(0, file.getName().indexOf("."));
                    pushService.push(name, json);
                }
            }

        }
    }

}

