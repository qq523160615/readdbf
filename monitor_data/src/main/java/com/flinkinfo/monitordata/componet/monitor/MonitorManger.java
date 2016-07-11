package com.flinkinfo.monitordata.componet.monitor;


import com.flinkinfo.monitordata.componet.util.LoggerUtil;
import org.apache.commons.io.monitor.FileAlterationMonitor;
import org.apache.commons.io.monitor.FileAlterationObserver;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 * 监测管理类
 */
@Component
public class MonitorManger
{
    private FileAlterationMonitor monitor;

    private FileAlterationObserver observer;

    @Value("${monitor.rootdir}")
    String rootDir;

    //轮循时间,默认5毫秒
    private long interval = 5l;

    public void setMonitorListen(MonitorListen monitorListen)
    {
//        System.out.println("监控目录:" + rootDir);
        LoggerUtil.info("监控目录:" + rootDir);
        if (observer == null)
        {
            observer = new FileAlterationObserver(rootDir);
        }
        observer.addListener(monitorListen);
        monitor = new FileAlterationMonitor(interval, observer);
    }

    public void setInterval(long interval)
    {
        this.interval = interval;
    }

    public void start() throws Exception
    {
        monitor.start();
    }

    public void stop() throws Exception
    {
        monitor.stop();
    }
}
