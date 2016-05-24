package com.flinkinfo.monitordata.thread;


import com.flinkinfo.monitordata.monitor.MonitorListen;
import com.flinkinfo.monitordata.monitor.MonitorManger;
import com.flinkinfo.monitordata.util.LoggerUtil;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * 线程
 */
public class MyThread extends Thread
{
    MonitorManger monitorManger;

    MonitorListen monitorListen;

    public MyThread()
    {
        ApplicationContext context = new ClassPathXmlApplicationContext("applicationContext.xml");
        monitorManger = context.getBean(MonitorManger.class);
        monitorListen = context.getBean(MonitorListen.class);
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
        }
    }
}
