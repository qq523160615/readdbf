package com.flinkinfo.monitordata.monitor;


import org.apache.commons.io.monitor.FileAlterationMonitor;
import org.apache.commons.io.monitor.FileAlterationObserver;

/**
 * 监测管理类
 */
public class MonitorManger
{
    private FileAlterationMonitor monitor;

    private FileAlterationObserver observer;

    //轮循时间,默认5毫秒
    private long interval = 5l;

    public MonitorManger(String rootDir)
    {
        observer = new FileAlterationObserver(
                rootDir, null,
                null);
    }

    public void setMonitorListen(MonitorListen monitorListen)
    {
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
}
