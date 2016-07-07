package com.flinkinfo.monitordata.componet.thread;


import com.flinkinfo.monitordata.componet.monitor.MonitorListen;
import com.flinkinfo.monitordata.componet.monitor.MonitorManger;
import com.flinkinfo.monitordata.componet.util.LoggerUtil;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.util.Date;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * 线程
 */
public class DBFMonitorThread extends Thread
{
    MonitorManger monitorManger;

    MonitorListen monitorListen;

    public DBFMonitorThread()
    {
        ApplicationContext context = new ClassPathXmlApplicationContext("applicationContext.xml");
        monitorManger = context.getBean(MonitorManger.class);
        monitorListen = context.getBean(MonitorListen.class);
        ThreadPoolExecutor threadPoolExecutor = new ThreadPoolExecutor(10, 12, 3,
                TimeUnit.SECONDS, new ArrayBlockingQueue<Runnable>(10), new ThreadPoolExecutor.DiscardOldestPolicy());
        monitorListen.setThreadPoolExecutor(threadPoolExecutor);

//        TaskThread taskThread = context.getBean(TaskThread.class);
//        taskThread.start();
    }

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
            System.out.println(new Date());
        }
    }
}
