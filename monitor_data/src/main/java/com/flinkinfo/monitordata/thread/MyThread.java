package com.flinkinfo.monitordata.thread;

import com.flinkinfo.monitordata.dao.DBHelper;
import com.flinkinfo.monitordata.dbf.DBFFileManager;
import com.flinkinfo.monitordata.monitor.MonitorListen;
import com.flinkinfo.monitordata.monitor.MonitorManger;
import com.flinkinfo.monitordata.util.LoggerUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * 线程
 */
@Component
public class MyThread extends Thread
{
    @Autowired
    MonitorManger monitorManger;

    @Autowired
    MonitorListen monitorListen;

    @Override
    public void run()
    {
        super.run();
        try
        {
            System.out.println("开始监控:");
            LoggerUtil.info("开始监控");
            //设置监控监听器
            monitorManger.setMonitorListen(monitorListen);

            //开启监控
            monitorManger.start();

        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }
}
